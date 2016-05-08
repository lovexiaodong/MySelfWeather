package com.example.com.myselfviewtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/4/26.
 */
public class MyScrollView extends ViewGroup{

    private int mScreenHeight;
    private Scroller mScroller;
    private int mLastY;
    private int mStart;
    private int mEnd;

    public MyScrollView(Context context) {
        super(context);
        initView(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyScrollView(Context context, AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        /*
        在真机下调试才发现有问题，Android有个类DisplayMetrics可以得到分辨率等信息，方法如下：

        DisplayMetrics metrics;getWindowManager().getDefaultDisplay().getMetrics(metrics);

        metrics.widthPixels           屏幕宽

        metrics.heightPixels          屏幕高
        +
        metrics.density                  屏幕密度
        * */
        WindowManager wm = (WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed,
                            int l, int t, int r, int b) {
        int childCount = getChildCount();
        // 设置ViewGroup的高度
        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        mlp.height = mScreenHeight * childCount;
        setLayoutParams(mlp);
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                child.layout(l, i * mScreenHeight,
                        r, (i + 1) * mScreenHeight);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,
                             int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            View childView = getChildAt(i);
            measureChild(childView,
                    widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mStart = getScrollY();
                Log.i("MyScrollView", "onTouchEvent: mStart=" + mStart);
                Log.i("MyScrollView", "onTouchEvent: y =" + y);
                Log.i("MyScrollView", "onTouchEvent: getHight=" + getHeight());
                Log.i("MyScrollView", "onTouchEvent: mScreenHeight=" + mScreenHeight);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("MyScrollView", "onTouchEvent:  mLastY=" + getScrollY());
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;
                if (getScrollY() < 0) {
                    dy = 0;
                }
                if (getScrollY() > getHeight() - mScreenHeight) {
                    dy = 0;
                }
                scrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mEnd = getScrollY();
                int dScrollY = mEnd - mStart;
                if (dScrollY > 0) {
                    if (dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -dScrollY);
                    } else {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, mScreenHeight - dScrollY);
                    }
                } else {
                    if (-dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -dScrollY);
                    } else {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -mScreenHeight - dScrollY);
                    }
                }
                break;
        }
        postInvalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }
}
