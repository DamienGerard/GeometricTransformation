package GeometricTransformation;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

public class Graph extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final int initRefX;
	private final int initRefY;
	private int originX;
	private int originY;
	private int scale = 50;
	private boolean recordPoint = false;
	private ArrayList<Double> tempPointX;
	private ArrayList<Double> tempPointY;
	private LineItem lineToRecord;
	private boolean recordingLine = false;
	private ItemList items;
	private boolean clippingStateActivated = false;
	private Rectangle clipper;
	
	Graph(int panelWidth, int panelHeight, ItemList list){
		initRefX = panelWidth/2;
		initRefY = panelHeight/2;
		originX = initRefX;
		originY = initRefY;
		tempPointX = new ArrayList<Double>();
		tempPointY = new ArrayList<Double>();
		this.items = list;
		this.addMouseListener(mHandler);
		this.addMouseMotionListener(mHandler);
		this.addMouseWheelListener(mHandler);
	}
	
	public Dimension getPreferredSize() {
	    return new Dimension(2*initRefX, 2*initRefY);
	}
	
	
	protected void paintComponent(Graphics g) {
		drawGraph(g);
		
		if(tempPointX.size()>0) {
			drawPoint(g);
		}
		
		ShapeItem dummyShape;
		LineItem dummyLine;
		if(items.length()>0) {
			for(int i=0; i<items.length(); i++) {
				if(items.getItems().get(i) instanceof ShapeItem) {
					dummyShape = (ShapeItem) items.getItems().get(i);
					if(dummyShape.getColor()!=null) {
						scanLineFill(g,  dummyShape.getPolygon(g, originX, originY, scale), dummyShape.getColor());
					}
					drawShape(g, dummyShape);
				}else if(items.getItems().get(i) instanceof LineItem) {
					dummyLine = (LineItem) items.getItems().get(i);
					drawLine(g, dummyLine);
				}
			}
		}
		
		if(clippingStateActivated) {
			drawClipper(g, clipper);
		}
		
        //g2.dispose();
	}
	
	private void drawClipper(Graphics g, Rectangle clipper) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(1f,0f,0f,.5f));
		g2.fill(clipper);
		g2.setColor(new Color(0f,0f,0f,.25f));
		g2.draw(clipper);
	}

	private void drawLine(Graphics g, LineItem dummyLine) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2.5f));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int x1, y1, x2, y2;
		x1 = (int)((dummyLine.x1*scale)+0.5) +originX;
		y1 =  -(int)((dummyLine.y1*scale)+0.5) +originY;
		x2 = (int)((dummyLine.x2*scale)+0.5) +originX;
		y2 =  -(int)((dummyLine.y2*scale)+0.5) +originY;
		g2.drawLine(x1, y1, x2, y2);
	}

	private void scanLineFill(Graphics g, Polygon polygon, Color color) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.setStroke(new BasicStroke(1f));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Line2D.Float[] edges = new Line2D.Float[polygon.npoints];
		float[] slope = new float[edges.length];
		float[] currentX = new float[edges.length];
		
		for(int i=0; i<edges.length; i++) {
			if(polygon.ypoints[i%edges.length]<polygon.ypoints[(i+1)%edges.length]) {
				edges[i] = new Line2D.Float((float)polygon.xpoints[i%edges.length], (float)polygon.ypoints[i%edges.length], (float)polygon.xpoints[(i+1)%edges.length], (float)polygon.ypoints[(i+1)%edges.length]);
			}else {
				edges[i] = new Line2D.Float((float)polygon.xpoints[(i+1)%edges.length], (float)polygon.ypoints[(i+1)%edges.length], (float)polygon.xpoints[i%edges.length], (float)polygon.ypoints[i%edges.length]);
			}
		}
		
		int minYIndex;
		Line2D.Float tmp;
		for(int i=0; i<edges.length; i++) {
			minYIndex = i;
			for(int j=i+1; j<edges.length-1-i; j++) {
				if(edges[j].y1 < edges[minYIndex].y1) {
					minYIndex = j;
				}
			}
			
			if(minYIndex!=i) {
				tmp = edges[minYIndex];
				edges[minYIndex] = edges[i];
				edges[i] = tmp;
			}
			
			slope[i] = (edges[i].y2-edges[i].y1)/(edges[i].x2-edges[i].x1);
			currentX[i] = edges[i].x1;
		}
		
        int scanlineEnd = 0;
        for (int i = 0; i < edges.length; i++)
        {
            if (scanlineEnd < edges[i].y2) {
                scanlineEnd = (int)(edges[i].y2+0.5);
            }
        }

        ArrayList<Integer> xPointIntercept = new ArrayList<Integer>();
        
        for(int scanline=(int)(edges[0].y1+0.5); scanline < scanlineEnd; scanline++) {
        	xPointIntercept.clear();
        	for(int i=0; i<edges.length; i++) {
        		if(scanline > edges[i].y1 && scanline <= edges[i].y2) {
        			xPointIntercept.add((int) (currentX[i]+0.5));
        			currentX[i]+= 1/slope[i];
        		}
        	}
        	Collections.sort(xPointIntercept);
        	for(int i=0; i<xPointIntercept.size(); i+=2) {
        		g2.drawLine(xPointIntercept.get(i), scanline, xPointIntercept.get(i+1), scanline);
        	}
        }
	}

	private void drawShape(Graphics g, ShapeItem shape) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2.5f));
		g2.setColor(Color.black);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawPolygon(shape.getPolygon(g2, originX, originY, scale));
	}

	public void drawGraph(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.clearRect(0, 0, getWidth(), getHeight());
		g2.setStroke(new BasicStroke(2.5f));
		g2.setColor(Color.RED);
		g2.drawLine(0, originY, 10*initRefX, originY);
		g2.drawLine(originX, 0, originX, 10*initRefY);
		
		
		g2.setColor(Color.BLACK);

		for(int i=0; i<10*initRefX; i++){
			if(((i-originX)%scale)==0) {
				g2.setStroke(new BasicStroke(1f));
				g2.drawString(Integer.toString((i-originX)/scale), i-20, originY+13);
				g2.drawLine(i, 0, i, 10*initRefY);
			}

			if(((i-originY)%scale)==0) {
				g2.setStroke(new BasicStroke(1f));
				g2.drawString(Integer.toString((originY-i)/scale), originX-20, i+13);
				g2.drawLine(0, i, 10*initRefX, i);
			}

		}
	}
	
	public void drawPoint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		for(int i=0; i<tempPointX.size(); i++) {
			g2.drawOval((int)(tempPointX.get(i)*scale + originX + 0.5), (int)(-tempPointY.get(i)*scale + originY + 0.5), 4, 4);
		}
	}
	
	
	MouseHandler mHandler = new MouseHandler();
	class MouseHandler implements MouseMotionListener, MouseListener, MouseWheelListener{
		private int initPosX = 0;
		private int initPosY = 0;
		private int finalPosX = 0;
		private int finalPosY = 0;
		private int clipAnchorX = 0;
		private int clipAnchorY = 0;
		private int clipX1 = 0;
		private int clipY1 = 0;
		private int clipX2 = 0;
		private int clipY2 = 0;
		@Override
		public void mouseDragged(MouseEvent e) {
			if(clippingStateActivated) {
				clipX2 = e.getX();
				clipY2 = e.getY();
				
				if(clipX1 > clipX2) {
					clipAnchorX = clipX2;
				}else {
					clipAnchorX = clipX1;
				}

				if(clipY1 > clipY2) {
					clipAnchorY = clipY2;
				}else {
					clipAnchorY = clipY1;
				}
				
				clipper = new Rectangle(clipAnchorX, clipAnchorY, Math.abs(clipX2-clipX1), Math.abs(clipY2-clipY1));
				repaint();
			}else {
				finalPosX = e.getX();
				finalPosY = e.getY();
				
				originX += (finalPosX - initPosX);
				originY += (finalPosY - initPosY);
				repaint();
	
				initPosX = e.getX();
				initPosY = e.getY();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(recordPoint) {
				tempPointX.add(((e.getX()-originX)*1.0)/scale);
				tempPointY.add(((-e.getY()+originY)*1.0)/scale);
				if(recordingLine && tempPointX.size()==2) {
					lineToRecord.setLine(tempPointX.get(0), tempPointY.get(0), tempPointX.get(1), tempPointY.get(1));
					recordingLine = false;
					stopRecordPoint();
				}
				repaint();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(clippingStateActivated) {
				clipX1 = e.getX();
				clipY1 = e.getY();
				clipper = new Rectangle(clipX1, clipY1, 0, 0);
				repaint();
			}else {
				initPosX = e.getX();
				initPosY = e.getY();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(clippingStateActivated) {
				clipX2 = e.getX();
				clipY2 = e.getY();
				
				if(clipX1 > clipX2) {
					clipAnchorX = clipX2;
				}else {
					clipAnchorX = clipX1;
				}

				if(clipY1 > clipY2) {
					clipAnchorY = clipY2;
				}else {
					clipAnchorY = clipY1;
				}
				
				clipper = new Rectangle(clipAnchorX, clipAnchorY, Math.abs(clipX2-clipX1), Math.abs(clipY2-clipY1));
				repaint();
				clippingStateActivated = false;
				items.clipLine(((clipAnchorX-originX)*1.0)/scale,((-clipAnchorY+originY)*1.0)/scale,(((clipAnchorX+Math.abs(clipX2-clipX1))-originX)*1.0)/scale,((-(clipAnchorY+Math.abs(clipY2-clipY1))+originY)*1.0)/scale);
			}else {
				finalPosX = e.getX();
				finalPosY = e.getY();
				
				originX += (finalPosX - initPosX);
				originY += (finalPosY - initPosY);
				repaint();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			int offsetScale=0;
			if(e.getUnitsToScroll()<0) {
				offsetScale = 1;
			}else if(e.getUnitsToScroll()>0) {
				offsetScale = -1;
			}
			
			if((scale>20 && e.getUnitsToScroll()>0) || (scale<1000000 && e.getUnitsToScroll()<0)) {
				double originXRef = ((initRefX-originX)*1.0)/scale;
				double originYRef = ((originY-initRefY)*1.0)/scale;
				scale += offsetScale;
				originX = -(int)(((originXRef)*scale)+0.5)+initRefX;
				originY = (int)(((originYRef)*scale)+0.5)+initRefY;
				repaint();
			}
		}
		
	}
	
	public void startRecordPoint() {
		recordPoint = true;
		tempPointX = new ArrayList<Double>();
		tempPointY = new ArrayList<Double>();
	}
	
	public void stopRecordPoint() {
		recordPoint = false;
		tempPointX = new ArrayList<Double>();
		tempPointY = new ArrayList<Double>();
		repaint();
	}
	
	public boolean isRecordingPoint() {
		return recordPoint;
	}
	
	public ArrayList<Double> getTempPolyXpoints() {
		return tempPointX;
	}
	
	public ArrayList<Double> getTempPolyYpoints() {
		return tempPointY;
	}
	
	public void setLineToRecord(LineItem l) {
		this.lineToRecord = l;
	}
	
	public void setRecordingLine(boolean b) {
		this.recordingLine = b;
	}
	
	public void toggleClippingState() {
		clippingStateActivated = !clippingStateActivated;
		if(clippingStateActivated) {
			clipper = new Rectangle(0,0,0,0);
		}else {
			clipper = null;
		}
	}
}