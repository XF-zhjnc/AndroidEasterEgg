package com.kid.easteregg.layout;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.kid.easteregg.R;
import com.kid.easteregg.model.Pref;

/**
 * Created by Administrator on 2017/1/21.
 */

public class SettingActivity extends Activity {

    private Pref mPrefs;

    private EditText et_kkStr;
    private EditText et_kkSize;
    private EditText et_lmDeff;
    private TextInputLayout kkstrWrapper;
    private TextInputLayout kksizeWrapper;
    private TextInputLayout lmdeffWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mPrefs = new Pref(this);

        et_kkStr = (EditText) findViewById(R.id.et_kkstr);
        et_kkSize = (EditText) findViewById(R.id.et_kksize);
        et_lmDeff = (EditText) findViewById(R.id.et_lmdeff);
        kkstrWrapper = (TextInputLayout) findViewById(R.id.til_kkstrWrapper);
        kksizeWrapper = (TextInputLayout) findViewById(R.id.til_kksizeWrapper);
        lmdeffWrapper = (TextInputLayout) findViewById(R.id.til_lmdeffWrapper);
    }

    @Override
    protected void onStart() {
        super.onStart();
        et_kkStr.setText(mPrefs.getKitkatStr());
        et_kkSize.setText(mPrefs.getKitkatSize()+"");
        et_lmDeff.setText(mPrefs.getLmDeff()+"");
        kkstrWrapper.setHint("请输入KitKat文本,默认( K )");
        kksizeWrapper.setHint("请输入KitKat文本大小,默认( 300 )");
        lmdeffWrapper.setHint("请输入L/M游戏难度,默认( 550 )");
    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(et_kkStr.getText())){
            mPrefs.setKitkatStr(et_kkStr.getText().toString());
        } else {
            mPrefs.setKitkatStr("K");
        }
        if (!TextUtils.isEmpty(et_kkSize.getText())){
            String str1 = et_kkSize.getText().toString();
            if (str1 != null && str1.length() > 0) {
                mPrefs.setKitkatSize(Integer.parseInt(str1));
            } else {
                mPrefs.setKitkatSize(300);
            }
        } else {
            mPrefs.setKitkatSize(300);
        }
        if (!TextUtils.isEmpty(et_lmDeff.getText())){
            String str2 = et_lmDeff.getText().toString();
            if (str2 != null && str2.length() > 0) {
                mPrefs.setLmDeff(Integer.parseInt(str2));
            } else {
                mPrefs.setLmDeff(550);
            }
        } else {
            mPrefs.setLmDeff(550);
        }
        Toast.makeText(this, "设置已保存", Toast.LENGTH_SHORT).show();
        finish();
    }
}
