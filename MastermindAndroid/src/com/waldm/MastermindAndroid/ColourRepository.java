package com.waldm.MastermindAndroid;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class ColourRepository {
    private static ImmutableList<Colour> colours = new ImmutableList.Builder<Colour>()
            .add(new Colour("Red", 'R', R.drawable.red_peg))
            .add(new Colour("Orange", 'O', R.drawable.orange_peg))
            .add(new Colour("Yellow", 'Y', R.drawable.yellow_peg))
            .add(new Colour("Green", 'G', R.drawable.green_peg))
            .add(new Colour("Blue", 'B', R.drawable.blue_peg))
            .add(new Colour("Purple", 'P', R.drawable.purple_peg))
            .build();

    private final ImmutableList<Colour> availableColours;

    public ColourRepository(int numberOfColours) {
        ImmutableList.Builder<Colour> availableColoursBuilder = new ImmutableList.Builder<Colour>();
        for (int i = 0; i < numberOfColours; i++) {
            availableColoursBuilder.add(colours.get(i));
        }

        availableColours = availableColoursBuilder.build();
    }

    public List<Colour> getAvailableColours() {
        return availableColours;
    }
}
