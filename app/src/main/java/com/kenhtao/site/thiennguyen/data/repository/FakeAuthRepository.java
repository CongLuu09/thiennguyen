package com.kenhtao.site.thiennguyen.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kenhtao.site.thiennguyen.R;
import com.kenhtao.site.thiennguyen.data.model.HelpRequest;
import com.kenhtao.site.thiennguyen.data.model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FakeAuthRepository {

    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_USERS = "users";
    private static final String TAG = "FakeAuthRepository";

    private final SharedPreferences prefs;
    private final Gson gson = new Gson();

    public FakeAuthRepository(Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Thêm user mẫu nếu danh sách rỗng
        if (getUserList().isEmpty()) {
            User defaultUser = new User("1", "User A", "user@example.com", "123456", "donor");
            saveUser(defaultUser);
        }
    }

    private List<User> getUserList() {
        String json = prefs.getString(KEY_USERS, "");
        if (json.isEmpty()) return new ArrayList<>();

        Type type = new TypeToken<List<User>>() {}.getType();
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi parse JSON danh sách user: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveUser(User user) {
        List<User> users = getUserList();
        users.add(user);
        saveUserList(users);
    }

    private void saveUserList(List<User> users) {
        prefs.edit().putString(KEY_USERS, gson.toJson(users)).apply();
    }

    public void register(User user, Callback<User> callback) {

        new Handler(Looper.getMainLooper()).postDelayed(() -> {


            if (user.getEmail() == null || user.getPassword() == null) {
                Log.d(TAG, "Đăng ký thất bại: email hoặc password null");
                callback.onResponse(null, Response.success(null));
                return;
            }


            List<User> users = getUserList();


            for (User u : users) {
                if (user.getEmail() != null && u.getEmail() != null &&
                        user.getEmail().equalsIgnoreCase(u.getEmail())) {
                    Log.d(TAG, "Đăng ký thất bại: Email đã tồn tại - " + user.getEmail());
                    callback.onResponse(null, Response.success(null));
                    return;
                }
            }



            user.setId(String.valueOf(users.size() + 1));
            saveUser(user);


            Log.d(TAG, "Đăng ký thành công: " + user.getEmail());
            callback.onResponse(null, Response.success(user));
        }, 1000);
    }


    public void login(String email, String password, Callback<User> callback) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            List<User> users = getUserList();

            Log.d(TAG, "Đang login với: " + email + " / " + password);
            Log.d(TAG, "Tổng số user: " + users.size());

            for (User user : users) {
                Log.d(TAG, "So sánh với user: " + user.getEmail());
                if (email != null && password != null &&
                        email.equalsIgnoreCase(user.getEmail()) &&
                        password.equals(user.getPassword())) {
                    Log.d(TAG, "Login thành công với user: " + user.getEmail());
                    callback.onResponse(null, Response.success(user));
                    return;
                }
            }

            Log.d(TAG, "Login thất bại - Không tìm thấy user khớp.");
            callback.onResponse(null, Response.success(null));
        }, 1000);
    }

    public void updateUserRole(String userId, String newRole) {
        List<User> users = getUserList();

        for (User user : users) {
            if (user.getId().equals(userId)) {
                user.setRole(newRole);
                break;
            }
        }

        saveUserList(users);
    }


    public static List<HelpRequest> getHelpRequests() {
        List<HelpRequest> list = new ArrayList<>();
        list.add(new HelpRequest("1", "Bé An cần giúp đỡ học phí",
                "Gia đình khó khăn, cần hỗ trợ đóng học phí lớp 6",
                R.drawable.help1));

        list.add(new HelpRequest("2", "Cụ bà sống neo đơn",
                "Cần thực phẩm và hỗ trợ chăm sóc y tế tại nhà",
                R.drawable.help2));

        list.add(new HelpRequest("3", "Hỗ trợ sửa nhà dột",
                "Nhà cấp 4 xuống cấp nghiêm trọng, cần sửa lại mái",
                R.drawable.help3));

        return list;
    }
}
