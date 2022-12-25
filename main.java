
public class Main {
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
            System.out.println("Usage: java ASCIIFILTER <image_pathname> [<width> <height>] [<brightness_offset>]");
            System.out.println("False arguments or missing pathname");
        }
    }
}
