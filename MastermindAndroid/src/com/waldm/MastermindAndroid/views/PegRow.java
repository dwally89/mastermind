package com.waldm.MastermindAndroid.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

import java.util.List;

public class PegRow extends LinearLayout{
    public PegRow(Context context, List<Drawable> backgrounds) {
        super(context);
        setOrientation(HORIZONTAL);

        for (Drawable background : backgrounds) {
            addView(new Peg(context, background));
        }
    }

    public PegRow(Context context, List<Drawable> backgrounds, Peg.PegClickListener listener) {
        super(context);
        setOrientation(HORIZONTAL);

        for (Drawable background : backgrounds) {
            addView(new Peg(context, background, listener));
        }
    }
}
