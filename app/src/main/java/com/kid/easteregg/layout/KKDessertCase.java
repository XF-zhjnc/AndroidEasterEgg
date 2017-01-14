package com.kid.easteregg.layout;

import android.app.Activity;

/**
 * Created by Administrator on 2017/1/14.
 */

public class KKDessertCase extends Activity {
    KKDessertCaseView mView;
    @Override
    public void onStart() {
        super.onStart();
        mView = new KKDessertCaseView(this);
        KKDessertCaseView.RescalingContainer container = new KKDessertCaseView.RescalingContainer(this);
        container.setView(mView);
        setContentView(container);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.start();
            }
        }, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mView.stop();
    }
}
