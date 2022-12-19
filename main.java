
public class main {
    public static void main(String args[]) {
        try {
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
