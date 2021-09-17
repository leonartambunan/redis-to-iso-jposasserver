package id.co.bbw.utility;

import id.co.bbw.spec.IsoConstants;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.transaction.Context;

public class IsoUtils {
    public static String printIsoMsg(ISOMsg isoMsg) {
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (int i = 0; i < 125; i++) {
            if (isoMsg.hasField(i)) {
                stringBuilder.append("Field[").append(i).append("]:").append(isoMsg.getString(i)).append("\n");
            }
        }

        return stringBuilder.toString();
    }

    public static String getField(ISOMsg isomessage, int index) {
        String field = "";
        if (isomessage.hasField(index)) field = isomessage.getString(index);
        return field;
    }


    public static ISOSource getISOSource(Context ctx) {
        return ctx.get(IsoConstants.CONTEXT_SRC);
    }

    public static ISOMsg getISORequest(Context ctx) {
        return ctx.get(IsoConstants.CONTEXT_REQ);
    }

    public static ISOMsg getISOResponse(Context ctx) {
        return ctx.get(IsoConstants.CONTEXT_RSP);
    }

    public static String getResponseCode(ISOMsg isoMsg) {
        //String s = ctx.get(CONTEXT_RESPONSE_CODE);
        String s = isoMsg.getString(39);
        if (s == null) s = "";
        return s;
    }

    public static String getResponseMsg(Context ctx) {
        String s = ctx.get(IsoConstants.CONTEXT_RESPONSE_MSG);
        if (s == null) s = "";
        return s;
    }

    public static String getCHANNEL(Context ctx) {
        String s = ctx.get(IsoConstants.CONTEXT_CHANNEL);
        if (s == null) s = "";
        return s.toUpperCase();
    }
}
