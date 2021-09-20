package id.co.bbw.controller;

import org.apache.logging.log4j.util.Strings;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.q2.iso.QMUX;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/",
        consumes = MediaType.TEXT_PLAIN_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        method = {RequestMethod.GET, RequestMethod.POST})
public class IsoController {

    private final QMUX qmux;

    public IsoController(QMUX qmux) {
        this.qmux = qmux;
    }

    @PostMapping("/isorequest")
    public Map<String, String> isorequest(@RequestBody String request){
        Map<String, String> result = new LinkedHashMap<>();

        JSONParser parser = new JSONParser();
        JSONObject jsonRequest;
        try {
            jsonRequest = (JSONObject) parser.parse(request);
        } catch (ParseException e) {
            e.printStackTrace();
            result.put("error","Request contains malformed JSON");
            return result;
        }

        logger.info("Incoming request:");
        logger.info(request);

        ISOMsg isoResponse = null;
        try {
            ISOMsg msgRequest = new ISOMsg((String) jsonRequest.get("0"));

            for (int x = 2; x<128;x++) {
                String val = (String) jsonRequest.get(x+"");
                if (Strings.isNotEmpty(val)) {
                    msgRequest.set(x, val);
                }
            }

            isoResponse = qmux.request(msgRequest, 10 * 1000); //10 seconds timeout

            if(isoResponse == null) {
                result.put("error", "timeout");
                return result;
            }

            result.put("0",isoResponse.getMTI());

            int j = isoResponse.getMaxField();
            for (int i = 1; i<=j ; i++) {
                String value =  (String) isoResponse.getValue(i);
                if (Strings.isNotEmpty(value)) {
                    result.put(i + "", value );
                }
            }
        } catch (ISOException e) {
            logger.error(e.getMessage());
            result.put("error", e.getMessage());
        }

        return result;
    }

    private static final Logger logger = LoggerFactory.getLogger(IsoController.class);
}

