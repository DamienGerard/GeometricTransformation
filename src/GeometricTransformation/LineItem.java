package GeometricTransformation;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class LineItem extends Item{
	private static final long serialVersionUID = 1L;
	public double x1, y1, x2, y2;
	final private int INSIDE = 0;
	final private int LEFT = 1;
	final private int RIGHT = 2;
	final private int BOTTOM = 4;
	final private int TOP = 8;

	public LineItem(Graph myGraph, ItemList itemListPanel, int lineIndex) {
		super("Line"+String.valueOf(lineIndex), false, true, false, false);
		this.graph = myGraph;
		itemList = itemListPanel;
		try {
			icon = ImageIO.read(new File("Icons/line-icon.png"));
			setFrameIcon(new ImageIcon(icon));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		show();
		setPreferredSize(new Dimension(350, 30));
		itemList.addItem((Item)LineItem.this);
	}
	
	public void setLine(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		confirmed = true;
	}
	
	@Override
	public void doDefaultCloseAction() {
		itemList.delItem((Item)LineItem.this);
		if(!confirmed) {
			graph.stopRecordPoint();
			itemList.decLineIndex();
		}
		graph.repaint();
	}

	public boolean cohenSutherlandClip(double x_min, double y_min, double x_max, double y_max) {
	    int code1 = computeCode(x_min, y_min, x_max, y_max, x1, y1); 
	    int code2 = computeCode(x_min, y_min, x_max, y_max, x2, y2);

	    boolean accept = false; 
	    
	    while (true) {
	        if ((code1 | code2) == 0) {
	            accept = true; 
	            break; 
	        }else if ((code1 & code2)>0) {
	            break; 
	        }else { 
	            int code_out; 
	            double x = 0, y = 0; 
	   
	            if (code1 != 0) {
	                code_out = code1; 
	            }else {
	                code_out = code2; 
	            }
	            
	            if ((code_out & TOP)!=0) { 
	                x = x1 + (x2 - x1) * (y_max - y1) / (y2 - y1); 
	                y = y_max; 
	            }else if ((code_out & BOTTOM)!=0) { 
	                x = x1 + (x2 - x1) * (y_min - y1) / (y2 - y1); 
	                y = y_min; 
	            }else if ((code_out & RIGHT)!=0) {  
	                y = y1 + (y2 - y1) * (x_max - x1) / (x2 - x1); 
	                x = x_max; 
	            }else if ((code_out & LEFT)!=0) { 
	                y = y1 + (y2 - y1) * (x_min - x1) / (x2 - x1); 
	                x = x_min; 
	            } 
 
	            if (code_out == code1) { 
	                x1 = x; 
	                y1 = y; 
	                code1 = computeCode(x_min, y_min, x_max, y_max, x1, y1); 
	            }else { 
	                x2 = x; 
	                y2 = y; 
	                code2 = computeCode(x_min, y_min, x_max, y_max, x2, y2); 
	            } 
	        } 
	    }
	    
	    if(!accept) {
	    	doDefaultCloseAction();
	    }
	    graph.repaint();
	    
	    return accept;
	}

	private int computeCode(double x_min, double y_min, double x_max, double y_max, double x, double y) { 
	    int code = INSIDE; 
	    
	    if (x < x_min) {
	        code |= LEFT;
	    }else if (x > x_max) {
	        code |= RIGHT;
	    }
	    
	    if (y < y_min) {
	        code |= BOTTOM;
	    }else if (y > y_max) {
	        code |= TOP;
	    }
	    
	    return code;
	}

}
