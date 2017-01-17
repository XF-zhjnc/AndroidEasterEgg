package com.kid.easteregg.layout;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.kid.easteregg.R;
import com.kid.easteregg.neko.NekoActivationActivity;
import com.kid.easteregg.neko.NekoLand;
import com.kid.easteregg.neko.NekoService;
import com.kid.easteregg.neko.PrefState;

/**
 * Created by Administrator on 2017/1/16.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class NNPlatLogoActivity extends Activity implements PrefState.PrefsListener {
    public static final boolean REVEAL_THE_NAME = false;
    FrameLayout mLayout;
    int mTapCount;
    int mKeyCount;
    PathInterpolator mInterpolator = new PathInterpolator(0f, 0f, 0.5f, 1f);
    private PrefState mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout = new FrameLayout(this);
        setContentView(mLayout);

        mPrefs = new PrefState(this);
        mPrefs.setListener(this);
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
                                try {
                                    //startActivity(new Intent(NNPlatLogoActivity.this, NekoActivationActivity.class));
                                    final PackageManager pm = getPackageManager();
                                    //final ComponentName cn = new ComponentName(this, NekoTile.class);
                                    final ComponentName cn = new ComponentName(NNPlatLogoActivity.this, NekoService.class);
                                    if (pm.getComponentEnabledSetting(cn) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                                        if (NekoLand.DEBUG) {
                                            Log.v("Neko", "Disabling tile.");
                                        }
                                        //设为不可用状态
                                        pm.setComponentEnabledSetting(cn, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 0);
                                        Toast.makeText(NNPlatLogoActivity.this, "抓猫猫游戏关闭", Toast.LENGTH_SHORT).show();
                                        mPrefs.setListener(null);
                                        NekoService.cancelJob(NNPlatLogoActivity.this);
                                    } else {
                                        if (NekoLand.DEBUG) {
                                            Log.v("Neko", "Enabling tile.");
                                        }
                                        //设为可用状态
                                        pm.setComponentEnabledSetting(cn, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 0);
                                        Toast.makeText(NNPlatLogoActivity.this, "抓猫猫游戏开启", Toast.LENGTH_SHORT).show();
                                        mPrefs.setFoodState(2);  // 永远有食物
                                        NekoService.registerJob(NNPlatLogoActivity.this, 30);
                                    }

                                } catch (ActivityNotFoundException ex) {
                                    Log.e("PlatLogoActivity", "No more eggs.");
                                }
                                //finish();
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

    @Override
    public void onPrefsChanged() {
        //do nothing
    }
}
