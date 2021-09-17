package id.co.bbw.spec;

import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.PostChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;

public class BankChannel extends PostChannel {

    public BankChannel() {
        super();
    }

    public BankChannel(ISOPackager isoPackager) throws Exception {
        super(isoPackager);
        logger.info("BankChannel(ISOPackager isoPackager)");
    }

    public BankChannel(ISOPackager isoPackager, ServerSocket serverSocket) throws Exception {
        super(isoPackager, serverSocket);
        logger.info("BankChannel(ISOPackager isoPackager, ServerSocket serverSocket)");
    }

    public BankChannel(String str, int i, ISOPackager isoPackager) throws Exception {
        super(str, i, isoPackager);
        logger.info("BankChannel(String str, int i, ISOPackager isoPackager)");

    }



    private static final Logger logger = LoggerFactory.getLogger(BankChannel.class);

}
