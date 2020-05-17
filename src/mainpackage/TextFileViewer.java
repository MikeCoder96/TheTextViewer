package mainpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.control.TextArea;

public class TextFileViewer extends TextArea{
	private File textfile;
	public TextFileViewer(File in) throws IOException {
		super();
		textfile = in;
		BufferedReader br = new BufferedReader(new FileReader(textfile));
		while(br.ready()) {
			this.appendText(br.readLine());
			this.appendText(System.lineSeparator());
		}
		br.close();
	}
}
