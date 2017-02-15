package com.kid.easteregg.layout;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kid.easteregg.R;
import com.kid.easteregg.adapter.MainRecycleAdapter;
import com.kid.easteregg.model.AndroidVersion;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements MainRecycleAdapter.OnRecyclerViewItemClickListener {

    private TextView infoAndroid;
    private TextView infoPhone;
    private TextView infoCPU;

    private boolean NOFUN = false;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    long[] mHits = new long[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();

        MainRecycleAdapter mAdapter = new MainRecycleAdapter(getVersionsData());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, AndroidVersion data) {
        //有不会修复的问题：快速点3次，最后一次点击的项就是打开项
        System.arraycopy(mHits, 1, mHits, 0, mHits.length-1);
        mHits[mHits.length-1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis()-500)) {
            Intent intent = null;
            switch (data.getVersionName()) {
                case "GB":
                    intent = new Intent(MainActivity.this, GBPlatLogoActivity.class);
                    break;
                case "HC":
                    intent = new Intent(MainActivity.this, HCPlatLogoActivity.class);
                    break;
                case "ICS":
                    intent = new Intent(MainActivity.this, ICSPlatLogoActivity.class);
                    break;
                case "JB":
                    intent = new Intent(MainActivity.this, JBPlatLogoActivity.class);
                    break;
                case "KK":
                    intent = new Intent(MainActivity.this, KKPlatLogoActivity.class);
                    break;
                case "LL":
                    if (Build.VERSION.SDK_INT < 21) {
                        NOFUN = true;
                    } else {
                        intent = new Intent(MainActivity.this, LLPlatLogoActivity.class);
                    }
                    break;
                case "MM":
                    if (Build.VERSION.SDK_INT < 21) {
                        NOFUN = true;
                    } else {
                        intent = new Intent(MainActivity.this, MMPlatLogoActivity.class);
                    }
                    break;
                case "NN":
                    if (Build.VERSION.SDK_INT < 21) {
                        NOFUN = true;
                    } else {
                        intent = new Intent(MainActivity.this, NNPlatLogoActivity.class);
                    }
                    break;
                default:
                    break;
            }
            try {
                if (NOFUN) {
                    Toast.makeText(MainActivity.this, "Sorry, you can't play this egg.", Toast.LENGTH_SHORT).show();
                    NOFUN = false;
                }
                if (intent != null){
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_versionList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        infoAndroid  = (TextView) findViewById(R.id.info_android);
        infoPhone  = (TextView) findViewById(R.id.info_phone);
        infoCPU = (TextView) findViewById(R.id.info_cpu);
        infoAndroid.setText("Android " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")");
        infoPhone.setText(Build.BRAND + " " + Build.MODEL);
        infoCPU.setText(Build.CPU_ABI);
        infoCPU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "这是CPU架构", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<AndroidVersion> getVersionsData() {
        ArrayList<AndroidVersion> list = new ArrayList<AndroidVersion>();
        AndroidVersion GB = new AndroidVersion("GB", "Android 2.3 - Gingerbread", R.mipmap.ic_android_grey600_48dp);
        AndroidVersion HC = new AndroidVersion("HC", "Android 3.0/3.1/3.2 - Honeycomb", R.mipmap.ic_android_grey600_48dp);
        AndroidVersion ICS = new AndroidVersion("ICS", "Android 4.0 - Ice Cream Sandwich", R.mipmap.ic_android_grey600_48dp);
        AndroidVersion JB = new AndroidVersion("JB", "Android 4.1/4.2/4.3 - Jelly Bean", R.mipmap.ic_android_grey600_48dp);
        AndroidVersion KK = new AndroidVersion("KK", "Android 4.4 - KitKat", R.mipmap.ic_android_grey600_48dp);
        AndroidVersion LL = new AndroidVersion("LL", "Android 5.0/5.1 - Lollipop", R.mipmap.ic_android_grey600_48dp);
        AndroidVersion MM = new AndroidVersion("MM", "Android 6.0 - Marshmallow", R.mipmap.ic_android_grey600_48dp);
        AndroidVersion NN = new AndroidVersion("NN", "Android 7.0 -Nougat", R.mipmap.ic_android_grey600_48dp);
        list.add(GB);
        list.add(HC);
        list.add(ICS);
        list.add(JB);
        list.add(KK);
        list.add(LL);
        list.add(MM);
        list.add(NN);
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_supp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("开放源代码许可");
            builder.setMessage(R.string.supp_text);
            builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
