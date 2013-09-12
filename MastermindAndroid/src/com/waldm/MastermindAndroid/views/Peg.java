package com.waldm.MastermindAndroid.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.waldm.MastermindAndroid.Colour;

public class Peg extends ImageView {
    private Colour colour;

    public interface PegClickListener {
        void onPegClick(Peg peg);
    }

    public Peg(Context context, Colour colour, final PegClickListener listener) {
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
                listener.onPegClick((Peg) view);
            }
        });
    }

    public Colour getColour() {
        return colour;
    }
    public void setColour(Colour colour) {
        this.colour = colour;
        setImageResource(colour.drawableResource);
    }
}
