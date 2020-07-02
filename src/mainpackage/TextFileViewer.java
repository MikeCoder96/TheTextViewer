package mainpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.ScrollEvent;

public class TextFileViewer extends TextArea{
	private int fontsize;
	
	public TextFileViewer(File in) throws IOException {
		super();
		setEditable(false);
		readFromFile(in);
		fontsize = 1;
		setStyle("-fx-font-size:" + fontsize +"em");
		
		/*
		 * Control + scroll to change
		 * the fontsize "zoom"
		 */
		this.addEventHandler(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
			
			private int lowerbound = 0;
			private int upperbound = 10;
			@Override
			public void handle(ScrollEvent event) {
				if(event.isControlDown()) {
					if(event.getDeltaY() > 0.0 && fontsize < upperbound) {
						fontsize++;
					} else  if (fontsize > lowerbound){
						fontsize--;
					}
					setStyle("-fx-font-size:" + fontsize +"em");
				}
			}
		});
	}
	
	public void readFromFile(File in) throws IOException {
		clear();
		BufferedReader br = new BufferedReader(new FileReader(in));
		while(br.ready()) {
			appendText(br.readLine());
			appendText(System.lineSeparator());
		}
		br.close();
	}
}
