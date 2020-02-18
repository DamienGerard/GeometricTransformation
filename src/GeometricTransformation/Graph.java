package GeometricTransformation;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

public class Graph extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private final int initRefX;
	private final int initRefY;
	private int originX;
	private int originY;
	private int scale = 50;
	
	Graph(int panelWidth, int panelHeight){
		initRefX = panelWidth/2;
		initRefY = panelHeight/2;
		originX = initRefX;
		originY = initRefY;
		this.addMouseListener(mHandler);
		this.addMouseMotionListener(mHandler);
		this.addMouseWheelListener(mHandler);
	}
	
	public Dimension getPreferredSize() {
	    return new Dimension(2*initRefX, 2*initRefY);
	}
	
	
	protected void paintComponent(Graphics g) {
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
        g2.dispose();
	}
	
	
	MouseHandler mHandler = new MouseHandler();
	class MouseHandler implements MouseMotionListener, MouseListener, MouseWheelListener{
		private int initPosX = 0;
		private int initPosY = 0;
		private int finalPosX = 0;
		private int finalPosY = 0;
		@Override
		public void mouseDragged(MouseEvent e) {
			finalPosX = e.getX();
			finalPosY = e.getY();
			
			originX += (finalPosX - initPosX);
			originY += (finalPosY - initPosY);
			repaint();

			initPosX = e.getX();
			initPosY = e.getY();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			initPosX = e.getX();
			initPosY = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			finalPosX = e.getX();
			finalPosY = e.getY();
			
			originX += (finalPosX - initPosX);
			originY += (finalPosY - initPosY);
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			int offsetScale=0;
			if(e.getUnitsToScroll()<0) {
				offsetScale = -1;
			}else if(e.getUnitsToScroll()>0) {
				offsetScale = 1;
			}
			
			if((scale>20 && e.getUnitsToScroll()>0) || (scale<1000000 && e.getUnitsToScroll()<0)) {
				scale -= offsetScale;
				repaint();
			}
		}
		
	}
	public int[] getTempPolyXpoints() {
		// TODO Auto-generated method stub
		return null;
	}

	public int[] getTempPolyYpoints() {
		// TODO Auto-generated method stub
		return null;
	}
}