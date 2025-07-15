package com.kenhtao.site.thiennguyen.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kenhtao.site.thiennguyen.R;
import com.kenhtao.site.thiennguyen.base.BaseActivity;
import com.kenhtao.site.thiennguyen.data.model.User;
import com.kenhtao.site.thiennguyen.data.repository.AuthRepository;
import com.kenhtao.site.thiennguyen.data.repository.FakeAuthRepository;
import com.kenhtao.site.thiennguyen.ui.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnRegister;

    private FakeAuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initViews();


     authRepository = new FakeAuthRepository(getApplicationContext());


        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (validateInput(email, password)) {
                doLogin(email, password);
            }
        });


        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void initViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Email không được để trống");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Mật khẩu không được để trống");
            return false;
        }
        return true;
    }

    private void doLogin(String email, String password) {
        showLoading();

        android.util.Log.d("LoginDebug", "Gửi login với email: " + email + ", password: " + password);

        authRepository.login(email, password, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                hideLoading();
                User user = response.body();

                if (user != null) {
                    android.util.Log.d("LoginDebug", "Login thành công - user ID: " + user.getId() + ", role: " + user.getRole());

                    SharedPreferences.Editor editor = getSharedPreferences("AppPrefs", MODE_PRIVATE).edit();
                    editor.putString("user_id", user.getId());
                    editor.putString("user_role", user.getRole());
                    editor.apply();

                    if (user.getRole() == null || user.getRole().isEmpty()) {
                        android.util.Log.d("LoginDebug", "Chuyển đến RoleSelectActivity do chưa có role.");
                        startActivity(new Intent(LoginActivity.this, RoleSelectActivity.class));
                    } else {
                        android.util.Log.d("LoginDebug", "Chuyển đến MainActivity với role: " + user.getRole());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }

                    finish();
                } else {
                    android.util.Log.d("LoginDebug", "Login thất bại - Không tìm thấy user.");
                    showToast("Sai tài khoản hoặc mật khẩu");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                hideLoading();
                android.util.Log.e("LoginDebug", "Lỗi trong quá trình login: " + t.getMessage(), t);
                showToast("Lỗi fake login: " + t.getMessage());
            }
        });
    }

}