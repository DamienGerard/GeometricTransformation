package GeometricTransformation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class ShapeItem extends Item{
	private static final long serialVersionUID = 1L;
	private String shapeName;
	private JButton done;
	private JPanel validationPanel;
	private ArrayList<Double> xPoints;
	private ArrayList<Double> yPoints;
	private ArrayList<Double> xOriginalPoints;
	private ArrayList<Double> yOriginalPoints;
	private Color preColor;
	private Color color;
	

	ShapeItem(Graph graph, ItemList shapeL, int index){
		super("Polygon"+String.valueOf(index), false, true, false, false);
		shapeName = "Polygon"+String.valueOf(index);
		xPoints = new ArrayList<Double>();
		yPoints = new ArrayList<Double>();
		try {
			icon = ImageIO.read(new File("Icons/polygon-icon.png"));
			setFrameIcon(new ImageIcon(icon));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		show();
		this.graph = graph;
		this.itemList = shapeL;
		setPreferredSize(new Dimension(350, 215));
		
		
		validationPanel = new JPanel();
		validationPanel.setSize(350, 140);
		validationPanel.setLayout(new FlowLayout());
		
		done = new JButton("Done");
		done.setSize(50, 125);
		done.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(graph.getTempPolyXpoints().size()>2) {
					createPolygon(graph.getTempPolyXpoints(), graph.getTempPolyYpoints());
					System.out.print("We In!!!\n");
					confirmed = true;
					JPanel settingsPanel = new JPanel();
					JPanel shapeColorPanel = new JPanel();
					shapeColorPanel.setLayout(new FlowLayout());
					ShapeColorButton red = new ShapeColorButton(new Color(255, 0, 0));
					ShapeColorButton blue = new ShapeColorButton(new Color(0, 0, 255));
					ShapeColorButton black = new ShapeColorButton(new Color(0, 0, 0));
					ShapeColorButton green = new ShapeColorButton(new Color(0, 255, 0));
					
					red.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							blue.setSelect(false);
							black.setSelect(false);
							green.setSelect(false);
							preColor = new Color(255, 0, 0);
						}
					});
					shapeColorPanel.add(red);
					
					blue.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							red.setSelect(false);
							black.setSelect(false);
							green.setSelect(false);
							preColor = new Color(0, 0, 255);
						}
					});
					shapeColorPanel.add(blue);
					
					green.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							red.setSelect(false);
							blue.setSelect(false);
							black.setSelect(false);
							preColor = new Color(0, 255, 0);
						}
					});
					shapeColorPanel.add(green);
					
					black.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							red.setSelect(false);
							blue.setSelect(false);
							green.setSelect(false);
							preColor = new Color(0, 0, 0);
						}
					});
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
					moreColor.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							red.setSelect(false);
							blue.setSelect(false);
							black.setSelect(false);
							green.setSelect(false);
							preColor = JColorChooser.showDialog(null, "Choose Polygon Border Color", color);
						}
					});
					shapeColorPanel.add(moreColor);
					
					JButton fillShape = new JButton("Fill");
					fillShape.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							color = preColor;
							graph.repaint();
						}
					});
					
					shapeColorPanel.add(fillShape);
					
					settingsPanel.add(shapeColorPanel);
					
					ShapeTransformationMenu shapeTransformationPanel = new ShapeTransformationMenu(ShapeItem.this, graph);
					settingsPanel.add(shapeTransformationPanel);
					
					add(settingsPanel);
					
					remove(validationPanel);
					revalidate();
				}else {
					doDefaultCloseAction();
				}
				graph.stopRecordPoint();
			}
		});
		validationPanel.add(done);
		
		add(validationPanel);
		itemList.addItem((Item)ShapeItem.this);
	}
	
	@Override
	public void doDefaultCloseAction() {
		itemList.delItem((Item)ShapeItem.this);
		if(!confirmed) {
			graph.stopRecordPoint();
			itemList.decShapeIndex();
		}
		graph.repaint();
	}
	
	public void createPolygon(ArrayList<Double> xPoints, ArrayList<Double> yPoints) {
		this.xOriginalPoints = xPoints;
		this.yOriginalPoints = yPoints;
		this.xPoints = xPoints;
		this.yPoints = yPoints;
	}
	
	public void resetPolygon() {
		this.xOriginalPoints = this.xPoints;
		this.yOriginalPoints = this.yPoints;
	}
	
	public void setPolygon(ArrayList<Double> xPoints, ArrayList<Double> yPoints) {
		this.xPoints = xPoints;
		this.yPoints = yPoints;
	}
	
	public ArrayList<Double> getXPoints() {
		return xPoints;
	}
	
	public ArrayList<Double> getYPoints() {
		return yPoints;
	}
	
	public ArrayList<Double> getOriginalXPoints() {
		return xOriginalPoints;
	}
	
	public ArrayList<Double> getOriginalYPoints() {
		return yOriginalPoints;
	}
	
	public String getShapeName() {
		return shapeName;
	}
	
	public Polygon getPolygon(Graphics g, int originX, int originY, int scale) {
		int numPoints = getXPoints().size();
		int[] xpoints = new int[numPoints];
		int[] ypoints = new int[numPoints];
		for(int i=0; i<numPoints; i++) {
			xpoints[i] = (int)((xPoints.get(i)*scale)+0.5) +originX;
			ypoints[i] = -(int)((yPoints.get(i)*scale)+0.5) +originY;
		}
		return new Polygon(xpoints, ypoints, numPoints);
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
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
