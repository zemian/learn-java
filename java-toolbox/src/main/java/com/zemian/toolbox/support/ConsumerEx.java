package com.zemian.toolbox.support;

public interface ConsumerEx<T> {
    void accept(T data) throws Exception;
}
