import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;
 
import static java.lang.System.out;
 
/**
 * Originally written on 1/19/2016.
 */
 
 
public class ASCIIart {
 
    static BufferedImage imgRead = null;
    static BufferedWriter writer = null;
 
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        int tempInp = 0;
        int artWidth = 0;
        int artHeight = 0;
 
        try {
            writer = new BufferedWriter(new FileWriter("output.txt"));
        } catch (Exception e) {
            out.println("Unable to create text file for output.");
            System.exit(0);
        }
 
        JButton open = new JButton();
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle("Select your image to convert");
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setPreferredSize(new Dimension(800, 600));
        if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION)
        {
        	
        }
        //get a valid BufferedImage
        try {
            imgRead = ImageIO.read(new File(fc.getSelectedFile().getAbsolutePath()));
        } catch (NoSuchFileException e) {
            out.println("Please put a .jpg image into the folder.");
            System.exit(0);
        } catch (Exception e) {
            out.println("Please select a valid image file.");
            System.exit(0);
        }
        
        int nHeight = imgRead.getHeight();
        int nWidth = imgRead.getWidth();
        //get the desired width (in characters), making sure it is less than the image width and not 0 or less.
        boolean invalidInp = false;
        out.println("How many characters wide should the output be?");
        do {
            try {
                tempInp = Integer.parseInt(inp.nextLine());
                invalidInp = false;
            } catch (Exception e) {
                out.println("Please input a number.");
                invalidInp = true;
            }
            if (tempInp > imgRead.getWidth() && !invalidInp) {
                out.println("Please input a number that is less than or equal to the original dimensions of the picture.");
                invalidInp = true;
            } else if (tempInp < 1 && !invalidInp) {
                out.println("Please input a positive number.");
                invalidInp = true;
            }
        } while (invalidInp);
        
        artWidth = nWidth / tempInp;
        //Text is twice as tall as it is wide, so we have to half the number of characters per pixel height-wise
        artHeight = artWidth * 2;
 
        //shaves off pixels that make the image dimensions indivisible with the desired width
        if (imgRead.getHeight() % artHeight != 0) {
            nHeight = nHeight - (imgRead.getHeight() % artHeight);
        }
        if (imgRead.getWidth() % artWidth != 0) {
            nWidth = nWidth - (imgRead.getWidth() % artWidth);
        }
 
        int count = 0;
        for (int r = 0; r < nHeight; r += artHeight) {
            for (int c = 0; c < nWidth; c += artWidth) {
                int totalR = 0;
                int totalG = 0;
                int totalB = 0;
                Double avgBright = 0.0;
                for (int x = c; x < c + artWidth; x++) {
                    for (int y = r; y < r + artHeight; y++) {
                        Color getColor = new Color(imgRead.getRGB(x, y));
                        totalR += getColor.getRed();
                        totalG += getColor.getGreen();
                        totalB += getColor.getBlue();
                        count++;
                    }
                }
                avgBright = (Math.sqrt(0.299 * totalR * totalR + 0.587 * totalG * totalG + 0.114 * totalB * totalB) / (artWidth * artHeight));
                try {
                    printChar(avgBright);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            try {
                writer.newLine();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inp.close();
        out.close();
        System.out.println("Completed. Check 'output.txt'");
    }
 
    private static void printChar(Double avgBright) throws IOException {
    	if (avgBright < 255 * (1.0/16.0)) {
            writer.write('█');
            return;
        }
    	else if (avgBright < 255 * (2.0/16.0)) {
            writer.write('▓');
            return;
        }
    	else if (avgBright < 255 * (3.0/16.0)) {
            writer.write('▒');
            return;
        }
    	else if (avgBright < 255 * (4.0/16.0)) {
            writer.write('░');
            return;
        }
    	else if (avgBright < 255 * (5.0/16.0)) {
            writer.write('X');
            return;
        }
    	else if (avgBright < 255 * (6.0/16.0)) {
            writer.write('R');
            return;
        }
    	else if (avgBright < 255 * (7.0/16.0)) {
            writer.write('Y');
            return;
        }
    	else if (avgBright < 255 * (8.0/16.0)) {
            writer.write('7');
            return;
        }
    	else if (avgBright < 255 * (9.0/16.0)) {
            writer.write('1');
            return;
        }
    	else if (avgBright < 255 * (10.0/16.0)) {
            writer.write(':');
            return;
        }
    	else if (avgBright < 255 * (11.0/16.0)) {
            writer.write('<');
            return;
        }
    	else if (avgBright < 255 * (12.0/16.0)) {
            writer.write('-');
            return;
        }
    	else if (avgBright < 255 * (13.0/16.0)) {
            writer.write(',');
            return;
        }
    	else if (avgBright < 255 * (14.0/16.0)) {
            writer.write('.');
        }
    	else if(avgBright < 255 * (15.0/16.0)) {
        	writer.write('`');
        }
        else {
            writer.write(' ');
        }
    }
}