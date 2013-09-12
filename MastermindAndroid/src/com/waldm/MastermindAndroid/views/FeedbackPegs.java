package com.waldm.MastermindAndroid.views;

import android.content.Context;
import android.widget.LinearLayout;
import com.google.common.collect.Lists;
import com.waldm.MastermindAndroid.Colour;
import com.waldm.mastermind.Result;

import java.util.List;

public class FeedbackPegs extends LinearLayout{
    private final List<Peg> pegs = Lists.newArrayList();

    public FeedbackPegs(Context context, int numberOfPegs) {
        super(context);
        setOrientation(HORIZONTAL);

        for (int i = 0; i < numberOfPegs; i++) {
            Peg peg = new Peg(context, Colour.NONE, null);
            pegs.add(peg);
            addView(peg);
        }
    }

    public void update(Result result) {
        int correctLocation = result.locationCorrect;
        int correctColour = result.numberCorrect;

        for (Peg peg : pegs) {
            if (correctLocation > 0) {
                peg.setColour(Colour.BLACK);
                correctLocation--;
            } else if (correctColour > 0) {
                peg.setColour(Colour.WHITE);
                correctColour--;
            }
        }
    }

    public void setPegsOnClickListener (OnClickListener listener) {
        for (Peg peg : pegs) {
            peg.setOnClickListener(listener);
        }
    }

    public void removePegsOnClickListener() {
        setPegsOnClickListener(null);
    }

    public Result getResult() {
        int locationCorrect = 0;
        int colourCorrect = 0;
        for (Peg peg : pegs) {
            if (peg.getColour() == Colour.BLACK) {
                locationCorrect++;
            } else if (peg.getColour() == Colour.WHITE) {
                colourCorrect++;
            }
        }

        return new Result(locationCorrect, colourCorrect);
    }
}
