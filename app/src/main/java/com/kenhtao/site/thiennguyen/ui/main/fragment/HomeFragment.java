package com.kenhtao.site.thiennguyen.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.kenhtao.site.thiennguyen.R;
import com.kenhtao.site.thiennguyen.data.adapter.BannerAdapter;
import com.kenhtao.site.thiennguyen.data.adapter.HelpRequestAdapter;
import com.kenhtao.site.thiennguyen.data.model.HelpRequest;
import com.kenhtao.site.thiennguyen.data.repository.FakeAuthRepository;
import com.kenhtao.site.thiennguyen.ui.main.activity.SituationListActivity;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerHelpRequests;
    private ViewPager2 viewPagerBanner;
    private GridLayout gridMenu;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 1;

    private String userRole;

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerHelpRequests = view.findViewById(R.id.recyclerHelpRequests);
        viewPagerBanner = view.findViewById(R.id.viewPagerBanner);
        gridMenu = view.findViewById(R.id.gridMenu);
        handler = new Handler();


        SharedPreferences prefs = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        userRole = prefs.getString("user_role", "donor");

        setupBanner();
        setupGridMenu(userRole);
        setupHelpRequests();

        return view;
    }

    private void setupBanner() {
        BannerAdapter adapter = new BannerAdapter(requireContext());
        viewPagerBanner.setAdapter(adapter);


        viewPagerBanner.setCurrentItem(1, false);

        viewPagerBanner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    int itemCount = adapter.getItemCount();
                    if (currentPage == 0) {
                        viewPagerBanner.setCurrentItem(itemCount - 2, false);
                    } else if (currentPage == itemCount - 1) {
                        viewPagerBanner.setCurrentItem(1, false);
                    }
                }
            }
        });

        runnable = () -> {
            currentPage++;
            viewPagerBanner.setCurrentItem(currentPage, true);
            handler.postDelayed(runnable, 3000);
        };

        handler.postDelayed(runnable, 3000);
    }

    private void setupGridMenu(String role) {
        String[] labels;
        int[] icons;

        switch (role) {
            case "volunteer":
                labels = new String[]{"Lịch trực", "Hỗ trợ", "Bản đồ", "Tin tức"};
                icons = new int[]{R.drawable.schedule, R.drawable.support, R.drawable.map, R.drawable.news};
                break;

            case "recipient":
                labels = new String[]{"Đăng ký hỗ trợ", "Hướng dẫn", "Bản đồ", "Liên hệ"};
                icons = new int[]{R.drawable.request, R.drawable.guide, R.drawable.map, R.drawable.contact};
                break;

            case "donor":
            default:
                labels = new String[]{"Hoàn cảnh", "Đồng hành", "Sự kiện", "Bản đồ"};
                icons = new int[]{R.drawable.situation, R.drawable.companion, R.drawable.event, R.drawable.map};
                break;
        }

        gridMenu.removeAllViews();
        Log.d("HomeFragment", "userRole = " + role);

        for (int i = 0; i < labels.length; i++) {
            View item = LayoutInflater.from(getContext()).inflate(R.layout.item_grid_menu, gridMenu, false);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // chia đều 1 phần

            item.setLayoutParams(params);


            ImageView img = item.findViewById(R.id.imgIcon);
            TextView txt = item.findViewById(R.id.txtLabel);
            img.setImageResource(icons[i]);
            txt.setText(labels[i]);

            int index = i;
            item.setOnClickListener(v -> {
                Log.d("GridMenu", "Click: role = " + role + ", index = " + index);

                switch (role) {
                    case "volunteer":
                        switch (index) {
                            case 0:
//                                startActivity(new Intent(getContext(), ScheduleActivity.class));
                                break;
                            case 1:
//                                startActivity(new Intent(getContext(), SupportActivity.class));
                                break;
                            case 2:
//                                startActivity(new Intent(getContext(), MapActivity.class));
                                break;
                            case 3:
//                                startActivity(new Intent(getContext(), NewsActivity.class));
                                break;
                        }
                        break;

                    case "recipient":
                        switch (index) {
                            case 0:
//                                startActivity(new Intent(getContext(), RequestSupportActivity.class));
                                break;
                            case 1:
//                                startActivity(new Intent(getContext(), GuideActivity.class));
                                break;
                            case 2:
//                                startActivity(new Intent(getContext(), MapActivity.class));
                                break;
                            case 3:
//                                startActivity(new Intent(getContext(), ContactActivity.class));
                                break;
                        }
                        break;

                    case "donor":
                    default:
                        switch (index) {
                            case 0:
                                startActivity(new Intent(getContext(), SituationListActivity.class));
                                break;
                            case 1:
//                                startActivity(new Intent(getContext(), CompanionActivity.class));
                                break;
                            case 2:
//                                startActivity(new Intent(getContext(), EventActivity.class));
                                break;
                            case 3:
//                                startActivity(new Intent(getContext(), MapActivity.class));
                                break;
                        }
                        break;
                }
            });


            gridMenu.addView(item);
        }
    }

    private void setupHelpRequests() {
        List<HelpRequest> requests = FakeAuthRepository.getHelpRequests();
        recyclerHelpRequests.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerHelpRequests.setAdapter(new HelpRequestAdapter(getContext(), requests, item -> {
            // TODO: Xử lý click vào từng request
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
