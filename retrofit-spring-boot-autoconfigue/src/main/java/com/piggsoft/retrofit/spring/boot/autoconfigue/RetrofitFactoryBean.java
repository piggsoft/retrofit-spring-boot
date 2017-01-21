/*
 *
 * Copyright (C) 1999-2016 IFLYTEK Inc.All Rights Reserved.
 * History：
 * Version   Author      Date                              Operation
 * 1.0       yaochen4    2017/1/20                           Create
 */
package com.piggsoft.retrofit.spring.boot.autoconfigue;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.annotation.AnnotationUtils;
import retrofit2.Retrofit;

/**
 * @author yaochen4
 * @version 1.0
 * @create 2017/1/20
 * @since 1.0
 */
public class RetrofitFactoryBean<T> implements FactoryBean<T> {


    public Class<T> interfaceClass;

    private RetrofitHolder holder;

    @Override
    public T getObject() throws Exception {
        RetrofitService retrofitService = AnnotationUtils.findAnnotation(interfaceClass, RetrofitService.class);
        String name = retrofitService.value();
        Retrofit retrofit = holder.getMap().get(name);
        if (retrofit == null) {
            throw new IllegalArgumentException("没有对应的Retrofit name");
        }
        return retrofit.create(interfaceClass);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public RetrofitHolder getHolder() {
        return holder;
    }

    public void setHolder(RetrofitHolder holder) {
        this.holder = holder;
    }
}
