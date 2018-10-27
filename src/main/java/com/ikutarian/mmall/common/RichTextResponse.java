package com.ikutarian.mmall.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RichTextResponse {

    private boolean success;
    @JsonProperty("msg")
    private String message;
    @JsonProperty("file_path")
    private String filePath;

    public RichTextResponse(boolean success, String message, String filePath) {
        this.success = success;
        this.message = message;
        this.filePath = filePath;
    }

    public static RichTextResponse success(String filePath) {
        return new RichTextResponse(true, "图片上传成功", filePath);
    }

    public static RichTextResponse error(String message) {
        return new RichTextResponse(true, message, null);
    }
}
