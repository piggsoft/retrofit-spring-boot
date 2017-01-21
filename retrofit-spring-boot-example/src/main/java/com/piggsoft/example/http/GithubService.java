/*
 *
 * Copyright (C) 1999-2016 IFLYTEK Inc.All Rights Reserved.
 * History：
 * Version   Author      Date                              Operation
 * 1.0       yaochen4    2017/1/21                           Create
 */
package com.piggsoft.example.http;

import com.piggsoft.retrofit.spring.boot.autoconfigue.RetrofitService;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * @author yaochen4
 * @version 1.0
 * @create 2017/1/21
 * @since 1.0
 */
@RetrofitService("github")
public interface GithubService {

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
}
