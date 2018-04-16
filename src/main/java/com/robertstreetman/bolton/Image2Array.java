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
 * This class is designed to minimize amount of code needed to get an array of
 * pixel values from an image file. Supports multiple color spaces & array output
 * options.
 */
public class Image2Array {
    
    public static int[][][] RGBArray(String filename) {
        int[][][] RGBArray = null;
        
        try {
            BufferedImage buffered = ImageIO.read(new File(filename));
            int width = buffered.getWidth();
            int height = buffered.getHeight();
            RGBArray = new int[height][width][3];
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixel = buffered.getRGB(j, i);
                    RGBArray[i][j][0] = validateValue((pixel >> 16) & 0xff);
                    RGBArray[i][j][1] = validateValue((pixel >> 8) & 0xff);
                    RGBArray[i][j][2] = validateValue(pixel & 0xff);
                }            
            }
        } catch (IOException e) {
            System.out.println("ERROR: Cannot read file: " + e.getMessage());
        }
        
        return RGBArray;
    }
    
    public static int[][] GrayscaleArray(String filename) {
        int[][] GSArray = null;
        
        try {
            BufferedImage buffered = ImageIO.read(new File(filename));
            int width = buffered.getWidth();
            int height = buffered.getHeight();
            int pixel;
            GSArray = new int[height][width];
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    pixel = buffered.getRGB(j, i);
                    GSArray[i][j] = (validateValue((pixel >> 16) & 0xff)
                            + validateValue((pixel >> 8) & 0xff)
                            + validateValue(pixel & 0xff)) / 3;
                }            
            }
        } catch (IOException e) {
            System.out.println("ERROR: Cannot read file: " + e.getMessage());
        }
        
        return GSArray;
    }
    
    public static double[][][] HSVArray(String filename) {
        double[][][] HSVArray = null;
        
        try {
            BufferedImage buffered = ImageIO.read(new File(filename));
            int width = buffered.getWidth();
            int height = buffered.getHeight();
            HSVArray = new double[height][width][3];
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    double[] rgbPrime = new double[3];
                    double cmax, cmin;
                    int pixel = buffered.getRGB(j, i);
                    rgbPrime[0] = validateValue((pixel >> 16) & 0xff) / 255.;
                    rgbPrime[1] = validateValue((pixel >> 8) & 0xff) / 255.;
                    rgbPrime[2] = validateValue(pixel & 0xff) / 255.;
                    
                    if (rgbPrime[0] > rgbPrime[1] && rgbPrime[0] > rgbPrime[2]) {
                        cmax = rgbPrime[0];
                        cmin = (rgbPrime[1] < rgbPrime[2]) ? rgbPrime[1] : rgbPrime[2];
                        HSVArray[i][j][0] = 60 * (((rgbPrime[1] - rgbPrime[2]) / (cmax - cmin)) % 6);
                    } else if (rgbPrime[1] > rgbPrime[0] && rgbPrime[1] > rgbPrime[2]) {
                        cmax = rgbPrime[1];
                        cmin = (rgbPrime[0] < rgbPrime[2]) ? rgbPrime[0] : rgbPrime[2];
                        HSVArray[i][j][0] = 60 * (((rgbPrime[2] - rgbPrime[0]) / (cmax - cmin)) + 2);
                    } else if (rgbPrime[2] > rgbPrime[1] && rgbPrime[2] > rgbPrime[0]){
                        cmax = rgbPrime[2];
                        cmin = (rgbPrime[0] < rgbPrime[1]) ? rgbPrime[0] : rgbPrime[1];
                        HSVArray[i][j][0] = 60 * (((rgbPrime[0] - rgbPrime[1]) / (cmax - cmin)) + 4);
                    } else {
                        cmax = 0;
                        cmin = 0;
                        HSVArray[i][j][0] = 0;
                    }
                    
                    HSVArray[i][j][1] = (cmax == 0) ? 0 : (cmax - cmin) / cmax;
                    HSVArray[i][j][2] = cmax;                    
                }            
            }
        } catch (IOException e) {
            System.out.println("ERROR: Cannot read file: " + e.getMessage());
        }
        
        return HSVArray;
    }
    
    /*
     * "Xerox Color Encoding Standard". XNSS 289005, 1989.
    */
    //Untested
    public static double[][][] YESArray(String filename) {
        double[][][] YESArray = null;
        
        try {
            BufferedImage buffered = ImageIO.read(new File(filename));
            int width = buffered.getWidth();
            int height = buffered.getHeight();
            YESArray = new double[height][width][3];
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int[] rgb = new int[3];
                    int pixel = buffered.getRGB(j, i);
                    
                    rgb[0] = validateValue((pixel >> 16) & 0xff);
                    rgb[1] = validateValue((pixel >> 8) & 0xff);
                    rgb[2] = validateValue(pixel & 0xff);
                    
                    YESArray[i][j][0] = 0.253 * rgb[0] + 0.684 * rgb[1] + 0.063 * rgb[2];
                    YESArray[i][j][1] = 0.5 * rgb[0] - 0.5 * rgb[1];
                    YESArray[i][j][2] = 0.25 * rgb[0] + 0.25 * rgb[1] - 0.5 * rgb[2];
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: Cannot read file: " + e.getMessage());
        }
        
        return YESArray;
    }
    
    /*
    //Untested
    public static double[][][] TSLArray(String filename) {
        double[][][] TSLArray = null;
        double[] rgPrime = new double[2];
        int[] rgb = new int[3];
        int r, g;
        
        try {
            BufferedImage buffered = ImageIO.read(new File(filename));
            int width = buffered.getWidth();
            int height = buffered.getHeight();
            int pixel;
            
            TSLArray = new double[height][width][3];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    pixel = buffered.getRGB(j, i);
                    rgb[0] = validateValue((pixel >> 16) & 0xff);
                    rgb[1] = validateValue((pixel >> 8) & 0xff);
                    rgb[2] = validateValue(pixel & 0xff);
                    rgPrime[0] = (rgb[0] / 255.) - (1. / 3.);
                    rgPrime[1] = (rgb[1] / 255.) - (1. / 3.);
                    r = rgb[0] / (rgb[0] + rgb[1] + rgb[2]);
                    g = rgb[1] / (rgb[0] + rgb[1] + rgb[2]);
                    
                    if (rgPrime[1] > 0) {
                        TSLArray[i][j][0] = (1 / (2 * Math.PI)) * Math.atan((rgPrime[0] / rgPrime[1]) + 0.25);
                    } else if (rgPrime[1] < 0) {
                        TSLArray[i][j][0] = (1 / (2 * Math.PI)) * Math.atan((rgPrime[0] / rgPrime[1]) + 0.75);
                    } else {
                        TSLArray[i][j][0] = 0;
                    }
                    
                    TSLArray[i][j][1] = Math.sqrt((9. / 5.) * (rgPrime[0] * rgPrime[0] + rgPrime[1] * rgPrime[1]));
                    
                    TSLArray[i][j][0] = 0.299 * rgb[0] + 0.587 * rgb[1] + 0.114 * rgb[2];
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: Cannot read file: " + e.getMessage());
        }
        
        return TSLArray;
    }
    */
    
    private static int validateValue(int pxVal) {
        return (pxVal > 255) ? 255 : (pxVal < 0) ? 0 : pxVal;
    }
}
