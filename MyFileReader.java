import java.io.*;
import java.util.ArrayList;

public class MyFileReader {
	// field
	private BufferedReader br = null;

	public String read(String path, int lines) throws MyFileReaderException {

		// close file if it is open
		if (br != null) {
			close();
		}

		// list stores all lines read from file
		ArrayList<String> list = new ArrayList<String>();

		// stores specified lines from file
		String readLines = "";

		try {
			br = new BufferedReader(new FileReader(path));
			String s = br.readLine();

			// adds lines from file to list
			while (s != null) {
				list.add(s);
				s = br.readLine();
			}

			// by default, all lines displayed
			int start = 0;
			int end = list.size();

			// displays the first n lines of the text
			if (lines > 0) {
				end = lines;
			}
			// displays the last n lines of the text
			else if (lines < 0) {
				start = list.size() + lines;
			}

			// checks for errors
			if (start < 0 || start > list.size()) {
				throw new MyFileReaderException(list.size());
			}

			if (end < 0 || end > list.size()) {
				throw new MyFileReaderException(list.size());
			}

			// loop adds specified lines from ArrayList to String
			for (int i = start; i < end; i++) {
				readLines += list.get(i) + "\n";
			}
		} catch (IOException e) {
		}

		// returns specified lines from file
		return readLines;
	}

	public void close() {
		try {
			br.close();
			br = null;
		} catch (IOException e) {
			// error
		}
	}

	public static void main(String[] args) {
		// reading a file and not a URL
		// can be easily modified to read from a URL
		String path = "test file.txt";
		MyFileReader mfr = new MyFileReader();
		try {
			System.out.println(mfr.read(path, 0));
			System.out.println(mfr.read(path, 11));
			System.out.println(mfr.read(path, 5));
			System.out.println(mfr.read(path, -5));
			System.out.println(mfr.read(path, -11));

			// will throw error
			// System.out.println(mfr.read(path, 100));
			System.out.println(mfr.read(path, -100));

		} catch (MyFileReaderException e) {
			System.err.println(e);
		}

		mfr.close();
	}
}

// exception thrown if number of lines parameter is invalid
class MyFileReaderException extends Exception {
	private static final long serialVersionUID = 1L;

	public MyFileReaderException(int n) {
		super("Number of lines parameter should be between -" + n + " and " + n + " (inclusive).");
	}
}