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

        ISOMsg isoMsg = new ISOMsg("0200");

        isoMsg.setPackager(genericPackager);
//        String isoMessage = "";
//        isoMessage = "0200F23A400B88A0C008000000000400000016200889999999999930100000000000000010262216270541712216271026102660178000000008000000000390503905200000129491ALTOWBK MOB VIRTUAL ACCOUNT - BBW        JKT  ID3603600101000019991100110014254";
//
//        isoMsg.unpack(isoMessage.getBytes());
//
//        for (int i = 0 ; i<=isoMsg.getMaxField();i++) {
//            if (isoMsg.hasField(i)) {
//                System.out.println(i+":'"+isoMsg.getString(i)+"'");
//            }
//        }

        isoMsg.set(2,"6048209999999999995");
        isoMsg.set(3,"341000");
        isoMsg.set(4,"000010000000");
        isoMsg.set(7,"0904174227");
        isoMsg.set(11,"095428");
        isoMsg.set(12,"174226");
        isoMsg.set(13,"0904");
        isoMsg.set(15,"0619");
        isoMsg.set(18,"6017");
        isoMsg.set(22,"000");
        isoMsg.set(26,"12");
        isoMsg.set(29,"C00000000");
        isoMsg.set(31,"C00000000");
        isoMsg.set(32,"535");
        isoMsg.set(33,"535");
        isoMsg.set(35,"6048209999999999995=9999");
        isoMsg.set(37,"463505877857");
        isoMsg.set(41,"MB000045");
        isoMsg.set(43,"0000000000000000000000000000000000000000");
        isoMsg.set(49,"360");
        isoMsg.set(50,"360");
        isoMsg.set(52,"0000000000000000");
        isoMsg.set(63,"1099999");
        isoMsg.set(102,"999999999999");
        isoMsg.set(104,"0000147610368872");

        //isoMsg.unset(61);


        byte[] s = isoMsg.pack();

        System.out.println(new String(s));

//        isoMsg.unpack(s);
//
//        for (int i = 0 ; i<=isoMsg.getMaxField();i++) {
//            if (isoMsg.hasField(i)) {
//                System.out.println(i+":'"+isoMsg.getString(i)+"'");
//            }
//        }
    }
}

