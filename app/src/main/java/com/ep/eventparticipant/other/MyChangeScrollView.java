package com.ep.eventparticipant.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ScrollView;

import static com.ep.eventparticipant.fragment.FragmentHome.fAM_scroll;
import static com.ep.eventparticipant.fragment.FragmentHome.fAM_stable;


@SuppressLint("NewApi")
public class MyChangeScrollView extends ScrollView {
    private View mByWhichView;
    private View mTitleView;
    private boolean shouldSlowlyChange = true;
    private OnScrollListener mListener;

    public MyChangeScrollView(Context context) {
        super(context);
    }

    public MyChangeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyChangeScrollView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setListener(OnScrollListener listener) {
        this.mListener = listener;
    }

    public void setShouldSlowlyChange(boolean slowlyChange) {
        this.shouldSlowlyChange = slowlyChange;
    }


    public void setupTitleView(View view) {
        this.mTitleView = view;
    }


    public void setupByWhichView(View view) {
        mByWhichView = view;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        if (scrollY >= dip2px(getContext(), 180f )) {
            fAM_stable.setVisibility(View.VISIBLE);
            fAM_scroll.setVisibility(View.GONE);
            fAM_scroll.collapse();
        } else {
            fAM_stable.setVisibility(View.GONE);
            fAM_scroll.setVisibility(View.VISIBLE);
            fAM_stable.collapse();
        }

     if (scrollY >= 450) {
            mTitleView.setBackgroundColor(Color.WHITE);

        } else if (scrollY >= 0) {
            if (!shouldSlowlyChange) {//设置是否变色
                mTitleView.setBackgroundColor(Color.TRANSPARENT);
            } else {
                float persent = scrollY
                        * 1f
                        / ((float) 450.0);
                int alpha = (int) (255 * persent);
                int color = Color.argb(alpha, 255, 255, 255);
                Log.i("xiaoqiang", "scrollY==" + scrollY + ".." + 450 + "alpha=" + alpha);
                mTitleView.setBackgroundColor(color);
            }
        }

        if (mListener != null) {
            mListener.onScroll(null, scrollX, scrollY, scrollY);
        }
    }

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
}