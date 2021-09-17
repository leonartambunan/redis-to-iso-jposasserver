package id.co.bbw.spec;

import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseValidation {

    public ISOMsg setResponseCode(Context ctx,
                                  ISOMsg isoMsg,
                                  String rc,
                                  String servicename,
                                  String servicesource,
                                  String rc_reason,
                                  String detail)
    {
        logger.info("setResponseCode");
        logger.info("servicename:{}",servicename);
        logger.info("servicesource:{}",servicesource);

        logger.info(rc_reason + " || " + detail);
        ctx.put(IsoConstants.CONTEXT_RESPONSE_CODE, rc);
        ctx.put(IsoConstants.CONTEXT_RESPONSE_MSG, rc_reason);
        try {
            isoMsg.set(39, rc);
        } catch (Exception e) {
            logger.error("e",e);
        }
        //String traceid = isoMsg.getString( 7 ) + isoMsg.getString( 11 );
        //String tracenumber = isoMsg.getString( 37 );
        //if( tracenumber == null ) tracenumber = "null";
        return isoMsg;
    }

    private final static Logger logger = LoggerFactory.getLogger(BaseValidation.class);
}
