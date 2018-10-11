package com.i2e1.linq.Ui.CustomAppTextViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CustomAppTextViewRegular extends android.support.v7.widget.AppCompatTextView {


    public CustomAppTextViewRegular(Context context) {
        super(context);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GT-Walsheim-Regular.ttf"));
    }

    public CustomAppTextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/GT-Walsheim-Regular.ttf"));
    }
}
