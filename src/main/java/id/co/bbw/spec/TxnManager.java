package id.co.bbw.spec;

import id.co.bbw.utility.IsoUtils;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.jpos.transaction.GroupSelector;
import org.jpos.util.NameRegistrar;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class TxnManager implements GroupSelector, Configurable {

    private Configuration cfg;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TxnManager.class);

    public TxnManager() {
        //log = Log.getLog("Q2", getClass().getName());
    }

    String readyKey = "digitalbank.ready";

    public String select(long l, Serializable serializable) {
        logger.info("select(id,serializable)");

        Context ctx = (Context) serializable;
        ISOMsg isoMsg = IsoUtils.getISORequest(ctx);

        String groups = "";
        try {
            String channel = IsoUtils.getCHANNEL(ctx);
            String isoMTI = isoMsg.getMTI();

            logger.info("get Groups " + channel + "." + isoMTI);
            logger.info("Field[70]:{}",isoMsg.getString(70));
            logger.info("Field[03]:{}",isoMsg.getString(3));

            String value;

            if (IsoConstants.NETWORK_REQUEST.equals(isoMTI) || IsoConstants.NETWORK_RESPONSE.equals(isoMTI)) {
                value = channel + "." + isoMTI + "." + isoMsg.getString(70);
            } else if (IsoConstants.FINANCIAL_REQUEST.equals(isoMTI) || IsoConstants.FINANCIAL_RESPONSE.equals(isoMTI)) {
                value = channel + "." + isoMTI + "." + isoMsg.getString(3).substring(0, 2);
            } else if (IsoConstants.REVERSAL_REQUEST.equals(isoMTI)
                    || IsoConstants.REVERSAL_REPEAT.equals(isoMTI)
                    || IsoConstants.REVERSAL_RESPONSE.equals(isoMTI)
            ) {
                value = channel + "." + isoMTI + "." + isoMsg.getString(3).substring(0, 2);
            } else {
                value = isoMTI + isoMsg.getString(3);
            }

            logger.warn("value:{}",value);

            if (value.length() > 0) {
                groups = cfg.get(value, "NOT_FOUND");
            }

            // kalau groups berisi "NOT_FOUND" artinya tidak didefinisikan di
            // 20_tnxmgr.xml
            logger.info("groups : {} | {}", groups, value);

            if ("NOT_FOUND".equals(groups)) {
                logger.warn("NOT FOUND");
            } else if (isoMTI.equals(IsoConstants.NETWORK_REQUEST)) {
                    ctx.put(IsoConstants.CONTEXT_RESPONSE_CODE, IsoConstants.ResponseCode.RC_APPROVED);
                    isoMsg.set(39,IsoConstants.ResponseCode.RC_APPROVED);
                    ctx.put(IsoConstants.CONTEXT_RSP, isoMsg);

                    logger.error("readyKey:{}",readyKey);

                    NameRegistrar.register(readyKey, true);
            } else {
                logger.debug("its okay");
                logger.debug("its okay");
                logger.debug("its okay");
            }

        } catch (Exception ex) {
            logger.error("select", ex); //
            logger.warn(ex.getMessage(), ex);
        }

        logger.info("selected : {}",groups);
        return groups;
    }

    public int prepare(long l, Serializable srlzbl) {
        logger.info("prepare(id,serializable)");
        return PREPARED;
    }

    public void commit(long l, Serializable srlzbl) {
        logger.info("commit()");
    }

    public void abort(long l, Serializable srlzbl) {
        logger.info("abort()");
    }

    public void setConfiguration(Configuration c) {
        logger.info("setConfiguration({})",c.toString());
        this.cfg = c;
    }
}
