package com.ikutarian.mmall.service.impl;

import com.google.common.collect.Lists;
import com.ikutarian.mmall.service.FileService;
import com.ikutarian.mmall.util.FtpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("fileService")
public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("开始上传文件，文件名:{}，路径:{}，新文件名:{}", fileName, path, uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);  // 文件已经上传到本地硬盘，存在path下
            boolean success = FtpUtils.uploadFile(Lists.newArrayList(targetFile));  // 将targetFile上传到FTP服务器
            if (success) {
                targetFile.delete();  // 删除targetFile
                return targetFile.getName();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.error("上传文件异常", e);
            return null;
        }
    }
}
