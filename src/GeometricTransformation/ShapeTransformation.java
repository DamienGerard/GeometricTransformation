package GeometricTransformation;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ShapeTransformation extends JPanel implements ItemListener {
	private JPanel cards;
	private JTextField entry1, entry2, entry3;
	private final static String TRANSLATION = "Translation";
	private final static String NORMAL_ROTATION = "Normal rotation";
	private final static String FIXED_POINT_ROTATION = "Rotation about fixed point";
	private final static String NORMAL_SCALING = "Normal scaling";
	private final static String FIXED_POINT_SCALING = "Scaling about a fixed point";
	private final static String SHEAR = "Shear";
	private final static String REFLECTION = "Reflection";
	private JButton reflWrtX;
	private JButton reflWrtY;
	private JPanel reflect;
	private boolean reflWrtYflag;
	private JButton close;
	private static ShapeTransformationMenu transformationList;
	
	ShapeTransformation(ShapeTransformationMenu tl){
		transformationList = tl;
		setPreferredSize(new Dimension(250, 75));
		setBorder(new LineBorder(Color.BLACK, 2, true));
		
        String comboBoxItems[] = {TRANSLATION, NORMAL_ROTATION, FIXED_POINT_ROTATION, NORMAL_SCALING, FIXED_POINT_SCALING, SHEAR, REFLECTION};
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        add(cb);
        
		close = new JButton();
		close.setBorderPainted(false);
		close.setContentAreaFilled(false);
		close.setPreferredSize(new Dimension(26,26));
		
		try {
			Image img = ImageIO.read(new File("Icons/close-icon.jpg"));
			close.setIcon(new ImageIcon(img));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				transformationList.delTransformation(ShapeTransformation.this);
			}
		});
		close.addMouseListener(new MouseListener() {
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
		});
        
		add(close);
		
        entry1 = new JTextField(3);
        entry2 = new JTextField(3);
        entry3 = new JTextField(3);
        
        JPanel trans = new JPanel();
        trans.add(new JLabel("Vector i: "));
        entry1 = new JTextField(3);
        trans.add(entry1);
        trans.add(new JLabel("Vector j: "));
        entry2 = new JTextField(3);
        trans.add(entry2);
        
        JPanel normRot = new JPanel();
        normRot.add(new JLabel("Angle: "));
        entry1 = new JTextField(3);
        normRot.add(entry1);
        
        JPanel fixPRot = new JPanel();
        fixPRot.add(new JLabel("Angle: "));
        entry1 = new JTextField(2);
        fixPRot.add(entry1);
        fixPRot.add(new JLabel("x: "));
        entry2 = new JTextField(2);
        fixPRot.add(entry2);
        fixPRot.add(new JLabel("y: "));
        entry3 = new JTextField(2);
        fixPRot.add(entry3);
        
        JPanel normScale = new JPanel();
        normScale.add(new JLabel("Scale: "));
        entry1 = new JTextField(3);
        normScale.add(entry1);
        
        JPanel fixPScale = new JPanel();
        fixPScale.add(new JLabel("Scale: "));
        entry1 = new JTextField(3);
        fixPScale.add(entry1);
        fixPScale.add(new JLabel("x: "));
        entry2 = new JTextField(3);
        fixPScale.add(entry2);
        fixPScale.add(new JLabel("y: "));
        entry3 = new JTextField(3);
        fixPScale.add(entry3);
        
        JPanel shear = new JPanel();
        shear.add(new JLabel("Shear: "));
        entry1 = new JTextField(3);
        shear.add(entry1);
        
        reflect = new JPanel();
        entry1 = new JTextField(3);
        entry2 = new JTextField(3);
        reflWrtX = new JButton("wrt x-axis");
        reflWrtX.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reflWrtYflag = false;
				reflect.removeAll();
				reflect.add(new JLabel("x = "));
				reflect.add(entry1);
				reflect.add(new JLabel("y + "));
				reflect.add(entry2);
				reflect.revalidate();
				reflect.repaint();
			}
        	
        });
        reflect.add(reflWrtX);
        reflWrtY = new JButton("wrt y-axis");
        reflWrtY.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reflWrtYflag = true;
				reflect.removeAll();
				reflect.add(new JLabel("y = "));
				reflect.add(entry1);
				reflect.add(new JLabel("x + "));
				reflect.add(entry2);
				reflect.revalidate();
				reflect.repaint();
			}
        });
        reflect.add(reflWrtY);
        
        cards = new JPanel(new CardLayout());
        cards.setPreferredSize(new Dimension(200, 35));
        cards.add(trans,TRANSLATION);
        cards.add(normRot,NORMAL_ROTATION);
        cards.add(fixPRot,FIXED_POINT_ROTATION);
        cards.add(normScale, NORMAL_SCALING);
        cards.add(fixPScale,FIXED_POINT_SCALING);
        cards.add(shear,SHEAR);
        cards.add(reflect,REFLECTION);

        add(cards);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new ShapeTransformation(transformationList));
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		System.out.print("Oy\n");
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)e.getItem());
        cards.revalidate();
        cards.repaint();
		reflect.removeAll();
        reflect.add(reflWrtX);
        reflect.add(reflWrtY);
		reflect.revalidate();
		reflect.repaint();
	}

}
