//package id.nio.bifast.controller;
//
//import com.jayway.jsonpath.Configuration;
//import com.jayway.jsonpath.DocumentContext;
//import com.jayway.jsonpath.JsonPath;
//import id.nio.bifast.constant.TxnDirection;
//import id.nio.bifast.datasource.DataSource;
//import id.nio.bifast.utility.IsoUtils;
//import id.nio.bifast.utility.JsonUtils;
//import id.nio.bifast.utility.XmlUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.jpos.iso.ISOMsg;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.w3c.dom.Document;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Unmarshaller;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.text.SimpleDateFormat;
//import java.time.Instant;
//import java.util.Calendar;
//
//import static id.nio.bifast.constant.Settings.ISO.*;
//import static id.nio.bifast.converter.SQL.*;
//
//public class BaseController {
//
//    private final SimpleDateFormat rangeDateFormat = new SimpleDateFormat("yyyy-MM");
//
//
//
//    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
//}
