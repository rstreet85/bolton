/*
 * Copyright 2016 Robert Streetman
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.robertstreetman.bolton;

public class Bolton {
    private static String filenameIn;
    private static String filenameOut;
    private static final String[] METHODS = {"explicityes",""};

    public static void main(String[] args) throws Exception {
        filenameIn = args[0];
        filenameOut = filenameIn.split("\\.")[0] + "_out.png";
        String method = args[1];
        
        //Only explicit mode is supported for now
        if (method.equals(METHODS[0])) {
            explicitYes();
        }
    }

    private static void explicitYes() throws Exception {
        int[][][] RGBArray = Image2Array.RGBArray(filenameIn);
        System.out.println("Opening image file " + filenameIn + ": Dimensions: " + RGBArray.length
                + "px x " + RGBArray[0].length + "px...");
        
        int[][] skinMask = Explicit.ExplicitYES(RGBArray);
        Array2Image.RGBImage(RGBArray, skinMask, filenameOut, "png");
        
        System.out.println("Wrote image to file " + filenameOut + "...");
    }
}
