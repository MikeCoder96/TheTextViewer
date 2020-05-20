package utilspackage;

import java.util.Objects;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;

public class CellFactoryAdvanced implements Callback<TreeView<String>, TreeCell<String>> {
	private enum DropType {INTO, REORDER};
    private DropType dType;
    private static final DataFormat JAVA_FORMAT = new DataFormat("application/x-java-serialized-object");
    private static final String DROP_HINT_STYLE = "-fx-border-color: #eea82f; -fx-border-width: 0 0 2 0; -fx-padding: 3 3 1 3";
    private TreeCell<String> dropZone;
    private TreeItem<String> draggedItem;
    
    @Override
    public TreeCell<String> call(TreeView<String> treeView) {
        TreeCell<String> cell = new TreeCell<String>() {
            private TextField textField;
            
            private String getString() {
                return getItem() == null ? "" : getItem().toString();
            }
            
            private void createTextField() {
                textField = new TextField(getString());
                textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

                	//edit handler
                    @Override
                    public void handle(KeyEvent t) {
                    	//submit edit with ENTER cancel with ESCAPE
                        if (t.getCode() == KeyCode.ENTER) {
                            commitEdit(textField.getText());
                        } else if (t.getCode() == KeyCode.ESCAPE) {
                            cancelEdit();
                        }
                    }
                });
            }
            
        	@Override
            public void startEdit() {
                super.startEdit();

                if (textField == null) {
                    createTextField();
                }
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setText((String) getItem());
                setGraphic(getTreeItem().getGraphic());
            }
        	
            //update TreeCell on expand/move/delete/reorder
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                	setText(null);
                	setGraphic(null);
                	setContextMenu(null);
                	return;
                }
                setContextMenu(new ContextMenuTextCell(this));
                setText(item);
            }
        };
        cell.setOnDragDetected((MouseEvent event) -> dragDetected(event, cell, treeView));
        cell.setOnDragOver((DragEvent event) -> dragOver(event, cell, treeView));
        cell.setOnDragDropped((DragEvent event) -> drop(event, cell, treeView));
        cell.setOnDragDone((DragEvent event) -> clearDropLocation());
        
        return cell;
    }
    
    private void dragDetected(MouseEvent event, TreeCell<String> treeCell, TreeView<String> treeView) {
        draggedItem = treeCell.getTreeItem();

        // root can't be dragged
        if (draggedItem == null || draggedItem.getParent() == null) return;
        Dragboard db = treeCell.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        content.put(JAVA_FORMAT, draggedItem.getValue());
        db.setContent(content);
        db.setDragView(treeCell.snapshot(null, null));
        event.consume();
    }

    private void dragOver(DragEvent event, TreeCell<String> treeCell, TreeView<String> treeView) {
        if (!event.getDragboard().hasContent(JAVA_FORMAT)) return;
        TreeItem<String> thisItem = treeCell.getTreeItem();

        // can't drop on itself
        if (draggedItem == null || thisItem == null || thisItem == draggedItem) return;
        // ignore if this is the root
        if (draggedItem.getParent() == null) {
            clearDropLocation();
            return;
        }

        event.acceptTransferModes(TransferMode.MOVE);
        if (!Objects.equals(dropZone, treeCell)) {
            clearDropLocation();
            this.dropZone = treeCell;
            dropZone.setStyle(DROP_HINT_STYLE);
        }
        
        Point2D sceneloc = treeCell.localToScene(0.0, 0.0);
        double height = treeCell.getHeight();
        double y = event.getSceneY() - (sceneloc.getY());
        
        //handle drag into position
        //TODO find a better zone for drag Into
        if (y > (height * .75d)) {
        	dType = DropType.REORDER;
        }
        else {
        	dType = DropType.INTO;
        }
    }

    private void drop(DragEvent event, TreeCell<String> treeCell, TreeView<String> treeView) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (!db.hasContent(JAVA_FORMAT)) return;

        TreeItem<String> thisItem = treeCell.getTreeItem();
        TreeItem<String> droppedItemParent = draggedItem.getParent();
        //handle dropping node inside of itself
        if (Objects.equals(draggedItem, thisItem.getParent()))
        	return;
        // remove from previous location
        droppedItemParent.getChildren().remove(draggedItem);
        
        //handle drop inside an empty category
        if (dType == DropType.INTO && thisItem instanceof TreeCategory) {
        	thisItem.getChildren().add(draggedItem);
        }
        else {
	        // dropping on parent node makes it the first child
	        if (Objects.equals(droppedItemParent, thisItem)) {
	            thisItem.getChildren().add(0, draggedItem);
	            treeView.getSelectionModel().select(draggedItem);
	        }
	        else {
	            // add to new location
	            int indexInParent = thisItem.getParent().getChildren().indexOf(thisItem);
	            thisItem.getParent().getChildren().add(indexInParent + 1, draggedItem);
	        }
        }
        treeView.getSelectionModel().select(draggedItem);
        event.setDropCompleted(success);
    }

    //remove previously applied styles
    private void clearDropLocation() {
        if (dropZone != null) dropZone.setStyle("");
    }
}