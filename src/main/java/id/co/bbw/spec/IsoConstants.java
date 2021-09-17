package id.co.bbw.spec;

public class IsoConstants {

    public static final String CONTEXT_CHANNEL      = "CHANNEL_NAME";
    public static final String CONTEXT_IS_SERVER    = "IS_SERVER";
    public static final String CONTEXT_RSP          = "RSP";
    public static final String CONTEXT_RESPONSE_CODE= "RC";
    public static final String CONTEXT_RESPONSE_MSG = "RC_MSG";
    public static final String CONTEXT_REQ          = "REQ";
    public static final String CONTEXT_SRC          = "SRC";
    public static final String CONTEXT_DIR          = "DIR";
    public static final String CONTEXT_TXN_MGR      = "TXNMGR";

    public static final String NETWORK_REQUEST      = "0800";
    public static final String NETWORK_RESPONSE     = "0810";
    public static final String FINANCIAL_REQUEST    = "0200";
    public static final String FINANCIAL_RESPONSE   = "0210";
    public static final String REVERSAL_REQUEST     = "0420";
    public static final String REVERSAL_REPEAT      = "0421";
    public static final String REVERSAL_RESPONSE    = "0430";

    static class ResponseCode {
        public static final String RC_APPROVED = "00";
        public static final String RC_REFER_TO_CARD_ISSUER = "01";//	Refer to card issuer
        public static final String RC_ERROR = "06";
        public static final String RC_INVALID_TRANSACTION = "12"; //Invalid transaction
        public static final String RC_INVALID_AMOUNT = "13"; //Invalid amount
        public static final String RC_INVALID_CARD_NUMBER = "14"; //Invalid card number (no such number)
        public static final String RC_DESTINATION_ACCOUNT_NOT_VALID = "25";//norek tidak ditemukan
        public static final String RC_MESSAGE_FORMAT_ERROR = "30";
        public static final String RC_INVALID_BANK = "31"; // Bank not support by switch
        public static final String RC_LIMIT_PIN_RETRIES = "38"; //Allowable pin retries exceeded, max percobaan pin
        public static final String RC_LOST_CARD = "41";
        public static final String RC_STOLEN_CARD = "43";
        public static final String RC_INSUFFICIENT_AMOUNT = "51"; //saldo kurang
        public static final String RC_NO_CHECKING_ACCOUNT_DECLINE = "52";//No Checking Account Decline - tidak ada link account
        public static final String RC_CARD_WITH_NOT_RELATED_ACCOUNT = "53";//kartu tidak link ke nomor rekening manapun
        public static final String RC_EXPIRED_CARD = "54";
        public static final String RC_INCORRECT_PIN = "55";
        public static final String RC_NO_CARD_RECORD = "56";
        public static final String RC_LIMIT_AMOUNT_WITHDRAWAL = "61";//Exceeds withdrawal amount limit, melebihi batas jumlah penarikan
        public static final String RC_CARD_RESTRICTED = "62";//Restricted card, kartu dibatasi
        public static final String RC_FREQUENCY_LIMIT = "65";//65 Exceeds withdrawal frequency limit, Melebihi batas frekuensi penarikan
        public static final String RC_RESPONSE_RECEIVED_TOO_LATE = "68";//Response received too late
        public static final String RC_LIMIT_NUMBER_PIN_RETRIES = "75"; // Allowable number of PIN tries exceeded - Jumlah diijinkan PIN mencoba melebihi
        public static final String RC_CORE_BANKING_PROBLEM = "91";
        public static final String RC_DUPLICATE_TRANSMISSION = "94";
        public static final String RC_INVALID_PIN_TRANSLATION = "96"; // Invalid pin translation
    }

    static class Field {
        public static final String PRIMARY_ACCOUNT_NUMBER = "2";
        public static final String PROCESSING_CODE = "3";
        public static final String AMOUNT = "4";
        public static final String TRANSMISSION_DATE_TIME = "7";
        public static final String TRACE_NUMBER = "11";
        public static final String LOCAL_TRANSACTION_TIME = "12";
        public static final String LOCAL_TRANSACTION_DATE = "13";
        public static final String EXP_DATE = "14";
        public static final String POS = "22";
        public static final String NII = "24";
        public static final String TRACK_2 = "35";
        public static final String REFERENCE_NUMBER = "37";
        public static final String RESPONSE_CODE = "39";
        public static final String TERMINAL_ID = "41";
        public static final String MERCHANT_ID = "42";
        public static final String CURRENCY = "49";
        public static final String BATCH_NUMBER = "60";
        public static final String NETWORK_MANAGEMENT_INFO = "70";
        public static final String ACC_ID_1 = "F102"; // debited acc
        public static final String ACC_ID_2 = "F102"; // credited acc
    }

    static class NetworkAction {
        public static final String NETWORK_LOGON = "001";
        public static final String NETWORK_LOGOFF = "002";
        public static final String NETWORK_SAF = "060";
        public static final String NETWORK_SIGN_ON = "061";
        public static final String NETWORK_SIGN_OFF = "062";
        public static final String NETWORK_KEY_CHANGE = "101";
        public static final String NETWORK_NEW_KEY = "101";
        public static final String NETWORK_CUT_OFF = "201";
        public static final String NETWORK_ECHO = "301";
    }

//    public static ISOSource getISOSource(Context ctx) {
//        return ctx.get(CONTEXT_SRC);
//    }
//
//    public static ISOMsg getISORequest(Context ctx) {
//        return ctx.get(CONTEXT_REQ);
//    }
//
//    public static ISOMsg getISOResponse(Context ctx) {
//        return ctx.get(CONTEXT_RSP);
//    }
//
//    public static String getResponseCode(Context ctx) {
//        String s = ctx.get(CONTEXT_RESPONSE_CODE);
//        if (s == null) s = "";
//        return s;
//    }
//
//    public static String getResponseMsg(Context ctx) {
//        String s = ctx.get(CONTEXT_RESPONSE_MSG);
//        if (s == null) s = "";
//        return s;
//    }
//
//    public static String getCHANNEL(Context ctx) {
//        String s = ctx.get(CONTEXT_CHANNEL);
//        if (s == null) s = "";
//        return s.toUpperCase();
//    }

//    public static final DefaultSpecification DEFAULT_SPEC = new DefaultSpecification("DEFAULT");
//    public static final BankSpecification BANK_SPECIFICATION = new BankSpecification("neo");

}
