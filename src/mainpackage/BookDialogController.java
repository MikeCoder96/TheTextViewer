package mainpackage;

import java.io.File;

import com.sun.javafx.scene.control.skin.LabeledText;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import utilspackage.Book;
import utilspackage.FilterableTreeItem;
import utilspackage.Utils;
import utilspackage.XmlHandler;

public class BookDialogController {

    @FXML
    private ListView<Pair<Book, FilterableTreeItem<Book>>> toaddbooks;

    @FXML
    void initialize() {
    	toaddbooks.setCellFactory(param -> new ListCell<Pair<Book, FilterableTreeItem<Book>>>() {
    		@Override
    		protected void updateItem(Pair<Book, FilterableTreeItem<Book>> item, boolean empty) {
    			super.updateItem(item, empty);
    			if(empty || item == null) {
    				setText(null);
    			} else {
    				setText(item.getKey().getTitle());
    			}
    		}
    	});
    	toaddbooks.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 &&
						event.getTarget() instanceof LabeledText){
					FileChooser fc = new FileChooser();
					fc.setTitle("Select the file: "+ toaddbooks.getSelectionModel().getSelectedItem().getKey().getTitle());
					File sf = fc.showOpenDialog(SceneHandler.getInstance().getDialogStage());
					if(sf != null && sf.exists()) {
						Book tmp = toaddbooks.getSelectionModel().getSelectedItem().getKey();
						tmp.setPath(sf);
						Utils.addBooks(tmp, toaddbooks.getSelectionModel().getSelectedItem().getValue());
						toaddbooks.getItems().remove(toaddbooks.getSelectionModel().getSelectedIndex());
					}
				}
				//handle empty list close dialog
				if(toaddbooks.getItems().isEmpty()) {
			    	SceneHandler.getInstance().getDialogStage().close();
				}
			}
		});
    	toaddbooks.getItems().addAll(XmlHandler.nonfoundbooks);
    }
}