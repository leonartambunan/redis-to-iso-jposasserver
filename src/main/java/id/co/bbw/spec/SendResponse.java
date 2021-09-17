package id.co.bbw.spec;

import id.co.bbw.utility.IsoUtils;
import org.apache.commons.lang3.StringUtils;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.transaction.AbortParticipant;
import org.jpos.transaction.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map.Entry;
import java.util.Set;

import static id.co.bbw.spec.IsoConstants.*;
import static id.co.bbw.spec.IsoConstants.ResponseCode.RC_ERROR;

public class SendResponse implements AbortParticipant, Configurable {

    private Configuration cfg;
    private static final Logger logger = LoggerFactory.getLogger(SendResponse.class);

    public int prepare(long l, Serializable srlzbl) {
        logger.info("prepare({},{})",l,srlzbl.getClass());
        Context ctx = (Context) srlzbl;
        ISOSource source = IsoUtils.getISOSource(ctx);// (ISOSource)
        if (source == null || !source.isConnected()) {
            logger.error("source i null || source is not connected");
            return ABORTED;
        }
        return PREPARED;
    }

    public int prepareForAbort(long l, Serializable srlzbl) {
        return ABORTED;
    }

    public void commit(long l, Serializable srlzbl) {
        logger.info("commit({},{})",l,srlzbl.getClass());
        sendResponse(l, (Context) srlzbl);
    }

    // 63 02 - credit
    // 01 debit
    public void abort(long l, Serializable srlzbl) {
        logger.info("abort({},{})",l,srlzbl.getClass());
        sendResponse(l, (Context) srlzbl);
    }

    private void sendResponse(long l, Context ctx) {

        logger.info("sendResponse({},{})",l,ctx);

        String channel = IsoUtils.getCHANNEL(ctx);
        ISOSource isoSource = IsoUtils.getISOSource(ctx);// (ISOSource)
        ISOMsg isoResponse = IsoUtils.getISOResponse(ctx);// (ISOMsg)
        String rc = IsoUtils.getResponseCode(isoResponse);// (String)

        IsoUtils.getResponseMsg(ctx);// (String) ctx.get(SPEC.CONTEXT_RESPONE_MSG); //todo fishy

        logger.info("rc:" + rc);

        if (isoSource == null || !isoSource.isConnected()) {
            logger.error("source is null or isosource is not connected");
            return;
        }

        try {
            if (isoSource.isConnected()) {
                if (StringUtils.isEmpty(rc)) {
                    logger.info("SendResponse.sendResponse() set RC:06");
                    rc = RC_ERROR;
                }
            }

            if (isoResponse != null) {
                if (isoResponse.isRequest()) isoResponse.setResponseMTI();
            }

            String isoMTI = isoResponse.getMTI();
            String specResponse;
            switch (isoMTI) {
                case NETWORK_REQUEST:
                case NETWORK_RESPONSE:
                    specResponse = channel + "." + isoResponse.getMTI();
                    break;
                case FINANCIAL_REQUEST:
                case FINANCIAL_RESPONSE:
                    specResponse = channel + "." + isoResponse.getMTI() + "." + isoResponse.getString(3).substring(0, 2);
                    break;
                case REVERSAL_REQUEST:
                case REVERSAL_REPEAT:
                case REVERSAL_RESPONSE:
                    specResponse = channel + "." + isoResponse.getMTI() + "." + isoResponse.getString(3).substring(0, 2);
                    break;
                default:
                    logger.warn("Something fishy happens");
                    specResponse = channel + "." + isoResponse.getMTI();
                    break;
            }

            String tempFields = cfg.get(specResponse);

            if (StringUtils.isEmpty(tempFields)) {
                logger.info("fields is empty");
            } else {
                String[] fields = tempFields.split(",");
                try {
                    for (int v = 0; v < fields.length; v++) {
                        logger.info("fields[" + v + "] " + fields[v]);
                        if (!isoResponse.hasField(fields[v]))
                            isoResponse.set(fields[v], "");
                    }

                    int start = 0;
                    Set<Entry<String, String>> entrySet = isoResponse.getChildren().entrySet();
					for (Entry entry : entrySet) {
						String key = entry.getKey().toString();

                        // System.err.print( "COMPARE [ '"+key +"' :: '" +
                        // fields[start]+"' ] ");
                        if ("-1".equals(key)) {
                            logger.warn("key == -1");
						} else if (key.equals(fields[start])) {
                            logger.info("Key matches --> '{}'",key);
                            start++;
                        } else {
                            logger.warn("Removed --> '{}'",key);
                            isoResponse.unset(key);
                        }
                    }
                } catch (Exception e) {
                    logger.error("SendResponse.sendResponse()",e);
                }
            }

            logger.info("BEFORE - isoSource.send()");

            logger.info(IsoUtils.printIsoMsg(isoResponse));

            isoSource.send(isoResponse); // send message ke belakang

            logger.info("AFTER - isoSource.send()");

            ctx.put(IsoConstants.CONTEXT_RSP, isoResponse);

            ctx.put(IsoConstants.CONTEXT_RESPONSE_CODE, rc);

        } catch (Throwable t) {
            logger.error("sendResponse", t);
        }
    }

    public void setConfiguration(Configuration cfg) {
        logger.info("setConfiguration");
        this.cfg = cfg;
    }

}
