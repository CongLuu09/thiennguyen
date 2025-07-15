package com.kenhtao.site.thiennguyen.data.repository;

import com.kenhtao.site.thiennguyen.data.api.ApiClient;
import com.kenhtao.site.thiennguyen.data.api.ApiService;
import com.kenhtao.site.thiennguyen.data.model.User;

import retrofit2.Call;

public class AuthRepository {

    private final ApiService apiService;

    public AuthRepository() {
        this.apiService = ApiClient.getApiService();
    }


    public Call<User> login(String email, String password) {
        return apiService.login(email, password);
    }


    public Call<User> register(User user) {
        return apiService.register(user);
    }
}
