package com.nxin.base.common.threadpool.constant;

/**
 * Created by zhuhua
 * mail:mail.nxin.com
 * date: 2016/5/20.
 */
public enum ThreadPoolTypeEnum {
    /**
     * 普通工作线程池
     */
    THREAD_TYPE_WORK(0),
    /**
     * 接口请求线程池
     */
    THREAD_TYPE_SIMPLE_HTTP(1),
    /**
     * 文件传输线程池
     */
    THREAD_TYPE_FILE_HTTP(2),
    /**
     * 其他线程池
     */
    THREAD_TYPE_OTHERS(3),

    /**
     * 队列线程
     */
    THREAD_TYPE_QUEUE(4),
    /**
     * 重连线程池
     */
    THREAD_TYPE_RECONNECTION(5);

    ThreadPoolTypeEnum(int v) {
        this._value = v;
    }

    private int _value;

    public int getValue() {
        return _value;
    }
}
