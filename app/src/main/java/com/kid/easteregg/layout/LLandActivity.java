package com.kid.easteregg.layout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.kid.easteregg.R;

/**
 * Created by Administrator on 2017/1/14.
 */

public class LLandActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lland);
        LLand world = (LLand) findViewById(R.id.world);
        world.setScoreField((TextView) findViewById(R.id.score));
        world.setSplash(findViewById(R.id.welcome));
        Log.v(LLand.TAG, "focus: " + world.requestFocus());
    }
}
