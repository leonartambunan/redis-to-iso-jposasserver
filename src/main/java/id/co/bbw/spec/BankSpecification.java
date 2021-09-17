package id.co.bbw.spec;

import id.co.bbw.utility.IsoUtils;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.slf4j.LoggerFactory;

import static id.co.bbw.spec.IsoConstants.FINANCIAL_REQUEST;
import static id.co.bbw.spec.IsoConstants.NETWORK_REQUEST;
import static id.co.bbw.spec.IsoConstants.ResponseCode.RC_INVALID_TRANSACTION;

public class BankSpecification extends BaseValidation {

    public BankSpecification(String name) {
    }

    public ISOMsg validateRequest(Context ctx, ISOMsg isoRequest) {
        logger.info("validateRequest");
        try {
            String mti = isoRequest.getMTI();
            logger.info("mti : {}",mti);
            if (mti.equals(NETWORK_REQUEST)) {
                String f48 = IsoUtils.getField(isoRequest, 48);
                if (f48.length() > 0) {
                }
            } else if (FINANCIAL_REQUEST.equals(mti)) {
                //main task
                isoRequest = checkRequest(ctx, isoRequest);

                try {
                    if (isoRequest.isRequest()) isoRequest.setResponseMTI();
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                }
            }


        } catch (Exception e) {
            logger.info("Exception : {}",e.getMessage());
            logger.warn(e.getMessage(), e);
            return setResponseCode(ctx, isoRequest, RC_INVALID_TRANSACTION, "Spec_Bank", "Spec_Bank.validateRequest()",
                    "[02004] Error Exception " + e.getMessage(), "");
        }

        return isoRequest;
    }

    public ISOMsg checkRequest(Context ctx, ISOMsg isoRequest) {
        logger.info("checkRequest");
        return isoRequest;
    }

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BankSpecification.class);
}
