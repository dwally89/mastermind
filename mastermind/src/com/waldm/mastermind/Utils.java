package com.waldm.mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Utils {
    public static String readStringFromConsole() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(isr);
        return buffer.readLine();
    }
}
