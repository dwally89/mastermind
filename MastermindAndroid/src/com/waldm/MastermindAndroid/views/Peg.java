package com.waldm.MastermindAndroid.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Peg extends View {
    public interface PegClickListener {
        void displayColourPicker(View peg);
    }

    public Peg(Context context, Drawable background) {
        super(context);
        setBackgroundDrawable(background);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        setLayoutParams(params);
    }

    public Peg(Context context, Drawable background, final PegClickListener listener) {
        this(context, background);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.displayColourPicker(view);
            }
        });
    }
}
