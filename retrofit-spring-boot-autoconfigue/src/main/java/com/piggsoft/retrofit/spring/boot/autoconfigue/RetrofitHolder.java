/*
 *
 * Copyright (C) 1999-2016 IFLYTEK Inc.All Rights Reserved.
 * History：
 * Version   Author      Date                              Operation
 * 1.0       yaochen4    2017/1/21                           Create
 */
package com.piggsoft.retrofit.spring.boot.autoconfigue;

import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yaochen4
 * @version 1.0
 * @create 2017/1/21
 * @since 1.0
 */
public class RetrofitHolder {

    private Map<String, Retrofit> map = new HashMap<String, Retrofit>();

    public Map<String, Retrofit> getMap() {
        return map;
    }

    public void setMap(Map<String, Retrofit> map) {
        this.map = map;
    }
}
