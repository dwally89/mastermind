package com.waldm.MastermindAndroid.views;

import android.content.Context;
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

    public Peg(Context context, MainActivity.Colour colour, final PegClickListener listener) {
        super(context);

        setImageResource(colour.drawableResource);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        setLayoutParams(params);
        this.colour = colour;

        if (listener == null) {
            return;
        }

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
    public void setColour(MainActivity.Colour colour) {
        this.colour = colour;
        setImageResource(colour.drawableResource);
    }
}
