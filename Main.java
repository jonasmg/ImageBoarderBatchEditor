
public class Main {
    public static void main(String[] args) {
        int pixelWidthOutput = 1920;
        int pixelHeightOutput = pixelWidthOutput;
        
        int boarderPercent = 2;
        
        int jpegQuality = 80;

        int[] information = new int[4];
        information[0] = pixelWidthOutput;
        information[1] = pixelHeightOutput;
        information[2] = boarderPercent;
        information[3] = jpegQuality;
        boolean squareFirst = true;

        String filePath = "IncomingPics";
        String[] incomingClips = FileProcessor.getFilesDefault(filePath);

        for (String clip : incomingClips) {
            if (clip != null) {
                String inputFilePath = filePath + "/" + clip;
                String outputFilePath = "OutputPics/" + clip;
                ImageProcessor.addBorderToImage(inputFilePath, outputFilePath, information, squareFirst);
            }
        }

    }
}
