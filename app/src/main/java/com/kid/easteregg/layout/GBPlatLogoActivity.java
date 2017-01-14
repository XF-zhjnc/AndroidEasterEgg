package com.kid.easteregg.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.kid.easteregg.R;

/**
 * Created by Administrator on 2017/1/12.
 * Android 2.3 - Gingerbread
 */

public class GBPlatLogoActivity extends Activity {
    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToast = Toast.makeText(this, "Zombie art by Jack Larson", Toast.LENGTH_SHORT);

        ImageView content = new ImageView(this);
        content.setImageResource(R.drawable.platlogo_gb);
        content.setScaleType(ImageView.ScaleType.FIT_CENTER);

        setContentView(content);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            mToast.show();
        }
        return super.dispatchTouchEvent(ev);
    }
}
