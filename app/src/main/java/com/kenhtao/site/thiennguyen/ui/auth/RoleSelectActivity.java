package com.kenhtao.site.thiennguyen.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.kenhtao.site.thiennguyen.R;
import com.kenhtao.site.thiennguyen.base.BaseActivity;
import com.kenhtao.site.thiennguyen.data.repository.FakeAuthRepository;
import com.kenhtao.site.thiennguyen.ui.main.MainActivity;

public class RoleSelectActivity extends BaseActivity {

    private CardView cardDonor, cardNeedHelp, cardVolunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);

        initViews();
        setupListeners();
    }

    private void initViews() {
        cardDonor = findViewById(R.id.cardDonor);
        cardNeedHelp = findViewById(R.id.cardNeedHelp);
        cardVolunteer = findViewById(R.id.cardVolunteer);
    }

    private void setupListeners() {
        cardDonor.setOnClickListener(v -> selectRole("donor"));
        cardNeedHelp.setOnClickListener(v -> selectRole("recipient"));
        cardVolunteer.setOnClickListener(v -> selectRole("volunteer"));
    }

    private void selectRole(String role) {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        if (userId != null) {

            FakeAuthRepository authRepository = new FakeAuthRepository(this);
            authRepository.updateUserRole(userId, role);


            prefs.edit()
                    .putString("user_role", role)
                    .apply();


            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {

            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
        }
    }

}
