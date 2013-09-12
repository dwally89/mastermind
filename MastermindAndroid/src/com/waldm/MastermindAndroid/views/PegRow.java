package com.waldm.MastermindAndroid.views;

import android.content.Context;
import android.widget.LinearLayout;
import com.google.common.collect.Lists;
import com.waldm.MastermindAndroid.Colour;
import com.waldm.MastermindAndroid.R;
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
            LinearLayout.LayoutParams pegParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            feedbackPegs.setPadding(
                    getResources().getDimensionPixelSize(R.dimen.peg_row_margin),
                    getResources().getDimensionPixelSize(R.dimen.peg_row_margin),
                    getResources().getDimensionPixelSize(R.dimen.peg_row_margin),
                    getResources().getDimensionPixelSize(R.dimen.peg_row_margin)
                    );
            pegParams.weight = 1.5f;
            addView(feedbackPegs, pegParams);
        } else {
            feedbackPegs = null;
        }
    }

    public List<Peg> getPegs() {
        return pegs;
    }

    public void setColours(List<Peg> colours) {
        for (int i = 0; i < pegs.size(); i++) {
            Colour colour = colours.get(i).getColour();
            pegs.get(i).setColour(colour);
        }
    }

    public void setResult(Result result) {
        feedbackPegs.update(result);
    }

    public void setFeedbackPegsListener(OnClickListener listener) {
        feedbackPegs.setPegsOnClickListener(listener);
    }

    public void removeFeedbackPegsListener() {
        feedbackPegs.removePegsOnClickListener();
    }

    public Result getFeedbackPegsResult() {
        return feedbackPegs.getResult();
    }
}
