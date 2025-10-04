import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageProcessor {

    public static void addBorderToImage(String inputFilePath, String outputFilePath, int[] information, boolean squareFirst) {
        try {
            File inputFile = new File(inputFilePath);
            BufferedImage inputImage = ImageIO.read(inputFile);

            int targetWidth = information[0];
            int targetHeight = information[1];
            int borderPercent = information[2];
            int jpegQuality = information[3]; // currently unused, needs ImageWriter for JPEG

            int borderWidth = (int) (targetWidth * borderPercent / 100.0);
            int borderHeight = (int) (targetHeight * borderPercent / 100.0);

            int newWidth = targetWidth - 2 * borderWidth;
            int newHeight = targetHeight - 2 * borderHeight;

            int originalWidth = inputImage.getWidth();
            int originalHeight = inputImage.getHeight();

            double aspectRatio = (double) originalWidth / originalHeight;
            double targetAspectRatio = (double) newWidth / newHeight;

            if (squareFirst) {
                if (aspectRatio > 1) {
                    newHeight = (int) (newWidth / aspectRatio);
                } else {
                    newWidth = (int) (newHeight * aspectRatio);
                }
            } else {
                if (aspectRatio > targetAspectRatio) {
                    newWidth = (int) (newHeight * aspectRatio);
                } else {
                    newHeight = (int) (newWidth / aspectRatio);
                }
            }

            // Resize original image
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            // Create output image with border
            BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            g2d = outputImage.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, targetWidth, targetHeight);
            int xOffset = (targetWidth - newWidth) / 2;
            int yOffset = (targetHeight - newHeight) / 2;
            g2d.drawImage(resizedImage, xOffset, yOffset, null);
            g2d.dispose();

            // Save as JPEG (basic ImageIO without quality control)
            ImageIO.write(outputImage, "jpg", new File(outputFilePath));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Simple test
    public static void main(String[] args) {
        // [width, height, borderPercent, jpegQuality]
        int[] info = {800, 800, 10, 90};
        addBorderToImage("input.jpg", "output.jpg", info, false);
    }
}
