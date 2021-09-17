package id.co.bbw.spec;

import id.co.bbw.utility.IsoUtils;
import id.co.bbw.utility.StringUtils;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.jpos.transaction.TransactionParticipant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import static id.co.bbw.spec.IsoConstants.ResponseCode.RC_ERROR;

public class ValidateBitmap extends BaseValidation implements TransactionParticipant, Configurable {

    private Configuration cfg;

    public int prepare(long l, Serializable serializable) {

        logger.info("prepare");

        Context ctx = (Context) serializable;
        ISOMsg isoRequest = IsoUtils.getISORequest(ctx); //(ISOMsg) ctx.get("REQ");
        int result;
        try {
            //mengambil info bit/field yang dianggap mandatory
            String[] stringBitmap;
            if (isoRequest.hasField(IsoConstants.Field.NETWORK_MANAGEMENT_INFO)) {
                stringBitmap = cfg.get(isoRequest.getMTI()).split(",");
            } else {
                stringBitmap = cfg.get(isoRequest.getMTI() + "." + isoRequest.getString(3)).split(",");
                //contohnya -> <property name="0200.300000" value="0,2,3" comment="BALINQ"/>
            }

        	 logger.info( "stringBitmap "+stringBitmap );
        	 logger.info( "stringBitmap.length "+stringBitmap.length );

            int[] b = new int[stringBitmap.length];

            for (int i = 0; i < stringBitmap.length; i++) {
//            	logger.info( "stringBitmap["+i+"] : "+stringBitmap[i] );
                b[i] = Integer.parseInt(stringBitmap[i]);
            }

            if (isoRequest.hasFields(b)) {
                logger.info("ValidateBitmap() PREPARED RC_APPROVED");
                result = PREPARED;
//                ctx.put( SPEC.CONTEXT_RSP, isoRequest);
//                ctx.put("RC", SPEC.get( ctx, SPEC.RC_APPROVED ) );
            } else {
                logger.info("ValidateBitmap() UNKNOWN_MESSAGE");

                ctx.put(IsoConstants.CONTEXT_RSP, isoRequest);
                setResponseCode(ctx, isoRequest, RC_ERROR,
                        "DigitalBank", "ValidateBitmap.prepare()", "[01001] Fields invalid",
                        " fields:" + StringUtils.printArray(stringBitmap));

                result = ABORTED;
            }
        } catch (ISOException ex) {
            ctx.put(IsoConstants.CONTEXT_RSP, isoRequest);
            setResponseCode(ctx, isoRequest, RC_ERROR,
                    "DigitalBank", "ValidateBitmap.prepare()", "[01002] ISOException",
                    "ISOException : " + ex.getMessage());
            logger.error("prepare", ex);
            result = ABORTED;
        } catch (Exception ex) {
            ctx.put(IsoConstants.CONTEXT_RSP, isoRequest);
            setResponseCode(ctx, isoRequest, RC_ERROR,
                    "DigitalBank", "ValidateBitmap.prepare()", "[01003] Exception",
                    "Exception : " + ex.getMessage());
            logger.error("prepare", ex);
            result = ABORTED;
        }
        return result;
    }

    public void commit(long l, Serializable serializable) {
        logger.info("commit");
    }

    public void abort(long l, Serializable srlzbl) {
        logger.info("abort");
    }

    public void setConfiguration(Configuration c) {
        logger.info("setConfiguration");
        this.cfg = c;
    }

    private static final Logger logger = LoggerFactory.getLogger(ValidateBitmap.class);
}
