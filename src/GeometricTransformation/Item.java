package GeometricTransformation;

import java.awt.Image;

import javax.swing.JInternalFrame;

public class Item extends JInternalFrame{
	private static final long serialVersionUID = 1L;
	protected ItemList itemList;
	protected Graph graph;
	protected Image icon;
	protected boolean confirmed=false;
	
	
	Item(String title, boolean resize, boolean close, boolean max, boolean icon){
		super(title, resize, close, max, icon);
	}
	
}
