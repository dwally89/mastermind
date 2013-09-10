package com.waldm.MastermindAndroid.views;

import android.content.Context;
import android.widget.LinearLayout;
import com.google.common.collect.Lists;
import com.waldm.MastermindAndroid.MainActivity;
import com.waldm.mastermind.Result;

import java.util.List;

public class PegRow extends LinearLayout{
    private final FeedbackPegs feedbackPegs;
    private List<Peg> pegs = Lists.newArrayList();

    public PegRow(Context context, List<Peg> pegs, boolean showFeedbackPegs) {
        super(context);
        setOrientation(HORIZONTAL);

        this.pegs = pegs;

        for (Peg peg : pegs) {
            addView(peg);
        }

        if (showFeedbackPegs) {
            feedbackPegs = new FeedbackPegs(context, pegs.size());
            LinearLayout.LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            params.weight = 1.5f;
            addView(feedbackPegs, params);
        } else {
            feedbackPegs = null;
        }
    }

    public List<Peg> getPegs() {
        return pegs;
    }

    public void setColours(List<Peg> colours) {
        for (int i = 0; i < pegs.size(); i++) {
            MainActivity.Colour colour = colours.get(i).getColour();
            pegs.get(i).setColour(colour);
        }
    }

    public void setResult(Result result) {
        feedbackPegs.update(result);
    }
}
