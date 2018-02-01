package com.zhou.test.model;

/**
 * @author zqm
 * @since 2017/9/22
 */

public class Model {
    public Class<?> clazz;
    public String text;

    public Model(String text, Class<?> clazz) {
        this.text = text;
        this.clazz = clazz;
    }
}
