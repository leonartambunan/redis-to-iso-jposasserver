package id.co.bbw.utility;

import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class StringUtils {
    public static final int STR_PAD_LEFT = 1;
    public static final int STR_PAD_RIGHT = 2;
    public static final int STR_PAD_BOTH = 3;

    public static String padSpaceLeft(String input, int pad_length) {
        return pad(input, pad_length, " ", STR_PAD_LEFT);
    }

    public static String padLeft(String input, int pad_length, String pad_string) {
        return pad(input, pad_length, pad_string, STR_PAD_LEFT);
    }


    public static String padRight(String input, int pad_length, String pad_string) {
        return pad(input, pad_length, pad_string, STR_PAD_RIGHT);
    }

    private static String padMaker(int pad_length, int input_len, String pad_string) {
        String pad_str = "";
        int diff = pad_length - input_len;
        for (int i = 0; i < diff; i++) pad_str += pad_string;
        if ((pad_str.length() + input_len) > pad_length) pad_str = pad_str.substring(0, diff);
        return pad_str;
    }

    public static String pad(String input, int pad_length, String pad_string, int pad_type) {
        String result;

        if (input==null) {
            input = "";
        }

        if (input.length() < pad_length) {
            int input_len = input.length();
            String lpad_str = "", rpad_str = "";
            if (pad_type == STR_PAD_RIGHT) rpad_str = padMaker(pad_length, input_len, pad_string);
            else if (pad_type == STR_PAD_LEFT) lpad_str = padMaker(pad_length, input_len, pad_string);
            else if (pad_type == STR_PAD_BOTH) {
                int diff = pad_length - input_len;
                int rpad_len = diff / 2, lpad_len = diff - rpad_len;
                lpad_str = padMaker(pad_length, input_len + rpad_len, pad_string);
                rpad_str = padMaker(pad_length, input_len + lpad_len, pad_string);

            }
            result = lpad_str + input + rpad_str;
        } else result = input;

        return result;
    }

    public static String printArray(Object[] obj) {
        if (obj == null) {
            return null;
        } else {
            StringBuilder buf = new StringBuilder();
            buf.append('{');

            for (int i = 0; i < obj.length; ++i) {
                if (obj[i] != null) {
                    buf.append("\"").append(obj[i]).append("\"");
                } else {
                    buf.append("null");
                }

                if (i < obj.length - 1) {
                    buf.append(", ");
                }
            }

            buf.append('}');

            return buf.toString();
        }
    }
    public static String print2DArray(int[][] obj) {
        if (obj == null) {
            return null;
        } else {
            StringBuilder buf = new StringBuilder();
            buf.append('{');
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    for (int j = 0; j < obj[i].length; j++) {
                        if (i==0 && j==0) {
                            buf.append("[").append(i).append("][").append(j).append("]:").append(obj[i][j]);
                        } else {
                            buf.append(",").append("[").append(i).append("][").append(j).append("]:").append(obj[i][j]);
                        }
                    }
                } else {
                    buf.append("null");
                }
            }
            buf.append('}');
            return buf.toString();
        }
    }

    public static void printXML(Document document) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        System.out.println(writer.toString());
    }


    public static String xmlToString(Document document) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        return (writer.toString());
    }

    /*public static void main(String[] args) {
        int[][] i = new int[3][3];
        i[0][0] = 0;
        i[0][1] = 1;
        i[0][2] = 2;
        i[1][0] = 1;
        i[1][1] = 2;
        i[1][2] = 3;
        i[2][0] = 2;
        i[2][1] = 3;
        i[2][2] = 4;
        System.out.println(print2DArray(i));
    }*/
}
