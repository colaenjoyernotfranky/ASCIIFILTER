
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

public class ASCIIFILTER {
    // constructor
    public ASCIIFILTER() {

    }

    // public methods
    public void asciify(String pathname, int scaled_w, int scaled_h, int brightness_offset) {
        try {
            String outName = pathname.substring(pathname.lastIndexOf("/") + 1, pathname.lastIndexOf("."));
            BufferedImage img = getInputImage(pathname); // input image
            PrintWriter out = new PrintWriter(new File("./" + outName + ".txt")); // declaration of the output.txt file
            int offset = 0;
            if (brightness_offset != 0)
                offset = -brightness_offset;
            BufferedImage scaled_image = null;
            if (scaled_h != 0 || scaled_w != 0)
                scaled_image = scaleImage(img, scaled_w, scaled_h); // scaling image
            else
                scaled_image = scaleImage(img, img.getWidth(), img.getHeight());
            String s = toAscii(scaled_image, offset); // output String
            output(s, out);
        } catch (Exception e) {
            System.out.println(
                    "Usage: java ASCIIFILTER <pathname> [<width> <height>] [<brightness_offset>]");
        }
    }

    // private methods
    private int getPixelBrightness(BufferedImage img, int x, int y) { // calculates the brightness of a specific pixel
        int p = img.getRGB(x, y); // getting the pixel
        int image_r = (p >> 16) & 0xff;
        int image_g = (p >> 8) & 0xff; // getting the rgb values
        int image_b = p & 0xff;
        int pixel_brightness = (image_b + image_g + image_r) / 3; // calculating based on average of rgb values
        return pixel_brightness;
    }

    private BufferedImage getInputImage(String pathname) throws FileNotFoundException { // gets the image file
        BufferedImage img = null;
        // getting the image file
        try {
            img = ImageIO.read(new File(pathname)); // set the file at pathname as image
        } catch (IOException e) {
            System.out.println(e);
        }
        return img;
    }

    private BufferedImage scaleImage(BufferedImage img, int new_w, int new_h) { // scaling algorithm
        BufferedImage sampled_image = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB); // declare new image
        Graphics2D graphics2D = sampled_image.createGraphics();
        graphics2D.drawImage(img, 0, 0, new_w, new_h, null); // draw old image on new
        graphics2D.dispose();
        return sampled_image;
    }

    private String toAscii(BufferedImage img, int offset) {
        String s = "";
        for (int i = 0; i < img.getHeight(); i++) { // "height pointer"
            for (int j = 0; j < img.getWidth(); j++) { // "width pointer"
                int image_brightness = getPixelBrightness(img, j, i); // calculating brightness

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
            s += "\n"; // next line when the width loop is done
        }
        return s;
    }

    private void output(String output_string, PrintWriter out) { // outputs string to file
        System.out.println("Done.");
        out.println(output_string);
        out.close();
    }

    public static void main(String args[]) {
        try {
            ASCIIFILTER a = new ASCIIFILTER();
            if (args.length < 3 && args.length >= 1) {
                a.asciify(args[0], 0, 0, 0);
            }
            if (args.length == 3)
                a.asciify(args[0], Integer.parseInt(args[1]),
                        Integer.parseInt(args[2]), 0);
            if (args.length == 4)
                a.asciify(args[0], Integer.parseInt(args[1]),
                        Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        } catch (Exception e) {
            System.out.println("Usage: java ASCIIFILTER <pathname> [<width> <height>] [<brightness_offset>]");
            System.out.println("False arguments or missing pathname");
        }
    }
}

// 78 79 32 77 65 73 68 69 78 83 63