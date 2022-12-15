
/* ~15.12.2022~
 * ╔╦╗╦ ╦╔═╗┌─┐┬┌┬┐┌─┐┬  ┌─┐  ┌─┐┬─┐┌─┐┌─┐┌─┐┌┐┌┌┬┐┌─┐   
 *  ║ ╠═╣╠═╣├─┘││││├─┘│  ├┤   ├─┘├┬┘├┤ └─┐├┤ │││ │ └─┐   
 *  ╩ ╩ ╩╩ ╩┴  ┴┴ ┴┴  ┴─┘└─┘  ┴  ┴└─└─┘└─┘└─┘┘└┘ ┴ └─┘
 *  /------------------------------------------------------------------------------\
 * |                                                                                |
 * |  ░█████╗░░██████╗░█████╗░██╗██╗  ███████╗██╗██╗░░░░░████████╗███████╗██████╗░  |
 * |  ██╔══██╗██╔════╝██╔══██╗██║██║  ██╔════╝██║██║░░░░░╚══██╔══╝██╔════╝██╔══██╗  |
 * |  ███████║╚█████╗░██║░░╚═╝██║██║  █████╗░░██║██║░░░░░░░░██║░░░█████╗░░██████╔╝  |
 * |  ██╔══██║░╚═══██╗██║░░██╗██║██║  ██╔══╝░░██║██║░░░░░░░░██║░░░██╔══╝░░██╔══██╗  |
 * |  ██║░░██║██████╔╝╚█████╔╝██║██║  ██║░░░░░██║███████╗░░░██║░░░███████╗██║░░██║  |
 * |  ╚═╝░░╚═╝╚═════╝░░╚════╝░╚═╝╚═╝  ╚═╝░░░░░╚═╝╚══════╝░░░╚═╝░░░╚══════╝╚═╝░░╚═╝  |
 *  \------------------------------------------------------------------------------/
 * Coded in about one lecture and with love <3, could be useful, probably is not
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ASCIIFILTER {
    public static void main(String args[]) throws FileNotFoundException {
        BufferedImage img = null;
        // getting the image file
        try {
            img = ImageIO.read(new File(args[0]));
        } catch (IOException e) {
            System.out.println(e);
        }
        // output declarations
        PrintWriter out = new PrintWriter(new File("./output.txt")); // declaration of the output.txt file
        String s = ""; // output String

        // getting the image dimensions, for those reading, powers of 2 is recomended,
        // looks best
        int image_h = img.getHeight();
        int image_w = img.getWidth();

        // hardcode brightness offset :/, can change it, just my personal choice
        int offset = -85;

        // getting the pixel data and turning it into the wierd ascii boxes
        for (int i = 0; i < image_h; i++) { // "height pointer"
            for (int j = 0; j < image_w; j++) { // "width pointer"
                int p = img.getRGB(j, i);

                int image_r = (p >> 16) & 0xff;
                int image_g = (p >> 8) & 0xff; // getting the single color values
                int image_b = p & 0xff;

                int image_brightness = (image_b + image_g + image_r) / 3; // calculating brightness
                if (j == image_w - 1) {
                    s += "\n";
                }

                // brightness limits for the right characters, yes looks cursed
                if (image_brightness <= 64 + offset) {
                    s += "███";
                } else if (64 + offset < image_brightness && image_brightness < 129 + offset) {
                    s += "▓▓▓";
                } else if (129 + offset < image_brightness && image_brightness < 193 + offset) {
                    s += "▒▒▒";
                } else {
                    s += "░░░";
                }
            }

            // progress bar
            int progress = 10 - (int) (((double) (image_h - 1 - i) / (double) image_h) * 10);
            String progress_string = "";
            for (int c = 0; c < progress; c++)
                progress_string += "█";
            for (int c = 0; c < 10 - progress; c++)
                progress_string += " ";
            System.out.println("Progress : [" + progress_string + "]" + progress * 10 + "%");
        }
        System.out.println("Done.");
        out.println(s);
        out.close();
    }
}
// 78 79 32 77 65 73 68 69 78 83 63