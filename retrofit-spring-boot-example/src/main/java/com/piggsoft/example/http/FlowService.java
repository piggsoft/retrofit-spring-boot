/*
 *
 * Copyright (C) 1999-2016 IFLYTEK Inc.All Rights Reserved.
 * History：
 * Version   Author      Date                              Operation
 * 1.0       yaochen4    2017/1/20                           Create
 */
package com.piggsoft.example.http;

import com.piggsoft.retrofit.spring.boot.autoconfigue.RetrofitService;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author yaochen4
 * @version 1.0
 * @create 2017/1/20
 * @since 1.0
 */
@RetrofitService("jac")
public interface FlowService {

    @GET("operator/flow/{imsi}")
    Call<Object> getFlow(@Path("imsi") String imsi);

}
