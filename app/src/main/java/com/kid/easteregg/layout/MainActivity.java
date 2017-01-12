package com.kid.easteregg.layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kid.easteregg.R;
import com.kid.easteregg.adapter.MainRecycleAdapter;
import com.kid.easteregg.model.AndroidVersion;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements MainRecycleAdapter.OnRecyclerViewItemClickListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

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
        Toast.makeText(MainActivity.this, data.getVersionName(), Toast.LENGTH_SHORT).show();
    }

    private void initLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_versionList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    private ArrayList<AndroidVersion> getVersionsData() {
        ArrayList<AndroidVersion> list = new ArrayList<AndroidVersion>();
        AndroidVersion GB = new AndroidVersion("GB", "Android 2.3 - Gingerbread", R.mipmap.ic_launcher);
        AndroidVersion HC = new AndroidVersion("HC", "Android 3.0/3.1/3.2 - Honeycomb", R.mipmap.ic_launcher);
        AndroidVersion ICS = new AndroidVersion("ICS", "Android 4.0 - Ice Cream Sandwich", R.mipmap.ic_launcher);
        AndroidVersion JB = new AndroidVersion("JB", "Android 4.1/4.2/4.3 - Jelly Bean", R.mipmap.ic_launcher);
        AndroidVersion KK = new AndroidVersion("KK", "Android 4.4 - KitKat", R.mipmap.ic_launcher);
        AndroidVersion LL = new AndroidVersion("LL", "Android 5.0/5.1 - Lollipop", R.mipmap.ic_launcher);
        AndroidVersion MM = new AndroidVersion("MM", "Android 6.0 - Marshmallow", R.mipmap.ic_launcher);
        AndroidVersion NN = new AndroidVersion("NN", "Android 7.0 -Nougat", R.mipmap.ic_launcher);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
