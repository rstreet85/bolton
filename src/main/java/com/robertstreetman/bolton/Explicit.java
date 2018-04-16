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

/*
 * This class gives access to published explicit rules for skin pixels. Rules are
 * separated by publication, with accompanying citation.
 */
public class Explicit {
    
    /*
     * G Gomez, M Sanchez, LE Sucar. 2002. On selecting an appropriate colour space
     * for skin detection. MICAI 2002.
     *
     * Color space: YES
    */
    public static int[][] ExplicitYES(int[][][] rgb) {
        int[][] mask = new int[rgb.length][rgb[0].length];

        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask[0].length; j++) {
                if (0.5 * (rgb[i][j][0] - rgb[i][j][1]) > 13.4224   // Y = (red - green) / 2
                        && (double) rgb[i][j][0] / (double) rgb[i][j][1] < 1.7602) {
                    mask[i][j] = 1;
                } else {
                    mask[i][j] = 0;
                }
            }
        }
        
        return mask;
    }    
}
