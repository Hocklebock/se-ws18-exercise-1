import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Image {
	public int width;
	public int height;
	public byte[] data;
	
	public Image (int width, int height) {
		this.width = width;
		this.height = height;
		this.data = new byte[3*width*height];
	}
	
	public void set (int x, int y, int val) {
		int pos = (x*width+y)*3; // determine position in data array
		int r = (val & 0xFF0000) >> 16; //decode the hexadecimal color code (https://stackoverflow.com/questions/4129666/how-to-convert-hex-to-rgb-using-java)
		int g = (val & 0xFF00) >> 8;
		int b = (val & 0xFF);
		data[pos] = (byte)r;
		data[pos + 1] = (byte)g;
		data[pos + 2] = (byte)b;
	}
	
	public void write (String filename) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
		
		// write header
		bw.write("P3" + "\n");
		bw.write(width + " " + height + "\n");
		bw.write(255 + "\n");
		
		// write data
		for (int i = 0; i < data.length; i++) {
			bw.write(Byte.toString(data[i]));
			if ((i + 1) % (width * 3) == 0) { //new line
				bw.write("\n");
			}
			else if ((i + 1) % 3 == 0) { //new cell
				bw.write("\t");
			}
			else {
				bw.write(" ");
			}
		}
		bw.close();
	}
	
	public static void main(String[] args) throws IOException {
		Image i = new Image(4,4);
		i.set(0, 0, 0x123456);
		i.set(1, 1, 0x123456);
		i.set(2, 2, 0x123456);
		i.set(3, 3, 0x123456);
		i.set(0, 3, 0x120000);
		i.set(3, 0, 0x120000);
		i.write("test.ppm");
	}
}