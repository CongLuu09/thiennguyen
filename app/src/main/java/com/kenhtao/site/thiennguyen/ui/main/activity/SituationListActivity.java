package com.kenhtao.site.thiennguyen.ui.main.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kenhtao.site.thiennguyen.R;
import com.kenhtao.site.thiennguyen.data.adapter.HelpRequestAdapter;
import com.kenhtao.site.thiennguyen.data.model.HelpRequest;
import com.kenhtao.site.thiennguyen.data.repository.FakeAuthRepository;

import java.util.List;

public class SituationListActivity extends AppCompatActivity {

    private RecyclerView recyclerSituations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation_list);

        setupToolbar();

        recyclerSituations = findViewById(R.id.recyclerSituations);
        recyclerSituations.setLayoutManager(new LinearLayoutManager(this));

        List<HelpRequest> helpRequests = FakeAuthRepository.getHelpRequests();
        recyclerSituations.setAdapter(new HelpRequestAdapter(this, helpRequests, item -> {
            // TODO: mở chi tiết hoàn cảnh

        }));
    }

    private void setupToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbarSituation);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Hoàn cảnh cần giúp đỡ");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
