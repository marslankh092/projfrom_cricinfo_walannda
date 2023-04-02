package com.virmana.status_app_all.ui.Activities;

import android.os.Environment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;

import com.virmana.status_app_all.R;
import com.virmana.status_app_all.Adapters.RecyclerViewMediaAdapter;
import com.virmana.status_app_all.model.StatusWhatsapp;

import java.io.File;
import java.util.ArrayList;

public class WhatsAppActivity extends AppCompatActivity {
    private   String WHATSAPP_STATUSES_LOCATION ;
    private RecyclerView mRecyclerViewMediaList;
    private LinearLayoutManager mLinearLayoutManager;
    public static final String TAG = "Home";
    private SwipeRefreshLayout swipe_refreshl_whatsapp_saver;
    private ArrayList<StatusWhatsapp> inFiles;
    private RecyclerViewMediaAdapter recyclerViewMediaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app);

        if (android.os.Build.VERSION.SDK_INT < 30) {
            // Less then 11
            WHATSAPP_STATUSES_LOCATION = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses";

        } else {
            // Android 11
            WHATSAPP_STATUSES_LOCATION = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses";


        }

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.whatsapp_saver));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        inFiles = new ArrayList<StatusWhatsapp>();
        getListFiles(new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_STATUSES_LOCATION));

        swipe_refreshl_whatsapp_saver = (SwipeRefreshLayout) findViewById(R.id.swipe_refreshl_whatsapp_saver);
        mRecyclerViewMediaList = (RecyclerView) findViewById(R.id.recyclerViewMedia);
        mLinearLayoutManager = new LinearLayoutManager(this);


        recyclerViewMediaAdapter =new RecyclerViewMediaAdapter(inFiles, WhatsAppActivity.this);
        mRecyclerViewMediaList.setHasFixedSize(true);
        mRecyclerViewMediaList.setAdapter(recyclerViewMediaAdapter);
        mRecyclerViewMediaList.setLayoutManager(mLinearLayoutManager);
        swipe_refreshl_whatsapp_saver.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refreshl_whatsapp_saver.setRefreshing(false);
                getListFiles(new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_STATUSES_LOCATION));
                recyclerViewMediaAdapter.notifyDataSetChanged();
            }
        });
    }
    private ArrayList<StatusWhatsapp> getListFiles(File parentDir) {

        inFiles.clear();
        File[] files;
        files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                Log.v("HASSAN","OK");
                if (file.getName().endsWith(".jpg") || file.getName().endsWith(".gif") ||  file.getName().endsWith(".mp4")) {
                    if (!inFiles.contains(file))
                        if (file.getName().endsWith(".mp4")){
                            inFiles.add(new StatusWhatsapp().setFile(file).setViewType(0));
                        }else{
                            inFiles.add(new StatusWhatsapp().setFile(file).setViewType(1));
                        }
                }
            }
        }
        return inFiles;
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
        return;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
