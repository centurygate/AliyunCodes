package log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by free on 12/11/16.
 */
public class SystemLog {

    static {
        PropertyConfigurator.configure("/opt/log4j-measureserver.properties");
    }

    public static void log(Object loginfo) {
        logger.info(loginfo);
    }

    static Logger logger = Logger.getLogger(SystemLog.class);
}
