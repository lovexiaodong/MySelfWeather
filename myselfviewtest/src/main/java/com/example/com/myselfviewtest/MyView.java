package com.example.com.myselfviewtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016/4/20.
 */
public class MyView extends RelativeLayout {
    private String mTitleText;
    private int mTitleColor;
    private float mTitleSize;
    private Bitmap mTitleBackground;
    private String mLeftText;
    private int mLeftColor;
    private Drawable mLeftBackground;
    private String mRightText;
    private int mRightColor;
    private Drawable mRightBackground;

    private Button mRightButton;
    private Button mLeftButton;
    private TextView mTitleView;

    private LayoutParams mLeftParams;
    private LayoutParams mRightParams;
    private LayoutParams mTitleParams;

    private TopBarClinkListener mTopBarClickListener;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        mLeftColor = ta.getColor(R.styleable.TopBar_leftTextColor, 0);
        mLeftBackground = ta.getDrawable(R.styleable.TopBar_leftBackground);
        mLeftText = ta.getString(R.styleable.TopBar_leftText);

        mRightBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);
        mRightColor = ta.getColor(R.styleable.TopBar_rigthTextColor, 0);
        mRightText = ta.getString(R.styleable.TopBar_rigthText);

        mTitleSize = ta.getDimension(R.styleable.TopBar_titleTextSize, 10);
        mTitleColor = ta.getColor(R.styleable.TopBar_titleColor, 0);
        mTitleText = ta.getString(R.styleable.TopBar_titleText);
        ta.recycle();
        mRightButton =  new Button(context);
        mLeftButton = new Button(context);
        mTitleView = new TextView(context);

        mLeftButton.setTextColor(mLeftColor);
        mLeftButton.setBackground(mLeftBackground);
        mLeftButton.setText(mLeftText);

        mRightButton.setBackground(mRightBackground);
        mRightButton.setText(mRightText);
        mRightButton.setTextColor(mRightColor);

        mTitleView.setText(mTitleText);
        mTitleView.setTextColor(mTitleColor);
        mTitleView.setTextSize(mTitleSize);
        mTitleView.setGravity(Gravity.CENTER);

        mLeftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(mLeftButton, mLeftParams);

        mRightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(mRightButton, mRightParams);

        mTitleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTitleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(mTitleView, mTitleParams);

        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopBarClickListener.leftClick();
            }
        });

        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopBarClickListener.rightClick();
            }
        });

    }


    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public interface TopBarClinkListener{
        void leftClick();
        void rightClick();
    }

    public void setmTopBarClickListener(TopBarClinkListener listener)
    {
        this.mTopBarClickListener = listener;
    }

}

