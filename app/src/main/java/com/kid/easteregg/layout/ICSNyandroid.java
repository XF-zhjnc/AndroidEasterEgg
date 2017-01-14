package com.kid.easteregg.layout;

import android.animation.TimeAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kid.easteregg.R;

import java.util.Random;

/**
 * Created by Administrator on 2017/1/12.
 */

public class ICSNyandroid extends Activity {
    final static boolean DEBUG = false;

    public static class Board extends FrameLayout {
        public static final boolean FIXED_STARS = true;
        public static final int NUM_CATS = 20;

        static Random sRNG = new Random();

        static float lerp(float a, float b, float f) {
            return (b-a)*f + a;
        }

        static float randfrange(float a, float b) {
            return lerp(a, b, sRNG.nextFloat());
        }

        static int randsign() {
            return sRNG.nextBoolean() ? 1 : -1;
        }

        static <E> E pick(E[] array) {
            if (array.length == 0) return null;
            return array[sRNG.nextInt(array.length)];
        }

        public class FlyingCat extends ImageView {
            public static final float VMAX = 1000.0f;
            public static final float VMIN = 100.0f;
            public float v, vr;
            public float dist;
            public float z;
            public ComponentName component;

            public FlyingCat(Context context, AttributeSet as) {
                    super(context, as);
                    setImageResource(R.drawable.nyandroid_anim);

                    if (DEBUG) setBackgroundColor(0x80FF0000);
            }

            public String toString() {
                return String.format("<cat (%.1f, %.1f) (%d x %d)>",
                                getX(), getY(), getWidth(), getHeight());
            }

            public void reset() {
                final float scale = lerp(0.1f,2f,z);
                setScaleX(scale); setScaleY(scale);
                setX(-scale*getWidth()+1);
                setY(randfrange(0, Board.this.getHeight()-scale*getHeight()));
                v = lerp(VMIN, VMAX, z);
                dist = 0;
                android.util.Log.d("Nyandroid", "reset cat: " + this);
            }

            public void update(float dt) {
                dist += v * dt;
                setX(getX() + v * dt);
            }
        }

        TimeAnimator mAnim;

        public Board(Context context, AttributeSet attrs) {
            super(context, attrs);
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
            setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            setBackgroundColor(0xFF003366);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        private void reset() {
            removeAllViews();
            final ViewGroup.LayoutParams wrap = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if (FIXED_STARS) {
                for (int i = 0; i < 20; i++) {
                    ImageView fixedStar = new ImageView(getContext(), null);
                    if (DEBUG) fixedStar.setBackgroundColor(0x8000FF80);
                    fixedStar.setImageResource(R.drawable.ics_star_anim);
                    addView(fixedStar, wrap);
                    final float scale = randfrange(0.1f, 1f);
                    fixedStar.setScaleX(scale); fixedStar.setScaleY(scale);
                    fixedStar.setX(randfrange(0, getWidth()));
                    fixedStar.setY(randfrange(0, getHeight()));
                    final AnimationDrawable anim = (AnimationDrawable) fixedStar.getDrawable();
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            anim.start();
                        }
                    }, (int) randfrange(0, 1000));
                }
            }
            for (int i = 0; i < NUM_CATS; i++) {
                FlyingCat nv = new FlyingCat(getContext(), null);
                addView(nv, wrap);
                nv.z = ((float)i/NUM_CATS);
                nv.z *= nv.z;
                nv.reset();
                nv.setX(randfrange(0,Board.this.getWidth()));
                final AnimationDrawable anim = (AnimationDrawable) nv.getDrawable();
                postDelayed(new Runnable() {
                    public void run() {
                            anim.start();
                    }
                }, (int) randfrange(0, 1000));
            }
            if (mAnim != null) {
                mAnim.cancel();
            }
            mAnim = new TimeAnimator();
            mAnim.setTimeListener(new TimeAnimator.TimeListener() {
                @Override
                public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
                    for (int i=0; i<getChildCount(); i++) {
                        View v = getChildAt(i);
                        if (!(v instanceof FlyingCat)) continue;
                        FlyingCat nv = (FlyingCat) v;
                        nv.update(deltaTime / 1000f);
                        final float catWidth = nv.getWidth() * nv.getScaleX();
                        final float catHeight = nv.getHeight() * nv.getScaleY();
                        if (nv.getX() + catWidth < -2 || nv.getX() > getWidth() + 2 || nv.getY() + catHeight < -2 || nv.getY() > getHeight() + 2) {
                            nv.reset();
                        }
                    }
                }
            });
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            post(new Runnable() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void run() {
                    reset();
                    mAnim.start();
                }
            });
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            mAnim.cancel();
        }

        @Override
        public boolean isOpaque() {
            return true;
        }
    }

    private Board mBoard;

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON |
                                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBoard = new Board(this, null);
        setContentView(mBoard);

        mBoard.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (0 == (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)) {
                    ICSNyandroid.this.finish();
                }
            }
        });
    }

    @Override
    public void onUserInteraction() {
        finish();
    }
}
