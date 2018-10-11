package com.i2e1.linq.Ui.CustomAppTextViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class CustomAppTextViewMedium extends android.support.v7.widget.AppCompatTextView {


    public CustomAppTextViewMedium(Context context) {
        super(context);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GT-Walsheim-Medium.ttf"));
    }

    public CustomAppTextViewMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GT-Walsheim-Medium.ttf"));
    }
}
