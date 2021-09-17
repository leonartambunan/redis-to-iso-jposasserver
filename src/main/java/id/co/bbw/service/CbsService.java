package id.co.bbw.service;

import id.co.bbw.exception.CBSTimeoutException;
import id.co.bbw.exception.LinkIsDownException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOServer;
import org.jpos.q2.iso.QMUX;
import org.jpos.util.NameRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CbsService {

    @Autowired
    QMUX qmux;

    private static final long CBS_TIMEOUT = 5000;

    public ISOMsg sendToSwitching(final ISOMsg isoRequest) throws LinkIsDownException, CBSTimeoutException, NameRegistrar.NotFoundException, ISOException {

        String channel = "digitalbank";
        boolean isChannelServer = false;

        ISOMsg isoResponse=null;

        logger.warn("sendToCBS() is in progress");

        final QMUX mux = NameRegistrar.get("mux."+channel+"-mux");
        boolean isClientConnected = true;

        ISOServer isoServer;

        if (isChannelServer) {
            isoServer = ISOServer.getServer(channel);
            if (mux == null || isoServer == null) {
                isClientConnected = false;
            }
            else if (isoServer.getActiveConnections() == 0) {
                isClientConnected = false;
            }
        }
        if (!isClientConnected) {
            logger.info("mux == null || isoServer == null || isoServer.getActiveConnections() == 0");
            throw new LinkIsDownException("Client is not connected");
        }

        logger.info("KEY ISOMsg " + mux.getKey(isoRequest));
        isoResponse = mux.request(isoRequest, CBS_TIMEOUT);

        if (!mux.isConnected() && isoResponse == null) {
            logger.info("r == link down ");
            throw new LinkIsDownException();
        }
        if (isoResponse == null) {
            logger.info("r == null ");
            throw new CBSTimeoutException("ISO response was not received within defined period");
        }
        logger.info("ISO Response : " + isoResponse.getMTI());
        String rc = isoResponse.getString(39);
        logger.info("ISO ResponseCode : " + rc);
        if (!"00".equals(rc)) {
            if ("".equals(rc)) {
                isoResponse.set(39, "06");
            }
            else {
                isoResponse.set(39, rc);
            }
        }
        return isoResponse;

    }

    private static final Logger logger = LoggerFactory.getLogger(CbsService.class);
}

