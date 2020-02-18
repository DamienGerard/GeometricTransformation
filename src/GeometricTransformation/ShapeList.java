package GeometricTransformation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


public class ShapeList extends JPanel{
	private ArrayList<ShapeItem>  shapeItems;
	private int index = 0;
	private mainBoard board;
	
	ShapeList(mainBoard board){
		this.board = board;
		shapeItems = new ArrayList<ShapeItem>();
		setLayout(new FlowLayout());
	}
	
	public int length() {
		return shapeItems.size();
	}
	
	public int getIndex() {
		return index;
	}
	
	public void incIndex() {
		index++;
	}
	
	public void decIndex() {
		index--;
	}
	
	public void addShapeItem(ShapeItem s) {
		shapeItems.add(s);
		removeAll();
		reset();
		board.revalidate();
		board.repaint();
	}

	public void delShapeItem(ShapeItem s) {
		shapeItems.remove(s);
		removeAll();
		reset();
	}
	
	private void reset() {
		for(int i=shapeItems.size()-1; i>=0; i--) {
			add(shapeItems.get(i));
		}
		revalidate();
		repaint();
	}
	
	
	

}
