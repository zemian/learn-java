package com.zemian.hellojava.data.dao;

/**
 * Support data pagination input parameter.
 *
 * @Author Zemian Deng 2017
 */
public class Paging {
    public static final int DEFAULT_SIZE = 20;
    private int offset;
    private int size;

    public Paging() {
        this(0, DEFAULT_SIZE);
    }

    public Paging(int offset, int size) {
        this.offset = offset;
        this.size = size;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
