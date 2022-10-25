package com.dss.rest.exception.apihandler;

public class ApiErrorBody<B> {

    private String path;
    private String httpCode;
    private B body;

    public ApiErrorBody(String path, String httpCode, B body) {
        this.path = path;
        this.httpCode = httpCode;
        this.body = body;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }

    public B getBody() {
        return body;
    }

    public void setBody(B body) {
        this.body = body;
    }
}
