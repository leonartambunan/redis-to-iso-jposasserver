package id.co.bbw.spec;

import id.co.bbw.utility.IsoUtils;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.jpos.transaction.TransactionParticipant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import static id.co.bbw.spec.IsoConstants.ResponseCode.RC_APPROVED;
import static id.co.bbw.spec.IsoConstants.ResponseCode.RC_ERROR;

public class ValidateRequest extends BaseValidation implements TransactionParticipant, Configurable {
    private BankSpecification specification = new BankSpecification("DEFAULT");
    private Configuration cfg;
    //private final Log log;
    private final static Logger logger = LoggerFactory.getLogger(ValidateRequest.class);

    public ValidateRequest() {
        //log = Log.getLog("Q2", getClass().getName());
    }

    public int prepare(long l, Serializable serializable) {
        //Trace trace = new Trace( "ValidateRequest", "prepare()" );
        logger.info("prepare");
        Context ctx = (Context) serializable;
        ISOMsg isoRequest = IsoUtils.getISORequest(ctx);
        int result;

        try {
            ISOMsg isoResp = specification.validateRequest(ctx, isoRequest);
            String rc;
            if (isoResp.isResponse()) {
                rc = isoResp.getString(IsoConstants.Field.RESPONSE_CODE);
                if (rc == null) rc = "";
                if (rc.equals("")) rc = RC_ERROR;

                logger.info("rc:{}",rc);

                ctx.put(IsoConstants.CONTEXT_RSP, isoResp);

                if (rc.equals(RC_APPROVED)) {
                    result = PREPARED;
                } else {
                    setResponseCode(ctx, isoRequest, rc,
                            "MyBankFast", "ValidateRequest.prepare()",
                            "[02001] Reject dari SPEC.validateRequest() channel:" + IsoUtils.getCHANNEL(ctx), "");

                    result = ABORTED;
                }
            } else {
                ctx.put(IsoConstants.CONTEXT_REQ, isoResp);
                result = PREPARED;
            }

        } catch (Exception ex) {
            ctx.put(IsoConstants.CONTEXT_RSP, isoRequest);
            setResponseCode(ctx, isoRequest, RC_ERROR,
                    "MyBankFast", "ValidateRequest.prepare()",
                    "[02001] Exception Channel " + ctx.getString(IsoConstants.CONTEXT_CHANNEL) + " : " + ex.getMessage(),
                    "Exception : " + ex.getMessage());
            logger.error("prepare", ex);
            result = ABORTED;
        }
        return result;
    }

    public void commit(long l, Serializable serializable) {
        logger.info("commit");
    }

    public void abort(long l, Serializable serializable) {
        logger.info("abort");
    }

    public void setConfiguration(Configuration c) throws ConfigurationException {
        logger.info("setConfiguration");
        this.cfg = c;
    }
}
