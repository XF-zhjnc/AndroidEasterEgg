package com.kid.easteregg.layout;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.kid.easteregg.R;

/**
 * Created by Administrator on 2017/1/16.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class NNPlatLogoActivity extends Activity {
    public static final boolean REVEAL_THE_NAME = false;
    FrameLayout mLayout;
    int mTapCount;
    int mKeyCount;
    PathInterpolator mInterpolator = new PathInterpolator(0f, 0f, 0.5f, 1f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout = new FrameLayout(this);
        setContentView(mLayout);
    }
    @Override
    public void onAttachedToWindow() {
        final DisplayMetrics dm = getResources().getDisplayMetrics();
        final float dp = dm.density;
        final int size = (int)
                (Math.min(Math.min(dm.widthPixels, dm.heightPixels), 600*dp) - 100*dp);
        final ImageView im = new ImageView(this);
        final int pad = (int)(40*dp);
        im.setPadding(pad, pad, pad, pad);
        im.setTranslationZ(20);
        im.setScaleX(0.5f);
        im.setScaleY(0.5f);
        im.setAlpha(0f);
        im.setBackground(new RippleDrawable(
                ColorStateList.valueOf(0xFFFFFFFF),
                getDrawable(R.drawable.platlogo_nn),
                null));
        im.setClickable(true);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                im.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (mTapCount < 5) return false;
                        if (REVEAL_THE_NAME) {
                            final Drawable overlay = getDrawable(
                                    R.drawable.platlogo_nn);
                            overlay.setBounds(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
                            im.getOverlay().clear();
                            im.getOverlay().add(overlay);
                            overlay.setAlpha(0);
                            ObjectAnimator.ofInt(overlay, "alpha", 0, 255)
                                    .setDuration(500)
                                    .start();
                        }
                        im.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NNPlatLogoActivity.this, "这里并没有抓猫猫游戏...", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return true;
                    }
                });
                mTapCount++;
            }
        });
        // Enable hardware keyboard input for TV compatibility.
        im.setFocusable(true);
        im.requestFocus();
        im.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    ++mKeyCount;
                    if (mKeyCount > 2) {
                        if (mTapCount > 5) {
                            im.performLongClick();
                        } else {
                            im.performClick();
                        }
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
        mLayout.addView(im, new FrameLayout.LayoutParams(size, size, Gravity.CENTER));
        im.animate().scaleX(1f).scaleY(1f).alpha(1f)
                .setInterpolator(mInterpolator)
                .setDuration(500)
                .setStartDelay(800)
                .start();
    }

}
