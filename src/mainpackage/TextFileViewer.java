package mainpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class TextFileViewer extends ScrollPane{
	private File textfile;
	private TextArea text;
	
	public TextFileViewer(File in) throws IOException {
		super();
		textfile = in;
		text = new TextArea();
		BufferedReader br = new BufferedReader(new FileReader(textfile));
		while(br.ready()) {
			text.appendText(br.readLine());
			text.appendText("\n");
		}
		br.close();
		this.setContent(text);
	}
}
