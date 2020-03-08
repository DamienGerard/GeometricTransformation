package GeometricTransformation;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;


public class ItemList extends JPanel{
	private static final long serialVersionUID = 1L;
	private ArrayList<Item>  items;
	private int shapeIndex = 0;
	private int lineIndex = 0;
	private mainBoard board;
	
	ItemList(mainBoard board){
		this.board = board;
		items = new ArrayList<Item>();
		setLayout(new FlowLayout());
	}
	
	public int length() {
		return items.size();
	}
	
	public int getShapeIndex() {
		return shapeIndex;
	}
	
	public void incShapeIndex() {
		shapeIndex++;
	}
	
	public void decShapeIndex() {
		shapeIndex--;
	}
	
	public int getLineIndex() {
		return lineIndex;
	}
	
	public void incLineIndex() {
		lineIndex++;
	}
	
	public void decLineIndex() {
		lineIndex--;
	}
	
	public void addItem(Item i) {
		items.add(i);
		removeAll();
		reset();
		board.revalidate();
		board.repaint();
	}

	public void delItem(Item i) {
		items.remove(i);
		removeAll();
		reset();
	}
	
	private void reset() {
		for(int i=items.size()-1; i>=0; i--) {
			add(items.get(i));
		}
		revalidate();
		repaint();
	}
	
	public void clear() {
		items = new ArrayList<Item>();
		removeAll();
		board.revalidate();
		board.repaint();
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}

	public void clipLine(double x1, double y1, double x2, double y2) {
		double x_min;
		double y_min;
		double x_max;
		double y_max;
		
		if(x1<x2) {
			x_min = x1;
			x_max = x2;
		}else {
			x_min = x2;
			x_max = x1;
		}

		if(y1<y2) {
			y_min = y1;
			y_max = y2;
		}else {
			y_min = y2;
			y_max = y1;
		}
		LineItem dummyLine;
		boolean accepted = true;
		for(int i=0; i<items.size(); i++) {
			if(items.get(i) instanceof LineItem) {
				dummyLine = (LineItem) items.get(i);
				accepted = dummyLine.cohenSutherlandClip(x_min, y_min, x_max, y_max);
				if(!accepted) {
					i--;
				}
			}
		}
	}

}
