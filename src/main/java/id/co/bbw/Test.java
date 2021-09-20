package id.co.bbw;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

import java.io.File;
import java.io.FileInputStream;

public class Test {
    public static void main(String[] args) throws Exception {
        String userHome = System.getProperty("user.dir");
        File f = new File(userHome+"/"+"cfg/packager.xml");
        GenericPackager genericPackager = new GenericPackager(new FileInputStream(f));

        ISOMsg isoMsg = new ISOMsg();

        isoMsg.setPackager(genericPackager);
        String isoMessage = "";
        isoMessage = "0200F23A400B88A0C008000000000400000016200889999999999930100000000000000010262216270541712216271026102660178000000008000000000390503905200000129491ALTOWBK MOB VIRTUAL ACCOUNT - BBW        JKT  ID3603600101000019991100110014254";

        isoMsg.unpack(isoMessage.getBytes());

        for (int i = 0 ; i<=isoMsg.getMaxField();i++) {
            if (isoMsg.hasField(i)) {
                System.out.println(i+":'"+isoMsg.getString(i)+"'");
            }
        }

        isoMsg.set(29,"C00000000");
        isoMsg.set(3,"C00000000");
    }
}

