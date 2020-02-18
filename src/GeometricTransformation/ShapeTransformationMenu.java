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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;


public class ShapeTransformationMenu extends JPanel{
	private ArrayList<ShapeTransformation> transformations;
	private JButton more;
	private JButton play;
	private JButton pause;
	private JSlider seekBar;
	private JPanel transformationsListPanel;
	private JScrollPane transformationsScrollPane;
	private Image play_img;
	private Image pause_img;
	private boolean paused = true;
	
	public ShapeTransformationMenu() {
		setPreferredSize(new Dimension(275, 140));
		setLayout(new BorderLayout());
		transformations = new ArrayList<ShapeTransformation>();
		add(BorderLayout.NORTH, new JLabel("Transformations"));
		
		transformationsListPanel = new JPanel() {
			public Dimension getPreferredSize() {
			    return new Dimension(250, ShapeTransformationMenu.this.length()*80);
			}
		};
		transformationsListPanel.setLayout(new FlowLayout());
		transformationsScrollPane = new JScrollPane(transformationsListPanel);
		//transformationsScrollPane.setPreferredSize(new Dimension(100, 80));
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
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("Clicked play\n");
				if(paused) {
					play.setIcon(new ImageIcon(pause_img));
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

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new ShapeTransformationMenu());
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
