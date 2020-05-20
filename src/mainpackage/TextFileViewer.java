package mainpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.ScrollEvent;

public class TextFileViewer extends TextArea{
	private File textfile;
	private int fontsize;
	private TextFileViewer pointToSelf;
	
	public TextFileViewer(File in) throws IOException {
		super();
		this.setEditable(false);
		textfile = in;
		BufferedReader br = new BufferedReader(new FileReader(textfile));
		while(br.ready()) {
			this.appendText(br.readLine());
			this.appendText(System.lineSeparator());
		}
		br.close();
		fontsize = 1;
		//this.setFont(new Font(50));
		this.setStyle("-fx-font-size:" + fontsize +"em");
		pointToSelf = this;
		this.addEventHandler(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
			
			private int lowerbound = 0;
			private int upperbound = 10;
			@Override
			public void handle(ScrollEvent event) {
				if(event.getDeltaY() > 0.0 && fontsize < upperbound) {
					fontsize++;
				} else  if (fontsize > lowerbound){
					fontsize--;
				}
				pointToSelf.setStyle("-fx-font-size:" + fontsize +"em");
			}
		});
	}
}
