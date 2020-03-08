package GeometricTransformation;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ShapeTransformationMenu extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	private ShapeItem shape;
	private Graph graph;
	private ArrayList<ShapeTransformation> transformations;
	private JButton more;
	private JButton play;
	private JSlider seekBar;
	private JPanel transformationsListPanel;
	private JScrollPane transformationsScrollPane;
	private Image play_img;
	private Image pause_img;
	private boolean paused = true;
	private Thread threadTransform;
	private boolean execTransform = false;
	
	public ShapeTransformationMenu(ShapeItem s, Graph g) {
		shape = s;
		graph = g;
		setPreferredSize(new Dimension(275, 140));
		setLayout(new BorderLayout());
		transformations = new ArrayList<ShapeTransformation>();
		add(BorderLayout.NORTH, new JLabel("Transformations"));
		
		transformationsListPanel = new JPanel() {
			private static final long serialVersionUID = 1L;
			public Dimension getPreferredSize() {
			    return new Dimension(250, ShapeTransformationMenu.this.length()*80);
			}
		};
		transformationsListPanel.setLayout(new FlowLayout());
		transformationsScrollPane = new JScrollPane(transformationsListPanel);
		transformationsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		transformationsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		transformationsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(BorderLayout.CENTER, transformationsScrollPane);
		
		 JPanel controlBtnPane = new JPanel(new FlowLayout());
		more = new JButton();
		try {
		    Image img = ImageIO.read(new File("Icons/plusIcon.png"));
		    more.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		more.setPreferredSize(new Dimension(30, 30));
		more.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addTransformation(new ShapeTransformation(ShapeTransformationMenu.this));
			}
		});
		more.addMouseListener(new customBtnListener());
		more.setContentAreaFilled(false);
		more.setBorderPainted(false);
		controlBtnPane.add(more);
		
		play = new JButton();
		try {
		    play_img = ImageIO.read(new File("Icons/play-button-icon.jpg"));
		    pause_img = ImageIO.read(new File("Icons/pause-button-icon.jpg"));
		    play.setIcon(new ImageIcon(play_img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		play.addMouseListener(new customBtnListener());
		play.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				if(paused) {
					play.setIcon(new ImageIcon(pause_img));
					if(!(execTransform)) {
						int sumFrameCount = 0;
						for(int i=0; i<transformations.size(); i++) {
							transformations.get(i).setPoints((ArrayList<Double>) shape.getXPoints().clone(), (ArrayList<Double>) shape.getYPoints().clone());
							sumFrameCount = sumFrameCount+transformations.get(i).getFramecount();
						}
						seekBar.setMaximum(sumFrameCount);
						execTransform = true;
						threadTransform = new Thread(ShapeTransformationMenu.this, "transformThread");
						threadTransform.start();
					}
				}else {
				    play.setIcon(new ImageIcon(play_img));
				}
				paused = !(paused);
				controlBtnPane.revalidate();
				controlBtnPane.repaint();
			}
		});
		play.setPreferredSize(new Dimension(27, 27));
		controlBtnPane.add(play);
		
		seekBar = new JSlider();
		seekBar.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				exec(seekBar.getValue());
			}
			
		});
		seekBar.setValue(0);
		controlBtnPane.add(seekBar);
		
		add(BorderLayout.SOUTH, controlBtnPane);
	}
	
	public void addTransformation(ShapeTransformation t) {
		transformations.add(t);
		transformationsListPanel.add(t);
		transformationsListPanel.revalidate();
		transformationsListPanel.repaint();
	}
	
	public void delTransformation(ShapeTransformation t) {
		transformations.remove(t);
		transformationsListPanel.remove(t);
		transformationsListPanel.revalidate();
		transformationsListPanel.repaint();
	}
	
	public int length() {
		return transformations.size();
	}
	
	public void exec(int frameNum) {
		@SuppressWarnings("unchecked")
		ArrayList<Double> xPoints = (ArrayList<Double>) shape.getOriginalXPoints().clone();
		@SuppressWarnings("unchecked")
		ArrayList<Double> yPoints = (ArrayList<Double>) shape.getOriginalYPoints().clone();
		int sumFrameCount = 0;
		
		for(int i=0; i<transformations.size(); i++) {
			if(frameNum>sumFrameCount) {
				transformations.get(i).transform(xPoints, yPoints, frameNum-sumFrameCount);
			}else {
				break;
			}
			sumFrameCount = sumFrameCount+transformations.get(i).getFramecount();
		}
		
		shape.setPolygon(xPoints, yPoints);
		graph.repaint();
	}
	
	private class customBtnListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
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


	@Override
	public void run() {
		while( execTransform) {
			while(seekBar.getValue()<=seekBar.getMaximum() && !(paused)) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				seekBar.setValue(seekBar.getValue()+1);
				seekBar.revalidate();
				seekBar.repaint();
				if(seekBar.getValue()==seekBar.getMaximum()) {
					execTransform = false;
					shape.resetPolygon();
					graph.repaint();
					break;
				}
			}
			System.out.print("");
		}

	    play.setIcon(new ImageIcon(play_img));
	    paused = !(paused);
		seekBar.setValue(0);
		seekBar.revalidate();
		seekBar.repaint();
	}

}
