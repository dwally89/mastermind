package com.waldm.MastermindAndroid;

public class Colour {
    public static final Colour GREY = new Colour("Grey", 'G', R.drawable.unused_peg);
    public static final Colour WHITE = new Colour("White", 'W', R.drawable.white_peg);
    public static final Colour BLACK = new Colour("Black", 'B', R.drawable.black_peg);
    public static final Colour NONE = new Colour("NONE", 'N', R.drawable.transparent_peg);
    public final int drawableResource;
    public final String name;
    public final char shortName;

    public Colour(String name, char shortName, int drawableResource) {
        this.drawableResource = drawableResource;
        this.name = name;
        this.shortName = shortName;
    }
}
