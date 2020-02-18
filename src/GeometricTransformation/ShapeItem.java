package GeometricTransformation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ShapeItem extends JInternalFrame{
	private JButton done;
	private JButton cancel;
	private Polygon poly;
	private Graph graph;
	private JPanel validationPanel;
	private boolean confirmed=false;
	private ShapeList shapeList;
	
	
	ShapeItem(Graph graph, ShapeList shapeL, int index){
		super("Polygon"+String.valueOf(index), false, true, false, false);
		//setBorder(new LineBorder(Color.BLACK, 2, true));
	    Image icon;
		try {
			icon = ImageIO.read(new File("Icons/polygon-icon.png"));
			setFrameIcon(new ImageIcon(icon));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		show();
		this.graph = graph;
		this.shapeList = shapeL;
		setPreferredSize(new Dimension(350, 215));
		
		
		validationPanel = new JPanel();
		validationPanel.setSize(350, 140);
		validationPanel.setLayout(new FlowLayout());
		
		done = new JButton("Done");
		done.setSize(50, 125);
		done.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmed = true;
				JPanel settingsPanel = new JPanel();
				//setPolygon(graph.getTempPolyXpoints(), graph.getTempPolyYpoints());

				JPanel shapeColorPanel = new JPanel();
				shapeColorPanel.setLayout(new FlowLayout());
				ShapeColorButton red = new ShapeColorButton(new Color(255, 0, 0));
				shapeColorPanel.add(red);
				ShapeColorButton blue = new ShapeColorButton(new Color(0, 0, 255));
				shapeColorPanel.add(blue);
				ShapeColorButton green = new ShapeColorButton(new Color(0, 255, 0));
				shapeColorPanel.add(green);
				ShapeColorButton black = new ShapeColorButton(new Color(0, 0, 0));
				shapeColorPanel.add(black);
				JButton moreColor = new JButton();
				try {
				    Image img = ImageIO.read(new File("Icons/plusIcon.png"));
				    moreColor.setIcon(new ImageIcon(img));
				} catch (Exception ex) {
					System.out.println(ex);
				}
				moreColor.setPreferredSize(new Dimension(30, 30));
				moreColor.addMouseListener(new customBtnListener());
				moreColor.setContentAreaFilled(false);
				moreColor.setBorderPainted(false);
				shapeColorPanel.add(moreColor);
				
				JButton fillShape = new JButton("Fill");
				shapeColorPanel.add(fillShape);
				
				settingsPanel.add(shapeColorPanel);
				
				ShapeTransformationMenu shapeTransformationPanel = new ShapeTransformationMenu();
				settingsPanel.add(shapeTransformationPanel);
				
				add(settingsPanel);
				
				remove(validationPanel);
				revalidate();
			}
		});
		validationPanel.add(done);
		
		add(validationPanel);
		shapeList.addShapeItem(this);
	}
	
	@Override
	public void doDefaultCloseAction() {
		shapeList.delShapeItem(ShapeItem.this);
		if(!confirmed) {
			shapeList.decIndex();
		}
	}
	
	private void setPolygon(int[] xpoints, int[] ypoints) {
		poly = new Polygon(xpoints, ypoints, xpoints.length);
	}
	
	private class customBtnListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		@Override
		public void mouseExited(MouseEvent e) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

}
