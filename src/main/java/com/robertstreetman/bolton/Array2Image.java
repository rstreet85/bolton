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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * This class is designed to minimize amount of code needed to get an image file
 * from an array of pixel values. Supports multiple color spaces & array input
 * options.
 */
public class Array2Image {
    
    public static void RGBImage(int[][][] RGBArray, int[][] mask, String filename, String ext) throws Exception {
        if (RGBArray.length == 0 || RGBArray[0].length == 0) {
            throw new Exception("ERROR: Cannot convert an array of length 0!");
        }
        
        if (RGBArray[0][0].length < 3) {
            throw new Exception("ERROR: Pixel value has incorrect format!");
        }
        
        BufferedImage buffered = new BufferedImage(RGBArray[0].length, RGBArray.length, BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i < RGBArray.length; i++) {
            for (int j = 0; j < RGBArray[0].length; j++) {
                if (mask[i][j] == 1) {
                    buffered.setRGB(j, i, (RGBArray[i][j][0] << 16) | (RGBArray[i][j][1] << 8) | RGBArray[i][j][2]);
                } else {
                    buffered.setRGB(j, i, 0);
                }
            }
        }
        
        try {
            ImageIO.write(buffered, ext, new File(filename));
        } catch (IOException e) {
            System.out.println("ERROR: Cannot write to file: " + e.getMessage());
        }
    }
}
