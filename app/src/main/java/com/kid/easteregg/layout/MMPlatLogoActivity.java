package com.kid.easteregg.layout;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;

import com.kid.easteregg.R;

/**
 * Created by Administrator on 2017/1/15.
 * Android 6.0 - Marshmallow
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MMPlatLogoActivity extends Activity {
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
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onAttachedToWindow() {
        final DisplayMetrics dm = getResources().getDisplayMetrics();
        final float dp = dm.density;
        final int size = (int)
                (Math.min(Math.min(dm.widthPixels, dm.heightPixels), 600*dp) - 100*dp);
        final View im = new View(this);
        im.setTranslationZ(20);
        im.setScaleX(0.5f);
        im.setScaleY(0.5f);
        im.setAlpha(0f);
        im.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                final int pad = (int) (8 * dp);
                outline.setOval(pad, pad, view.getWidth() - pad, view.getHeight() - pad);
            }
        });
        final float hue = (float) Math.random();
        final Paint bgPaint = new Paint();
        bgPaint.setColor(ColorHSBtoColor(hue, 0.4f, 1f));
        final Paint fgPaint = new Paint();
        fgPaint.setColor(ColorHSBtoColor(hue, 0.5f, 1f));
        final Drawable M = getDrawable(R.drawable.platlogo_m);
        final Drawable platlogo = new Drawable() {
            @Override
            public void setAlpha(int alpha) { }
            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) { }
            @Override
            public int getOpacity() {
                return PixelFormat.TRANSLUCENT;
            }
            @Override
            public void draw(Canvas c) {
                final float r = c.getWidth() / 2f;
                c.drawCircle(r, r, r, bgPaint);
                c.drawArc(0, 0, 2 * r, 2 * r, 135, 180, false, fgPaint);
                M.setBounds(0, 0, c.getWidth(), c.getHeight());
                M.draw(c);
            }
        };
        im.setBackground(new RippleDrawable(
                ColorStateList.valueOf(0xFFFFFFFF),
                platlogo,
                null));
        im.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        });
        im.setClickable(true);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTapCount == 0) {
                    showMarshmallow(im);
                }
                im.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (mTapCount < 5) return false;
//                        final ContentResolver cr = getContentResolver();
//                        if (Settings.System.getLong(cr, Settings.System.EGG_MODE, 0)
//                                == 0) {
//                            // For posterity: the moment this user unlocked the easter egg
//                            try {
//                                Settings.System.putLong(cr,
//                                        Settings.System.EGG_MODE,
//                                        System.currentTimeMillis());
//                            } catch (RuntimeException e) {
//                                Log.e("PlatLogoActivity", "Can't write settings", e);
//                            }
//                        }
                        im.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    startActivity(new Intent(MMPlatLogoActivity.this, MLandActivity.class));
                                } catch (ActivityNotFoundException ex) {
                                    Log.e("PlatLogoActivity", "No more eggs.");
                                }
                                finish();
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
                    if (mKeyCount == 0) {
                        showMarshmallow(im);
                    }
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

    /**
     * Convert HSB components to an ARGB color. Alpha set to 0xFF.
     *     hsv[0] is Hue [0 .. 1)
     *     hsv[1] is Saturation [0...1]
     *     hsv[2] is Value [0...1]
     * If hsv values are out of range, they are pinned.
     * @param h Hue component
     * @param s Saturation component
     * @param b Brightness component
     * @return the resulting argb color
     *
     * Color 类的 Pending API council
     */
    private int ColorHSBtoColor(float h, float s, float b) {
//        h = MathUtils.constrain(h, 0.0f, 1.0f);
//        s = MathUtils.constrain(s, 0.0f, 1.0f);
//        b = MathUtils.constrain(b, 0.0f, 1.0f);
        h = (1.0f - 0.0f) * h;
        s = (1.0f - 0.0f) * s;
        b = (1.0f - 0.0f) * b;

        float red = 0.0f;
        float green = 0.0f;
        float blue = 0.0f;

        final float hf = (h - (int) h) * 6.0f;
        final int ihf = (int) hf;
        final float f = hf - ihf;
        final float pv = b * (1.0f - s);
        final float qv = b * (1.0f - s * f);
        final float tv = b * (1.0f - s * (1.0f - f));

        switch (ihf) {
            case 0:         // Red is the dominant color
                red = b;
                green = tv;
                blue = pv;
                break;
            case 1:         // Green is the dominant color
                red = qv;
                green = b;
                blue = pv;
                break;
            case 2:
                red = pv;
                green = b;
                blue = tv;
                break;
            case 3:         // Blue is the dominant color
                red = pv;
                green = qv;
                blue = b;
                break;
            case 4:
                red = tv;
                green = pv;
                blue = b;
                break;
            case 5:         // Red is the dominant color
                red = b;
                green = pv;
                blue = qv;
                break;
        }

        return 0xFF000000 | (((int) (red * 255.0f)) << 16) |
                (((int) (green * 255.0f)) << 8) | ((int) (blue * 255.0f));
    }

    public void showMarshmallow(View im) {
        final Drawable fg = getDrawable(R.drawable.platlogo_mm);
        fg.setBounds(0, 0, im.getWidth(), im.getHeight());
        fg.setAlpha(0);
        im.getOverlay().add(fg);
        final Animator fadeIn = ObjectAnimator.ofInt(fg, "alpha", 255);
        fadeIn.setInterpolator(mInterpolator);
        fadeIn.setDuration(300);
        fadeIn.start();
    }
}
