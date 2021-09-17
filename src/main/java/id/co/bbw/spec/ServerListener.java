package id.co.bbw.spec;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.transaction.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListener implements ISORequestListener, Configurable {

    private Configuration cfg;
    private final static Logger logger = LoggerFactory.getLogger(ServerListener.class);

    public ServerListener() {
        logger.info("constructor");
    }

    @Override
    public boolean process(ISOSource isos, ISOMsg isomsg) {
        logger.info("process(ISOSource,ISOMsg)");
        logger.info("Channel Name :{}",cfg.get("channel_name"));

        Context ctx = new Context();
        Space sp = SpaceFactory.getSpace();
        ctx.put(IsoConstants.CONTEXT_IS_SERVER, true);
        ctx.put(IsoConstants.CONTEXT_CHANNEL, cfg.get("channel_name"));
        ctx.put(IsoConstants.CONTEXT_SRC, isos);
        ctx.put(IsoConstants.CONTEXT_REQ, isomsg);
        ctx.put(IsoConstants.CONTEXT_DIR, "OUT");
        sp.out(IsoConstants.CONTEXT_TXN_MGR, ctx);
        return true;
    }

    @Override
    public void setConfiguration(Configuration c) {
        this.cfg = c;
    }

}
