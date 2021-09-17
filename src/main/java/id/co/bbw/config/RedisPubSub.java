package id.co.bbw.config;

import id.co.bbw.exception.CBSTimeoutException;
import id.co.bbw.exception.LinkIsDownException;
import id.co.bbw.service.CbsService;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@WebListener
public class RedisPubSub implements ServletContextListener {
    @Autowired
    JedisPool jedisPool;

    @Autowired
    CbsService cbsService;

    ISOPackager packager=null;

    public static final String REDIS_CHANNEL_REQUEST  = "channel_iso_online_req";
    public static final String REDIS_CHANNEL_RESPONSE = "channel_iso_online_res";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        destroy();
    }

    private final JedisPubSub jedisPubSub = new JedisPubSub() {
        @Override
        public void onMessage(String channel, String incomingMessage) {
            logger.info("onMessage");
            if (REDIS_CHANNEL_REQUEST.equals(channel)) {
                logger.info(incomingMessage);
                ISOMsg request = new ISOMsg();
                ISOMsg response;
                try {
                    request.setPackager(packager);
                    request.unpack(incomingMessage.getBytes());
                    response = cbsService.sendToSwitching(request);

                } catch (Exception e) {
                    logger.warn(e.getMessage());
                    response = (ISOMsg) request.clone();
                    try {response.setResponseMTI();} catch (ISOException isoException) {e.printStackTrace();}
                    response.set(39,"68");
                } catch (LinkIsDownException e) {
                    logger.warn(e.getMessage());
                    response = (ISOMsg) request.clone();
                    try {response.setResponseMTI();} catch (ISOException isoException) {e.printStackTrace();}
                    response.set(39,"67");
                } catch (CBSTimeoutException e) {
                    logger.warn(e.getMessage());
                    response = (ISOMsg) request.clone();
                    try {response.setResponseMTI();} catch (ISOException isoException) {e.printStackTrace();}
                    response.set(39,"66");
                }

                sendResponse(response);
            }
        }
    };


    private ExecutorService subscriberExecutor;
    private ExecutorService publisherExecutor;
    private Future subscriberFuture;

    private synchronized void init() {

        try {
            String userHome = System.getProperty("user.dir");
            File f = new File(userHome + "/" + "cfg/packager.xml");
            InputStream is = new FileInputStream(f);
            packager = new GenericPackager(is);
        } catch (Exception e) {
            logger.error("Abort. Abort .. "+e.getMessage());
            System.err.println("Abort. Abort .. "+e.getMessage());
            System.exit(3);
        }

        System.out.println("init");
        jedisSubscriber = jedisPool.getResource();
        jedisPublisher = jedisPool.getResource();
        if (subscriberFuture == null) {
            logger.info("Start background thread for getting request from Redis");
            subscriberExecutor = Executors.newFixedThreadPool(1);
            subscriberFuture = subscriberExecutor.submit(redisListenerTask);
            publisherExecutor = Executors.newFixedThreadPool(20);
        }
    }

    private final RedisListenerTask redisListenerTask = new RedisListenerTask();

    public synchronized void destroy() {
        if (subscriberFuture != null) {
            try {
                jedisPubSub.unsubscribe();
                subscriberExecutor.shutdownNow();
                publisherExecutor.shutdownNow();
                logger.info("Background thread for getting request is now closed");
            } catch (Exception ex) {
                logger.warn(ex.getMessage());
                ex.printStackTrace();
            } finally {
                try {
                    jedisPublisher.quit();
                    jedisSubscriber.quit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class RedisListenerTask implements Runnable {

        public void run() {
            try {
                jedisSubscriber.subscribe(jedisPubSub, REDIS_CHANNEL_REQUEST);
            } catch (Exception ex) {
                logger.warn(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private Jedis jedisPublisher;
    private Jedis jedisSubscriber;

    public void sendResponse(ISOMsg isoMsg) {
        try {
            isoMsg.setPackager(packager);
            String response = new String(isoMsg.pack(), StandardCharsets.UTF_8);
            jedisPublisher.publish(REDIS_CHANNEL_RESPONSE,response);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            ex.printStackTrace();
        }
    }


    private final static Logger logger = LoggerFactory.getLogger(RedisPubSub.class.getName());
}

