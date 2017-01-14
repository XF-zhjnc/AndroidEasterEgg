package com.kid.easteregg.layout;

import android.app.Activity;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.Toast;

import com.kid.easteregg.R;

/**
 * Created by Administrator on 2017/1/12.
 * Android 4.0 - Ice Cream Sandwich
 */

public class ICSPlatLogoActivity extends Activity {
    Toast mToast;
    ImageView mContent;
    Vibrator mZzz;
    int mCount;
    final Handler mHandler = new Handler();

    Runnable mSuperLongPress = new Runnable() {
        @Override
        public void run() {
            mCount++;
            mZzz.vibrate(50 * mCount);
            final float scale = 1f + 0.25f * mCount * mCount;
            mContent.setScaleX(scale);
            mContent.setScaleY(scale);

            if (mCount <= 3) {
                    mHandler.postDelayed(mSuperLongPress, ViewConfiguration.getLongPressTimeout());
            } else {
                try {
                startActivity(new Intent(ICSPlatLogoActivity.this, ICSNyandroid.class));
                } catch (ActivityNotFoundException ex) {
                    android.util.Log.e("PlatLogoActivity", "Couldn't find platlogo screensaver.");
                }
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mZzz = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        mToast = Toast.makeText(this, "Android 4.0: Ice Cream Sandwich", Toast.LENGTH_SHORT);
        mContent = new ImageView(this);
        mContent.setImageResource(R.drawable.platlogo_ics);
        mContent.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    mContent.setPressed(true);
                    mHandler.removeCallbacks(mSuperLongPress);
                    mCount = 0;
                    mHandler.postDelayed(mSuperLongPress, 2*ViewConfiguration.getLongPressTimeout());
                } else if (action == MotionEvent.ACTION_UP) {
                    if (mContent.isPressed()) {
                        mContent.setPressed(false);
                        mHandler.removeCallbacks(mSuperLongPress);
                        mToast.show();
                    }
                }
                return true;
            }
        });
        setContentView(mContent);
    }
}
