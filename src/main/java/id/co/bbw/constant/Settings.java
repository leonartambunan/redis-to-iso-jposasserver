package id.co.bbw.constant;

public class Settings {

    public static class Bank {
        public static String BI_CODE = "905";
        public static String SWIFT_CODE = "XXXXX";
        public static String CURRENCY = "360";
    }

    public static class ISO {
        public static int FIELD_SRC_ACCOUNT_NO = 102;
        public static int FIELD_DST_ACCOUNT_NO = 104;
        public static int FIELD_DST_ACCOUNT_NAME = 103;
        public static int FIELD_BANK_CODE = 103;
        public static int FIELD_STAN = 11;
        public static int FIELD_MERCHANT_CODE = 18;
        public static int FIELD_ACQUIRING_CODE = 32;
        public static int FIELD_FORWARDING_CODE = 33;
        public static int FIELD_CATI = 43;
        public static int FIELD_TXN_CURRENCY = 49;
        public static int FIELD_TXN_AMOUNT = 4;
        public static int FIELD_NOTE = 48;
        public static int FIELD_STLMNT_CURRENCY = 50;
        public static int FIELD_STLMNT_FEE = 29;
        public static int FIELD_STLMNT_DATE = 5;
        public static int FIELD_PROC_FEE = 31;
        public static int FIELD_RRN = 37; //retrieval ref number
        public static int FIELD_RC = 39;
        public static int FIELD_TXN_TIME = 7;
    }

    public static class IDENTIFIER {
        public static final String FIELD_SRC_ACCOUNT_NO = "ISO.FIELD.SRC_ACCOUNT_NO";
        public static final String FIELD_DST_ACCOUNT_NO = "ISO.FIELD.DST_ACCOUNT_NO";
        public static final String FIELD_DST_ACCOUNT_NAME = "ISO.FIELD.DST_ACCOUNT_NAME";
        public static final String FIELD_BANK_CODE = "ISO.FIELD.BANK_CODE";
        public static final String FIELD_STAN = "ISO.FIELD.STAN";
        public static final String FIELD_MERCHANT_CODE = "ISO.FIELD.MERCHANT_CODE";
        public static final String FIELD_ACQUIRING_CODE = "ISO.FIELD.ACQUIRING_CODE";
        public static final String FIELD_FORWARDING_CODE = "ISO.FIELD.FORWARDING_CODE";
        public static final String FIELD_CATI = "ISO.FIELD.CATI";
        public static final String FIELD_TXN_CURRENCY = "ISO.FIELDd.TXN_CURRENCY";
        public static final String FIELD_TXN_AMOUNT = "ISO.FIELD.TXN_AMOUNT";
        public static final String FIELD_NOTE = "ISO.FIELD.NOTE";
        public static final String FIELD_STLMNT_CURRENCY = "ISO.FIELD.STLMNT_CURRENCY";
        public static final String FIELD_STLMNT_FEE = "ISO.FIELD.STLMNT_FEE";
        public static final String FIELD_STLMNT_DATE = "ISO.FIELD.STLMNT_DATE";
        public static final String FIELD_PROC_FEE = "ISO.FIELD.PROC_FEE";
        public static final String FIELD_RRN = "ISO.FIELD.RRN";
        public static final String FIELD_RC = "ISO.FIELD.RC";
        public static final String FIELD_TXN_TIME = "ISO.FIELD.TXN_TIME";

        public static final String BANK_BI_CODE = "BANK_BI_CODE";
        public static final String BANK_SWIFT_CODE = "BANK_SWIFT_CODE";
        public static final String DEFAULT_CURRENCY = "BANK_DEFAULT_CURRENCY";
    }
}
