package com.czl.exercisesgeneration.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * 提供文件IO相关服务的接口
 */
public interface FileOperation {

    /**
     * 获得对应的字符缓冲输入流
     * @param string 文件路径
     * @return 对应的字符缓冲输入流
     */
    public abstract BufferedReader getBufferedReader(String string);

    /**
     * 获得对应的字符缓冲输出流
     * @param string 文件路径
     * @return 对应的字符缓冲输出流
     */
    public abstract BufferedWriter getBufferedWriter(String string);

}
