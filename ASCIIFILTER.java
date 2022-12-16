
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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ASCIIFILTER { // I know this is all a bit messy, but this was never meant to be a big thing
                           // we will see
    public static void main(String args[]) throws FileNotFoundException {
        try {
            BufferedImage img = null;
            // getting the image file
            try {
                img = ImageIO.read(new File(args[0])); // args[0] == pathname for image
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

            // second argument of initialization sets the brightness offset [DEFAULT = 85]
            int offset = -85;
            if (args.length == 4)
                offset = -Integer.parseInt(args[3]); // args[3] == negative offset value

            // sampling
            int image_h_ = image_h;
            int image_w_ = image_w;
            BufferedImage sampled_image = img;
            if (args.length == 3) { // sample the image only if width and height are given as arguments
                image_h_ = Integer.parseInt(args[1]); // args[1] == sampled image width
                image_w_ = Integer.parseInt(args[2]); // args[2] == sampled image height
                sampled_image = new BufferedImage(image_w_, image_h_, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2D = sampled_image.createGraphics();
                graphics2D.drawImage(img, 0, 0, image_w_, image_h_, null);
                graphics2D.dispose();
            }

            // getting the pixel data and turning it into the ascii boxes
            for (int i = 0; i < image_h_; i++) { // "height pointer"
                for (int j = 0; j < image_w_; j++) { // "width pointer"
                    int p = sampled_image.getRGB(j, i);

                    int image_r = (p >> 16) & 0xff;
                    int image_g = (p >> 8) & 0xff; // getting the single color values
                    int image_b = p & 0xff;

                    int image_brightness = (image_b + image_g + image_r) / 3; // calculating brightness

                    // brightness limits for the right characters, yes looks cursed
                    if (image_brightness <= 64 + offset) {
                        s += "██";
                    } else if (64 + offset < image_brightness && image_brightness < 129 + offset) {
                        s += "▓▓";
                    } else if (129 + offset < image_brightness && image_brightness < 193 + offset) {
                        s += "▒▒";
                    } else {
                        s += "░░";
                    }
                }
                s += "\n";
                // progress bar
                int progress = 10 - (int) (((double) (image_h_ - 1 - i) / (double) image_h_) * 10); // Ugly
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
        } catch (Exception e) {
            System.out.println("Usage: java ASCIIFILTER <image_pathname> [<width> <height>] [<brightness_offset>]");
        }
    }
}
// 78 79 32 77 65 73 68 69 78 83 63