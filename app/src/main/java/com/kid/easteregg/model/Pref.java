package com.kid.easteregg.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/1/21.
 */

public class Pref {

    private static final String FILE_NAME = "mPrefs";
    private final Context mContext;
    private final SharedPreferences mPrefs;

    private static String KITKAT_STR = "kitkatstr";
    private static String KITKAT_SIZE = "kitkatsize";
    private static String LM_DEFF = "boost_dv";

    public Pref(Context context) {
        mContext = context;
        mPrefs = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public String getKitkatStr() {
        return mPrefs.getString(KITKAT_STR, "K");
    }

    public void setKitkatStr(String s) {
        mPrefs.edit().putString(KITKAT_STR, s).commit();
    }

    public int getKitkatSize() {
        return mPrefs.getInt(KITKAT_SIZE, 300);
    }

    public void setKitkatSize(int size) {
        mPrefs.edit().putInt(KITKAT_SIZE, size).commit();
    }

    public int getLmDeff() {
        return mPrefs.getInt(LM_DEFF, 550);
    }

    public void setLmDeff(int deff) {
        mPrefs.edit().putInt(LM_DEFF, deff).commit();
    }
}
