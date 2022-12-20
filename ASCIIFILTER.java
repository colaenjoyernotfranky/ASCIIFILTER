
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
    // constructors
    public ASCIIFILTER(String image_pathname) throws FileNotFoundException {
        try {
            BufferedImage img = getInputImage(image_pathname); // input image
            PrintWriter out = new PrintWriter(new File("./output.txt")); // declaration of the output.txt file
            String s = toAscii(img, -85); // output String
            output(s, out);
        } catch (Exception e) {
            System.out.println("Usage: java ASCIIFILTER <image_pathname> [<width> <height>] [<brightness_offset>]");
        }
    }

    public ASCIIFILTER(String image_pathname, int scaled_w, int scaled_h) throws FileNotFoundException {
        try {
            BufferedImage img = getInputImage(image_pathname); // input image
            PrintWriter out = new PrintWriter(new File("./output.txt")); // declaration of the output.txt file
            BufferedImage scaled_image = scaleImage(img, scaled_w, scaled_h); // scaling image
            String s = toAscii(scaled_image, -85); // output String
            output(s, out);

        } catch (Exception e) {
            System.out.println("Usage: java ASCIIFILTER <image_pathname> [<width> <height>] [<brightness_offset>]");
        }
    }

    public ASCIIFILTER(String image_pathname, int scaled_w, int scaled_h, int brightness_offset) {
        try {
            BufferedImage img = getInputImage(image_pathname); // input image
            PrintWriter out = new PrintWriter(new File("./output.txt")); // declaration of the output.txt file
            int offset = -brightness_offset;
            BufferedImage scaled_image = scaleImage(img, scaled_w, scaled_h); // scaling image
            String s = toAscii(scaled_image, offset); // output String
            output(s, out);
        } catch (Exception e) {
            System.out.println("Usage: java ASCIIFILTER <image_pathname> [<width> <height>] [<brightness_offset>]");
        }
    }

    // private methods
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
                int p = img.getRGB(j, i);

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
            // different constructors for different arguments
            ASCIIFILTER a = null;
            if (args.length < 3 && args.length >= 1)
                a = new ASCIIFILTER(args[0]);
            if (args.length == 3)
                a = new ASCIIFILTER(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            if (args.length == 4)
                a = new ASCIIFILTER(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                        Integer.parseInt(args[3]));
        } catch (Exception e) {
            System.out.println("Usage: java ASCIIFILTER <image_pathname> [<width> <height>] [<brightness_offset>]");
            System.out.println("False arguments or missing pathname");
        }
    }
}

// 78 79 32 77 65 73 68 69 78 83 63