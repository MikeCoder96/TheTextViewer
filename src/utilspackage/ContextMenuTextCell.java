package utilspackage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;

public class ContextMenuTextCell extends ContextMenu{
	private TreeCell<String> object;
	
	public ContextMenuTextCell(TreeCell<String> obj) {
		super();
		object = obj;
		MenuItem editName = new MenuItem("Edit Title");
		editName.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println(object.getItem());
			}
		});
		this.getItems().add(editName);
		
	}
}
