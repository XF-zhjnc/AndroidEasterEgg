package com.kid.easteregg.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.kid.easteregg.R;

/**
 * Created by Administrator on 2017/1/14.
 * Android 3.0/3.1/3.2 - Honeycomb
 */

public class HCPlatLogoActivity extends Activity {
    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToast = Toast.makeText(this, "REZZZZZZZZZZ......", Toast.LENGTH_SHORT);

        ImageView content = new ImageView(this);
        content.setImageResource(R.drawable.platlogo_hc);
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
