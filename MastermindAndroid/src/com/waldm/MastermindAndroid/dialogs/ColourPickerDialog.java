package com.waldm.MastermindAndroid.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.google.common.collect.Lists;
import com.waldm.MastermindAndroid.Colour;
import com.waldm.MastermindAndroid.ColourRepository;
import com.waldm.MastermindAndroid.HumanGameActivity;
import com.waldm.MastermindAndroid.R;
import com.waldm.MastermindAndroid.views.Peg;
import com.waldm.MastermindAndroid.views.PegRow;

import java.util.List;

public class ColourPickerDialog extends Activity implements Peg.PegClickListener {
    public static final String COLOUR_KEY = "ColourKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int numberOfAvailableColours = getIntent().getIntExtra(HumanGameActivity.NUMBER_OF_AVAILABLE_COLOURS, -1);
        final ColourRepository colourRepository = new ColourRepository(numberOfAvailableColours);

        RelativeLayout mainLayout = new RelativeLayout(this);
        int padding = Math.round(getResources().getDimension(R.dimen.colour_picker_dialog_padding));
        mainLayout.setPadding(padding, padding, padding, padding);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        List<Peg> pegs = Lists.newArrayList();
        for (Colour colour : colourRepository.getAvailableColours()) {
            pegs.add(new Peg(this, colour, this));
        }

        PegRow pegRow = new PegRow(this, pegs, false);
        mainLayout.addView(pegRow, params);

        setContentView(mainLayout, params);

        setTitle(R.string.colour_picker_title);

        Window window = getWindow();
        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.copyFrom(window.getAttributes());
        windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowParams.height = getResources().getDimensionPixelSize(R.dimen.colour_picker_dialog_height);
        window.setAttributes(windowParams);
    }

    @Override
    public void onPegClick(Peg peg) {
        Intent intent = new Intent();
        intent.putExtra(COLOUR_KEY, peg.getColour().name);
        setResult(HumanGameActivity.RESULT_COLOUR_PICKED, intent);
        finish();
    }
}
