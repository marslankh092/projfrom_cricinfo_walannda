package com.virmana.status_app_all.ui.Activities;



import com.virmana.status_app_all.Provider.ThemeHelper;
import com.virmana.status_app_all.R;
import com.virmana.status_app_all.Provider.PrefManager;

import android.content.DialogInterface;
import android.content.pm.Signature;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.preference.ListPreference;
import android.preference.Preference;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

import es.dmoral.toasty.Toasty;
import android.content.ClipData;
import android.content.ClipboardManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
public class SettingsActivity extends AppCompatActivity {

    private LinearLayout linear_layout_clea_cache;
    private TextView text_view_cache_value;
    private PrefManager prf;
    private Switch switch_button_notification;
    private TextView text_view_version;
    private LinearLayout linearLayout_policy_privacy;
    private LinearLayout linearLayout_contact_us;
    private LinearLayout linear_layout_hash;
    private LinearLayout linearLayout_theme;
    private ImageView image_view_theme_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prf= new PrefManager(getApplicationContext());

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.action_settings));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        setValues();
        initAction();
        initializeCache();


    }

    private void initAction() {
        this.linearLayout_theme.setOnClickListener(v -> {
            String[] colors = {"Light", "Dark", "System default"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose theme");
            builder.setItems(colors, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    switch (which){
                        case 0:
                            ThemeHelper.applyTheme("light");
                            prf.setString("theme","light");
                            image_view_theme_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_light));

                            break;
                        case 1:
                            ThemeHelper.applyTheme("dark");
                            prf.setString("theme","dark");
                            image_view_theme_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_dark));

                            break;
                        case 2:
                            ThemeHelper.applyTheme("default");
                            prf.setString("theme","default");
                            image_view_theme_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_settings));

                            break;
                        default:

                    }
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.show();
        });
        this.linear_layout_clea_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCache(getApplicationContext());
                initializeCache();
            }
        });
        this.switch_button_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    prf.setString("notifications","true");
                }else{
                    prf.setString("notifications","false");
                }
            }
        });
        this.linearLayout_policy_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PolicyActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });

        this.linearLayout_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SupportActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });
        this.linear_layout_hash.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                printHashKey(getApplicationContext());
                return false;
            }
        });
    }

    public  void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("HASKEY", "printHashKey() Hash Key: " + hashKey);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("haskey", hashKey);
                clipboard.setPrimaryClip(clip);
                Toasty.info(getApplicationContext(),"haskey has been copied",Toast.LENGTH_SHORT).show();
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("HASKEY", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("HASKEY", "printHashKey()", e);
        }
    }
    private void initView() {
        this.linearLayout_theme= (LinearLayout) findViewById(R.id.linearLayout_theme);
        this.image_view_theme_icon= (ImageView) findViewById(R.id.image_view_theme_icon);
        this.linear_layout_clea_cache= (LinearLayout) findViewById(R.id.linear_layout_clea_cache);
        this.text_view_cache_value=(TextView) findViewById(R.id.text_view_cache_value);
        this.switch_button_notification=(Switch) findViewById(R.id.switch_button_notification);
        this.text_view_version=(TextView) findViewById(R.id.text_view_version);
        this.linearLayout_policy_privacy=(LinearLayout) findViewById(R.id.linearLayout_policy_privacy);
        this.linearLayout_contact_us=(LinearLayout) findViewById(R.id.linearLayout_contact_us);
        this.linear_layout_hash=(LinearLayout) findViewById(R.id.linear_layout_hash);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
        finish();
        return;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
    private void initializeCache() {
        long size = 0;
        try {
            size += getDirSize(this.getCacheDir());
            size += getDirSize(this.getExternalCacheDir());
        }catch (Exception  e){

        }

        this.text_view_cache_value.setText(getResources().getString(R.string.label_cache)  + readableFileSize(size));

    }

    public long getDirSize(File dir){
        long size = 0;

        for (File file : dir.listFiles()) {
            if (file != null && file.isDirectory()) {
                size += getDirSize(file);
            } else if (file != null && file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }

    public static String readableFileSize(long size) {
        if (size <= 0) return "0 Bytes";
        final String[] units = new String[]{"Bytes", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }



    private void setValues() {
        if (prf.getString("notifications").equals("false")){
            this.switch_button_notification.setChecked(false);
        }else{
            this.switch_button_notification.setChecked(true);
        }
        if (prf.getString("theme").equals("dark")) {
            image_view_theme_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_dark));
        } else if (prf.getString("theme").equals("light")){
            image_view_theme_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_light));

        }else{
            image_view_theme_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_settings));
        }
        try {
            PackageInfo pInfo =getPackageManager().getPackageInfo(getPackageName(),0);
            String version = pInfo.versionName;
            text_view_version.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
