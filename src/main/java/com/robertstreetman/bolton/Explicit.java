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
