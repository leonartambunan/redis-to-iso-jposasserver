//package id.co.bbw.controller;
//
//import id.co.bbw.service.CbsService;
//import org.jpos.iso.ISOMsg;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//
//
//@RestController
//@RequestMapping("/hello")
//public class HelloController {
//
//    @Autowired
//    CbsService cbsService;
//
//    @GetMapping("/text")
//    public String getHello ()
//    {
//        String result="X";
//        String request = new Date().toString();
//
//        StringBuilder stringBuilder = new StringBuilder();
////        final Document document = createBaseDocument("x");
////        try {
////           ISOMsg isoMsg = messageConverter.convertMxToIsoMessage(document,"0200","491000");
////           isoMsg.setMTI("0200");
////           stringBuilder.append(isoMsgToString(isoMsg));
////           ISOMsg isoResp = cbsService.sendToCBS(isoMsg,4000);
////
////        } catch (Exception | LinkIsDownException | CBSTimeoutException e) {
////            logger.error("e",e);
////        }
//
//        return request+"=="+result+"\n"+stringBuilder.toString();
//    }
//
//
//   public String isoMsgToString(ISOMsg isoMsg) {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < 125; i++) {
//            if (isoMsg.hasField(i)) {
//                stringBuilder.append("Field[").append(i).append("]:").append(isoMsg.getString(i)).append("\n");
//            }
//        }
//
//        return stringBuilder.toString();
//    }
//
////    public ISOMsg sendToCBS(final ISOMsg isoRequest,
////                                                 //final String channel,
////                                                 //final boolean isChannelServer,
////                                                 final int timeout) throws LinkIsDownException, CBSTimeoutException {
////
////        String channel = "fastbank";
////        boolean isChannelServer = false;
////
////        ISOMsg isoResponse=null;
////
////        logger.warn("sendToCBS() is in progress");
////        try {
////            final QMUX mux = NameRegistrar.get("mux."+channel+"-mux");
////            boolean isClientConnected = true;
////
////            ISOServer isoServer;
////
////            if (isChannelServer) {
////                isoServer = ISOServer.getServer(channel);
////                if (mux == null || isoServer == null) {
////                    isClientConnected = false;
////                }
////                else if (isoServer.getActiveConnections() == 0) {
////                    isClientConnected = false;
////                }
////            }
////            if (!isClientConnected) {
////                logger.info("mux == null || isoServer == null || isoServer.getActiveConnections() == 0");
////                isoRequest.setResponseMTI();
////                isoRequest.set(39, "68");
////                return isoRequest;
////            }
////            try {
////                logger.info("KEY ISOMsg " + mux.getKey(isoRequest));
////                isoResponse = mux.request(isoRequest, timeout);
////                if (!mux.isConnected() && isoResponse == null) {
////                    logger.info("r == link down ");
////                    throw new LinkIsDownException();
////                }
////                if (isoResponse == null) {
////                    logger.info("r == null ");
////                    throw new CBSTimeoutException("isoResponse was not received within defined period");
////                }
////                logger.info("ISO Response : " + isoResponse.getMTI());
////                String rc = isoResponse.getString(39);
////                logger.info("ISO ResponseCode : " + rc);
////                if (!"00".equals(rc)) {
////                    if ("channel_aj".equals(channel) && "63".equals(rc)) {
////                        logger.info("Replace RC 63 -> 91 ");
////                        rc = "91";
////                    }
////                    if ("channel_aj".equals(channel)) {
////                        if ("91".equals(rc)) {
////                            logger.info("Replace RC 91 -> 63 ");
////                            rc = "63";
////                        }
////                        else if ("57".equals(rc)) {
////                            logger.info("Replace RC " + rc + " -> 09 ");
////                            rc = "09";
////                        }
////                        else if ("68".equals(rc)) {
////                            logger.info("Replace RC " + rc + " -> 091 ");
////                            rc = "091";
////                        }
////                    }
////                    if ("".equals(rc)) {
////                        isoResponse.set(39, "06");
////                    }
////                    else {
////                        isoResponse.set(39, rc);
////                    }
////                }
////                return isoResponse;
////            } catch (Exception ex) {
////                logger.warn(ex.getMessage(),ex);
////                isoRequest.set(39, "12");
////            }
////
////        }
////
////        catch (Exception ex2) {
////            logger.warn(ex2.getMessage(),ex2);
////            isoRequest.set(39, "12");
////        }
////
////        return isoResponse;
////    }
//
//    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
//
//}
