package com.ocr;

import java.util.Map;

public class Document {

    private String name;
    private String type;
    private Map<String, String> ocrProperties;
    private String outputFormat;
    private String content;

    public Document() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getOcrProperties() {
        return ocrProperties;
    }

    public void setOcrProperties(Map<String, String> ocrProperties) {
        this.ocrProperties = ocrProperties;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}