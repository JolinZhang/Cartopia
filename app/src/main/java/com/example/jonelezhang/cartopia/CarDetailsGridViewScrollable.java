package com.example.jonelezhang.cartopia;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by Jonelezhang on 6/27/16.
 */
public class CarDetailsGridViewScrollable extends GridView {

    public CarDetailsGridViewScrollable(Context context) {
        super(context);
    }
    public CarDetailsGridViewScrollable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CarDetailsGridViewScrollable(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST));
        getLayoutParams().height = getMeasuredHeight();
    }
}
