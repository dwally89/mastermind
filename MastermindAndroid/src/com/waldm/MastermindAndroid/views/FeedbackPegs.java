package com.waldm.MastermindAndroid.views;

import android.content.Context;
import android.widget.LinearLayout;
import com.google.common.collect.Lists;
import com.waldm.MastermindAndroid.MainActivity;
import com.waldm.mastermind.Result;

import java.util.List;

public class FeedbackPegs extends LinearLayout{
    private final List<Peg> pegs = Lists.newArrayList();

    public FeedbackPegs(Context context, int numberOfPegs) {
        super(context);
        setOrientation(HORIZONTAL);

        for (int i = 0; i < numberOfPegs; i++) {
            Peg peg = new Peg(context, MainActivity.Colour.NONE, null);
            pegs.add(peg);
            addView(peg);
        }
    }

    public void update(Result result) {
        int correctLocation = result.locationCorrect;
        int correctColour = result.numberCorrect;

        for (Peg peg : pegs) {
            if (correctLocation > 0) {
                peg.setColour(MainActivity.Colour.BLACK);
                correctLocation--;
            } else if (correctColour > 0) {
                peg.setColour(MainActivity.Colour.WHITE);
                correctColour--;
            }
        }
    }
}
