package com.waldm.MastermindAndroid.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RelativeLayout;
import com.waldm.MastermindAndroid.R;

public class Peg extends View {
    public Peg(Context context, Drawable background) {
        super(context);
        setBackgroundDrawable(background);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.peg_width),
                getResources().getDimensionPixelSize(R.dimen.peg_height));
        setLayoutParams(params);
    }
}
