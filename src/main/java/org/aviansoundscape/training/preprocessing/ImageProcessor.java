package org.aviansoundscape.training.preprocessing;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor implements FileProcessor{
    @Override
    public double[] processFile(String filePath){
        return new double[0]; //TODO
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth){
        return Scalr.resize(originalImage, targetWidth);
    }

    public static double[] getPixelArray(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();

        double[] pixelArray = new double[width * height * 3]; // Assuming 3 color channels (RGB)
        int index = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Normalize the pixel values to the range of 0.0 to 1.0
                double normalizedRed = red / 255.0;
                double normalizedGreen = green / 255.0;
                double normalizedBlue = blue / 255.0;

                pixelArray[index] = normalizedRed;
                pixelArray[index++] = normalizedGreen;
                pixelArray[index++] = normalizedBlue;
                index ++;
            }
        }

        return pixelArray;
    }

    public static BufferedImage getImage(String imagePath){
        File imageFile = new File(imagePath);
        BufferedImage image;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }
}
