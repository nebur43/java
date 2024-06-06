package com.ocr.payload;

public class Ocr {

    private String name;
    private String ocrResponse;

    public Ocr() {
    }

    public Ocr(String name, String ocrResponse) {
        this.name = name;
        this.ocrResponse = ocrResponse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOcrResponse() {
        return ocrResponse;
    }

    public void setOcrResponse(String ocrResponse) {
        this.ocrResponse = ocrResponse;
    }
}
