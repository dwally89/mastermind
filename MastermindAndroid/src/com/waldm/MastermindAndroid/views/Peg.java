package com.waldm.MastermindAndroid.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.waldm.MastermindAndroid.MainActivity;

public class Peg extends ImageView {
    private MainActivity.Colour colour;

    public interface PegClickListener {
        void displayColourPicker(Peg peg);
    }

    public Peg(Context context, Drawable background) {
        super(context);
        setImageDrawable(background);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        setLayoutParams(params);
    }

    public Peg(Context context, Drawable background, final PegClickListener listener) {
        this(context, background);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.displayColourPicker((Peg)view);
            }
        });
    }

    public MainActivity.Colour getColour() {
        return colour;
    }
    public void setColour(MainActivity.Colour colour, int drawable) {
        this.colour = colour;
        setImageResource(drawable);
    }
}
