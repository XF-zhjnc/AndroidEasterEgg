package com.kid.easteregg.model;

/**
 * Created by Administrator on 2017/1/10.
 */

public class AndroidVersion {

    private String versionName;
    private String versionText;
    private int versionImage;

    public AndroidVersion(String name, String text, int res) {
        this.versionName = name;
        this.versionText = text;
        this.versionImage = res;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getVersionText() {
        return versionText;
    }

    public int getVersionImage() {
        return versionImage;
    }

}
