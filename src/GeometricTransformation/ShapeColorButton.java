package GeometricTransformation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class ShapeColorButton extends JButton implements MouseListener{
	private static final long serialVersionUID = 1L;
	private Color btnColor;
	private boolean selected = false;
	
	public ShapeColorButton(Color c) {
		btnColor = c;
		setPreferredSize(new Dimension(30, 30));
		setBorderPainted(false);
		setContentAreaFilled(false);
		addMouseListener(this);
	}
	
	protected void paintComponent(Graphics g) {
	      Graphics2D g2 = (Graphics2D) g.create();
	      g2.setColor(btnColor);
	      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	      if(!selected) {
		      g2.fillOval(10, 10, getWidth()-20, getHeight()-20);
		      g2.setPaint(Color.BLACK);
		      g2.setStroke(new BasicStroke(3));
		      g2.drawOval(5, 5, getWidth()-10, getHeight()-10);
	      }else {
		      g2.fillOval(2, 2, getWidth()-4, getHeight()-4);
	      }
		  g2.dispose();
	}
	
	public void setSelect(boolean b) {
		selected = b;
		repaint();
	}
	
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		setSelect(!selected);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
