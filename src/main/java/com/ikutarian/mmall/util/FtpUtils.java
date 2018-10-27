package com.ikutarian.mmall.util;

import com.ikutarian.mmall.common.Config;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class FtpUtils {

    private static final Logger log = LoggerFactory.getLogger(FtpUtils.class);

    public static boolean uploadFile(List<File> fileList) throws IOException {
        log.info("开始连接FTP服务器，上传图片");
        boolean result = uploadFile(Config.getImgBasePath(), fileList);
        log.info("结束上传，上传结果: {}", result);
        return result;
    }

    private static boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(Config.getFtpIp(), Config.getFtpPort());
            ftpClient.login(Config.getFtpUsername(), Config.getFtpPassword());
        } catch (IOException e) {
            log.error("连接FTP服务器异常", e);
            return false;
        }

        try {
            ftpClient.makeDirectory(remotePath);  // 如果remotePath不存在，就创建一个，当然首先要有权限才行
            ftpClient.changeWorkingDirectory(remotePath);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            for (File file : fileList) {
                FileInputStream fis = new FileInputStream(file);
                ftpClient.storeFile(file.getName(), fis);
                fis.close();
            }
        } catch (IOException e) {
            log.error("文件上传异常", e);
            return false;
        } finally {
            ftpClient.disconnect();
        }

        return true;
    }
}
