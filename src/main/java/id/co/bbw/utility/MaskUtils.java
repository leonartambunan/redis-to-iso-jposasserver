package id.co.bbw.utility;

/***
 * Masking class
 *
 * @author leonar.tambunan@gmail.com
 * 2021 May
 */

public class MaskUtils {

    /**
     * Password will be mased as ****word
     * Password1 will be mased as *****ord1
     *
     * @param strText String
     * @return String
     */
    public static String maskFirstHalf(String strText) {
        if (strText == null) return "";
        int maskLength = strText.length() / 2;
        StringBuilder sbMaskString = new StringBuilder(strText.length());

        for (int i = 0; i < strText.length(); i++) {
            if (i < maskLength) {
                sbMaskString.append("*");
            } else {
                sbMaskString.append(strText.charAt(i));
            }
        }
        return sbMaskString.toString();
    }

    /**
     * Password will be mased as ********
     * Password1 will be mased as *********
     *
     * @param strText String
     * @return String
     */
    public static String maskAllString(String strText, int start, int end, char maskChar)
            throws Exception {

        if (strText == null || strText.equals(""))
            return "";

        if (start < 0)
            start = 0;

        if (end > strText.length())
            end = strText.length();

        if (start > end)
            throw new Exception("End index cannot be greater than start index");

        int maskLength = end - start;

        if (maskLength == 0)
            return strText;

        StringBuilder sbMaskString = new StringBuilder(maskLength);

        for (int i = 0; i < maskLength; i++) {
            sbMaskString.append(maskChar);
        }

        return strText.substring(0, start)
                + sbMaskString.toString()
                + strText.substring(start + maskLength);
    }

}
