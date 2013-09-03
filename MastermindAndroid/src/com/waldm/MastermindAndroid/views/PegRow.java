package com.waldm.MastermindAndroid.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.common.collect.Lists;
import com.waldm.MastermindAndroid.MainActivity;
import com.waldm.mastermind.Result;

import java.util.List;

public class PegRow extends LinearLayout{
    private final TextView numberInWrongPlace;
    private final TextView numberCorrect;
    private List<Peg> pegs = Lists.newArrayList();

    public PegRow(Context context, List<Drawable> backgrounds) {
        super(context);
        setOrientation(HORIZONTAL);

        for (Drawable background : backgrounds) {
            Peg peg = new Peg(context, background);
            pegs.add(peg);
            addView(peg);
        }

        numberCorrect = new TextView(context);
        numberInWrongPlace = new TextView(context);
        addView(numberCorrect);
        addView(numberInWrongPlace);
    }

    public PegRow(Context context, List<Drawable> backgrounds, Peg.PegClickListener listener) {
        super(context);
        setOrientation(HORIZONTAL);

        for (Drawable background : backgrounds) {
            Peg peg = new Peg(context, background, listener);
            pegs.add(peg);
            addView(peg);
        }

        numberCorrect = null;
        numberInWrongPlace = null;
    }

    public List<Peg> getPegs() {
        return pegs;
    }

    public void setColours(List<Peg> colours) {
        for (int i = 0; i < pegs.size(); i++) {
            MainActivity.Colour colour = colours.get(i).getColour();
            pegs.get(i).setColour(colour, MainActivity.colourDrawableIds.get(colour));
        }
    }

    public void setResult(Result result) {
        numberCorrect.setText("(" + result.locationCorrect + ",");
        numberInWrongPlace.setText(result.numberCorrect + ")");
    }
}
