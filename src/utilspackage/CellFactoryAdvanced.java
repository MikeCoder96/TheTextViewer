package utilspackage;

import java.util.Objects;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;
import mainpackage.MainController;
import mainpackage.SceneHandler;

public class CellFactoryAdvanced implements Callback<TreeView<Book>, TreeCell<Book>> {
	private enum DropType {INTO, REORDER};
    private DropType dType;
    private static final DataFormat JAVA_FORMAT = new DataFormat("application/x-java-serialized-object");
    private static final String DROP_HINT_STYLE = "-fx-border-color: #eea82f; -fx-border-width: 0 0 2 0; -fx-padding: 3 3 1 3";
    private TreeCell<Book> dropZone;
    private FilterableTreeItem<Book> draggedItem;
    private Boolean top = false;
    
    @Override
    public TreeCell<Book> call(TreeView<Book> treeView) {
        TreeCell<Book> cell = new TreeCell<Book>() {
            private TextField textField;
            
            private String getString() {
                return getItem() == null ? "" : getItem().getTitle();
            }
            
            private void createTextField() {
                textField = new TextField();
                textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                	//edit handler
                    @Override
                    public void handle(KeyEvent t) {
                    	//submit edit with ENTER cancel with ESCAPE
                        if (t.getCode() == KeyCode.ENTER && !textField.getText().contentEquals("")) {
                        	Book tmp = new Book(textField.getText(),getItem().getPath());
                        	commitEdit(tmp);
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
                textField.setText(getString());
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setText(getItem().getTitle());
                setGraphic(getTreeItem().getGraphic());
            }
            
            @Override
            public void commitEdit(Book newvalue) {
            	super.commitEdit(newvalue);
            	setGraphic(getTreeItem().getGraphic());
            }
        	
            //update TreeCell on expand/move/delete/reorder
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                	setText(null);
                	setGraphic(null);
                	setContextMenu(null);
                	return;
                }
                setContextMenu(new ContextMenuTextCell(this));
                setText(item.getTitle());
                if(item.getPath() == null) {
                	ImageView imv = new ImageView("/formpackage/icon.png");
                	setGraphic(imv);
                }
                else
                	setGraphic(getTreeItem().getGraphic());
            }
        };
        cell.setOnDragDetected((MouseEvent event) -> dragDetected(event, cell, treeView));
        cell.setOnDragOver((DragEvent event) -> dragOver(event, cell, treeView));
        cell.setOnDragDropped((DragEvent event) -> drop(event, cell, treeView));
        cell.setOnDragDone((DragEvent event) -> clearDropLocation());
        cell.setOnMouseClicked(event -> {
        	if(!cell.isEmpty() && event.getButton().equals(MouseButton.PRIMARY) && cell.getTreeItem().getValue().getPath() != null) {
        		((MainController) SceneHandler.getInstance().getMainLoader().getController()).OpenText(cell.getTreeItem().getValue());
        	}
        });
        return cell;
    }
    
    private void dragDetected(MouseEvent event, TreeCell<Book> treeCell, TreeView<Book> treeView) {
        draggedItem = (FilterableTreeItem<Book>) treeCell.getTreeItem();

        // root can't be dragged
        if (draggedItem == null || draggedItem.getParent() == null) return;
        Dragboard db = treeCell.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        content.put(JAVA_FORMAT, draggedItem.getValue());
        db.setContent(content);
        db.setDragView(treeCell.snapshot(null, null));
        event.consume();
    }

    private void dragOver(DragEvent event, TreeCell<Book> treeCell, TreeView<Book> treeView) {
        if (!event.getDragboard().hasContent(JAVA_FORMAT)) return;
        TreeItem<Book> thisItem = treeCell.getTreeItem();

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
        if (y < (height * .33d)) {
        	dType = DropType.REORDER;
        	top = true;
        }
        else if (y > (height * .66d)){
    		dType = DropType.REORDER;
    		top = false;
    	}
        else {
        	dType = DropType.INTO;
        }
    }

    private void drop(DragEvent event, TreeCell<Book> treeCell, TreeView<Book> treeView) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (!db.hasContent(JAVA_FORMAT)) return;

        FilterableTreeItem<Book> thisItem = (FilterableTreeItem<Book>) treeCell.getTreeItem();
        FilterableTreeItem<Book> droppedItemParent = (FilterableTreeItem<Book>) draggedItem.getParent();
        //handle dropping node inside of itself
        if (Objects.equals(draggedItem, thisItem.getParent()))
        	return;
        // remove from previous location
        droppedItemParent.getInternalChildren().remove(draggedItem);
        
        //handle drop inside an empty category
        if (dType == DropType.INTO && thisItem.getValue().getPath() == null) {
        	thisItem.getInternalChildren().add(draggedItem);
        }
        else {
            int indexInParent = ((FilterableTreeItem<Book>) thisItem.getParent()).getInternalChildren().indexOf(thisItem);
            if (top)
            	indexInParent--;
            ((FilterableTreeItem<Book>) thisItem.getParent()).getInternalChildren().add(indexInParent + 1, draggedItem);
        }
        treeView.getSelectionModel().select(draggedItem);
        event.setDropCompleted(success);
    }

    //remove previously applied styles
    private void clearDropLocation() {
        if (dropZone != null) 
        	dropZone.setStyle("");
    }
}