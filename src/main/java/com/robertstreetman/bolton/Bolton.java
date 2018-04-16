/*
 * Copyright 2018 Robert Streetman
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
