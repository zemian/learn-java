package com.zemian.hellojava.web.controller.api;

import java.util.ArrayList;
import java.util.List;

public class ApiResult<T> {
    public static enum Status {
        OK, FAILED;
    }
    private Status status;
    private List<String> messages = new ArrayList<>();
    private T result;

    public ApiResult() {
    }

    public static ApiResult<Object> error(String error) {
        ApiResult<Object> ret = new ApiResult<>();
        ret.setStatus(Status.FAILED);
        ret.getMessages().add(error);
        return ret;
    }

    public static <T> ApiResult<T> result(T result) {
        ApiResult<T> ret = new ApiResult<>();
        ret.setStatus(Status.OK);
        ret.setResult(result);
        return ret;
    }

    public static <T> ApiResult<T> result(T result, String message) {
        ApiResult<T> ret = new ApiResult<>();
        ret.setStatus(Status.OK);
        ret.setResult(result);
        ret.getMessages().add(message);
        return ret;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "status=" + status +
                (messages.size() > 0 ?", messages=" + messages : "") +
                (result != null ? ", messages=" + result + ", type=" + result.getClass() : "") +
                '}';
    }
}
