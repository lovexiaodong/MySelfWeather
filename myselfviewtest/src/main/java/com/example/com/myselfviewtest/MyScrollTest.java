package com.example.com.myselfviewtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/4/27.
 */
public class MyScrollTest extends ViewGroup {

    private int mSreenHight;
    private Scroller mScroller;
    private int mLastY;
    private int mStart;
    private int mEnd;

    public MyScrollTest(Context context) {
        super(context);
    }

    public MyScrollTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initView(Context context)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        mSreenHight = metrics.heightPixels;
        mScroller = new Scroller(context);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for(int i = 0; i < count; i++)
        {
            View view = getChildAt(i);
            measureChild(view, heightMeasureSpec, widthMeasureSpec);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mStart = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(!mScroller.isFinished())
                {
                    mScroller.abortAnimation();
                }
                int dy = mLastY -y;
                if(getScaleY() < 0)
                {
                    dy = 0;
                }

                scrollBy(0,dy);
                mLastY = y;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset())
        {
            scrollTo(0, mScroller.getCurrY());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        params.height = mSreenHight * childCount;
        setLayoutParams(params);

        for(int i = 0; i < childCount; i++)
        {
            View view = getChildAt(i);
            if(view.getVisibility() != View.GONE)
            {
                view.layout(1,i * mSreenHight, r, (i +1) * mSreenHight);
            }
        }
    }
}
