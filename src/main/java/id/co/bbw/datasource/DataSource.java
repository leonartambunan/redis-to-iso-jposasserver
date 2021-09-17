package id.co.bbw.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import id.co.bbw.utility.MaskUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/***
 * Data source to PostgreSQL using zaxxer's Hikari connection pooling
 * @author leonartambunan@gmail.com
 *
 */
public class DataSource {
    private final static Logger logger = LoggerFactory.getLogger(DataSource.class);
    private static DataSource INSTANCE;
    private final HikariDataSource hikariDataSource;

    private DataSource() throws Exception {
        //String configFile = ConfigurationUtility.getConfigIsoIniFileLocation();
        //File file = new File(configFile);
//        InputStream inputStream = new FileInputStream(file);
//        Properties props = new Properties();
//        props.load(inputStream);

//        String dbserver = props.getProperty("DB_2");
//        String dbuser = props.getProperty("DB_5");
//        String dbpass = props.getProperty("DB_1");
//        String dbname = props.getProperty("DB_4");
//        String dbport = props.getProperty("DB_3");
//        dbserver = IsoIniEncryption.decrypt(dbserver);
//        dbuser = IsoIniEncryption.decrypt(dbuser);
//        dbpass = IsoIniEncryption.decrypt(dbpass);
//        dbname = IsoIniEncryption.decrypt(dbname);
//        dbport = IsoIniEncryption.decrypt(dbport);

        String dbserver = "localhost";
        String dbuser = "postgres";
        String dbpass = "postgres";
        String dbport = "5432";
        String dbname = "dev_online";

        logger.info("dbserver={}", MaskUtils.maskFirstHalf(dbserver));
        logger.info("dbuser={}", MaskUtils.maskFirstHalf(dbuser));
        logger.info("dbpass={}", MaskUtils.maskFirstHalf(dbpass));
        logger.info("dbname={}", MaskUtils.maskFirstHalf(dbname));
        logger.info("dbport={}", MaskUtils.maskFirstHalf(dbport));

        HikariConfig config = new HikariConfig();
        String jdbc = "jdbc:postgresql://" + dbserver + ":" + dbport + "/" + dbname;
        config.setJdbcUrl(jdbc);
        config.setUsername(dbuser);
        config.setPassword(dbpass);
        config.setDriverClassName("org.postgresql.Driver");


//        String maxPool = props.getProperty("MAX_POOL", "30");
//        String minPool = props.getProperty("MIN_POOL", "5");
//        config.setMaximumPoolSize(Integer.parseInt(maxPool));
//        config.setMinimumIdle(Integer.parseInt(minPool));
        config.setConnectionTimeout(10000); //10 seconds
        config.setIdleTimeout(15000); //15 seconds
        config.setAutoCommit(true);
        config.setMaxLifetime(30000);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        hikariDataSource = new HikariDataSource(config);
    }

    public static DataSource getInstance() throws Exception {
        if (INSTANCE == null) {
            INSTANCE = new DataSource();
        }

        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        if (hikariDataSource == null) {
            throw new SQLException("hikariDataSource is null");
        }
        return hikariDataSource.getConnection();
    }
}
