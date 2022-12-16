# ASCIIFILTER

Do you want to turn your pictures in a bad Ascii version?
Then you have come to the right place.

To use it for now, you need to actualy compile it with javac and start it with the java command. It will output a text File in which your picture is avalible in 4 
different shades of brightness.

Usage: java ASCIIFILTER <image_pathname> <brightness_offset>.

I recommend using pictures up to 256x256, because even though the algorithm handles bigger dimensions, the pictures are not shown correctly in the Windows Text Editor. If you use Notepad++ for example, it works fine, though I still do not recommend going above 512x512 it, since it still is not optimized fo it. When it is done, the output will be in the output.txt file. You will probably need to zoom out depending on your input picture.

That is it for now, if I ever come back to this, im planing on making it possible to fetch video files and turn them into moving Ascii pictures.
Also probably try to optimize it.

Have fun!
