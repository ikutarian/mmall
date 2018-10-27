package com.ikutarian.mmall.common;

import com.ikutarian.mmall.util.PropertiesUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class Config {

    public static String getImageServerHost() {
        return PropertiesUtils.getProperty(Const.Props.IMG_SERVER_HOST);
    }

    public static String getImgBasePath() {
        return PropertiesUtils.getProperty(Const.Props.IMG_BASE_PATH);
    }

    public static String getFtpIp() {
        return PropertiesUtils.getProperty(Const.Props.FTP_IP);
    }

    public static int getFtpPort() {
        String port = PropertiesUtils.getProperty(Const.Props.FTP_PORT);
        return NumberUtils.toInt(port);
    }

    public static String getFtpUsername() {
        return PropertiesUtils.getProperty(Const.Props.FTP_USERNAME);
    }

    public static String getFtpPassword() {
        return PropertiesUtils.getProperty(Const.Props.FTP_PASSWORD);
    }
}
