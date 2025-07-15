package com.kenhtao.site.thiennguyen.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kenhtao.site.thiennguyen.R;
import com.kenhtao.site.thiennguyen.base.BaseActivity;
import com.kenhtao.site.thiennguyen.data.model.User;
import com.kenhtao.site.thiennguyen.data.repository.FakeAuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    private EditText edtName, edtEmail, edtPassword;
    private Button btnRegister;

    private FakeAuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authRepository = new FakeAuthRepository(getApplicationContext());


        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (validate(name, email, password)) {
                doRegister(name, email, password);
            }
        });
    }

    private boolean validate(String name, String email, String password) {
        if (TextUtils.isEmpty(name)) {
            edtName.setError("Họ tên không được trống");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Email không được trống");
            return false;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            edtPassword.setError("Mật khẩu phải ít nhất 6 ký tự");
            return false;
        }
        return true;
    }

    private void doRegister(String name, String email, String password) {
        showLoading();


        User newUser = new User(null, name, email, password, null);

        authRepository.register(newUser, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();


                    getSharedPreferences("AppPrefs", MODE_PRIVATE)
                            .edit()
                            .putString("user_id", user.getId())
                            .apply();


                    showToast("Đăng ký thành công. Vui lòng đăng nhập.");
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    showToast("Đăng ký thất bại: Email đã tồn tại");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                hideLoading();
                showToast("Lỗi: " + t.getMessage());
            }
        });
    }

}
