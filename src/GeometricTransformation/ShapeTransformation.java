package GeometricTransformation;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ShapeTransformation extends JPanel implements ItemListener {
	private static final long serialVersionUID = 1L;
	private JPanel cards;
	private final static String TRANSLATION = "Translation";
	private final static String NORMAL_ROTATION = "Normal rotation";
	private final static String FIXED_POINT_ROTATION = "Rotation about fixed point";
	private final static String NORMAL_SCALING = "Normal scaling";
	private final static String FIXED_POINT_SCALING = "Scaling about a fixed point";
	private final static String SHEAR = "Shear";
	private final static String REFLECTION = "Reflection";
	private String actualTransformation = TRANSLATION;
	private JButton reflWrtX;
	private JButton reflWrtY;
	private JPanel reflect;
	private boolean reflWrtYflag;
	private JButton close;
	private static ShapeTransformationMenu transformationList;
	private ArrayList<Double> xPoints;
	private ArrayList<Double> yPoints;
	private ArrayList<Double> xOriginalPoints;
	private ArrayList<Double> yOriginalPoints;
	private int transFrameNumber;
	private JTextField transEntry1;
	private JTextField transEntry2;
	private JTextField normRotEntry1;
	private JTextField fixPRotEntry1;
	private JTextField fixPRotEntry2;
	private JTextField fixPRotEntry3;
	private JTextField normScaleEntry1;
	private JTextField normScaleEntry2;
	private JTextField fixPScaleEntry1;
	private JTextField fixPScaleEntry2;
	private JTextField fixPScaleEntry3;
	private JTextField fixPScaleEntry4;
	private JTextField shearEntry1;
	private JTextField reflectYEntry1;
	private JTextField reflectYEntry2;
	private JTextField reflectXEntry1;
	private JTextField reflectXEntry2;
	
	ShapeTransformation(ShapeTransformationMenu tl){
		transformationList = tl;
		setPreferredSize(new Dimension(250, 75));
		setBorder(new LineBorder(Color.BLACK, 2, true));
		
        String comboBoxItems[] = {TRANSLATION, NORMAL_ROTATION, FIXED_POINT_ROTATION, NORMAL_SCALING, FIXED_POINT_SCALING, SHEAR, REFLECTION};
        JComboBox<String> cb = new JComboBox<String>(comboBoxItems);
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
        
        JPanel trans = new JPanel();
        trans.add(new JLabel("Vector i: "));
        transEntry1 = new JTextField(3);
        trans.add(transEntry1);
        trans.add(new JLabel("Vector j: "));
        transEntry2 = new JTextField(3);
        trans.add(transEntry2);
        
        JPanel normRot = new JPanel();
        normRot.add(new JLabel("Angle: "));
        normRotEntry1 = new JTextField(3);
        normRot.add(normRotEntry1);
        
        JPanel fixPRot = new JPanel();
        fixPRot.add(new JLabel("Angle: "));
        fixPRotEntry1 = new JTextField(2);
        fixPRot.add(fixPRotEntry1);
        fixPRot.add(new JLabel("x: "));
        fixPRotEntry2 = new JTextField(2);
        fixPRot.add(fixPRotEntry2);
        fixPRot.add(new JLabel("y: "));
        fixPRotEntry3 = new JTextField(2);
        fixPRot.add(fixPRotEntry3);
        
        JPanel normScale = new JPanel();
        normScale.add(new JLabel("Scale X: "));
        normScaleEntry1 = new JTextField(3);
        normScale.add(normScaleEntry1);
        normScale.add(new JLabel("Scale Y: "));
        normScaleEntry2 = new JTextField(3);
        normScale.add(normScaleEntry2);
        
        JPanel fixPScale = new JPanel();
        fixPScale.add(new JLabel("Scale: "));
        fixPScaleEntry1 = new JTextField(1);
        fixPScale.add(fixPScaleEntry1);
        fixPScaleEntry2 = new JTextField(1);
        fixPScale.add(fixPScaleEntry2);
        fixPScale.add(new JLabel("x: "));
        fixPScaleEntry3 = new JTextField(3);
        fixPScale.add(fixPScaleEntry3);
        fixPScale.add(new JLabel("y: "));
        fixPScaleEntry4 = new JTextField(3);
        fixPScale.add(fixPScaleEntry4);
        
        JPanel shear = new JPanel();
        shear.add(new JLabel("Shear: "));
        shearEntry1 = new JTextField(3);
        shear.add(shearEntry1);
        
        reflect = new JPanel();
        reflWrtX = new JButton("wrt x-axis");
        reflWrtX.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
		        reflectXEntry1 = new JTextField(3);
		        reflectXEntry2 = new JTextField(3);
				reflWrtYflag = false;
				reflect.removeAll();
				reflect.add(new JLabel("x = "));
				reflect.add(reflectXEntry1);
				reflect.add(new JLabel("y + "));
				reflect.add(reflectXEntry2);
				reflect.revalidate();
				reflect.repaint();
			}
        	
        });
        reflect.add(reflWrtX);
        reflWrtY = new JButton("wrt y-axis");
        reflWrtY.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
		        reflectYEntry1 = new JTextField(3);
		        reflectYEntry2 = new JTextField(3);
				reflWrtYflag = true;
				reflect.removeAll();
				reflect.add(new JLabel("y = "));
				reflect.add(reflectYEntry1);
				reflect.add(new JLabel("x + "));
				reflect.add(reflectYEntry2);
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

	@Override
	public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)e.getItem());
        actualTransformation = (String)e.getItem();
        cards.revalidate();
        cards.repaint();
		reflect.removeAll();
        reflect.add(reflWrtX);
        reflect.add(reflWrtY);
		reflect.revalidate();
		reflect.repaint();
	}

	public int getFramecount() {
		if(actualTransformation.equals(TRANSLATION)) {
			return (int)(Math.sqrt(Math.pow(Double.parseDouble(transEntry1.getText()), 2)+Math.pow(Double.parseDouble(transEntry2.getText()), 2)))*100;
		}else if(actualTransformation.equals(NORMAL_ROTATION)) {
			return Math.abs((int)(Double.parseDouble(normRotEntry1.getText())*10));
		}else if(actualTransformation.equals(FIXED_POINT_ROTATION)){
			return Math.abs((int)(Double.parseDouble(fixPRotEntry1.getText())*10));
		}else if(actualTransformation.equals(NORMAL_SCALING)) {
			double maxDistance = 0;
			double distance;
			int farthestPointindex = 0;
			for(int i=0; i<xOriginalPoints.size(); i++) {
				distance = Math.sqrt(Math.pow(xOriginalPoints.get(i), 2)+Math.pow(yOriginalPoints.get(i), 2));
				if(distance>maxDistance) {
					maxDistance = distance;
					farthestPointindex = i;
				}
			}
			return 100*Math.abs((int)(Math.sqrt(Math.pow((Double.parseDouble(normScaleEntry1.getText())-1)*(Math.abs(xOriginalPoints.get(farthestPointindex))+1), 2)+Math.pow((Double.parseDouble(normScaleEntry2.getText())-1)*(Math.abs(yOriginalPoints.get(farthestPointindex))+1), 2))));
		}else if(actualTransformation.equals(FIXED_POINT_SCALING)){
			double maxDistance = 0;
			double distance;
			int farthestPointindex = 0;
			for(int i=0; i<xOriginalPoints.size(); i++) {
				distance = Math.sqrt(Math.pow(xOriginalPoints.get(i)-Double.parseDouble(fixPScaleEntry3.getText()), 2)+Math.pow(yOriginalPoints.get(i)-Double.parseDouble(fixPScaleEntry4.getText()), 2));
				if(distance>maxDistance) {
					maxDistance = distance;
					farthestPointindex = i;
				}
			}
			return 100*Math.abs((int)(Math.sqrt(Math.pow((Double.parseDouble(fixPScaleEntry1.getText())-1)*(Math.abs(xOriginalPoints.get(farthestPointindex)-Double.parseDouble(fixPScaleEntry3.getText()))+1), 2)+Math.pow((Double.parseDouble(fixPScaleEntry2.getText())-1)*(Math.abs(yOriginalPoints.get(farthestPointindex)-Double.parseDouble(fixPScaleEntry4.getText()))+1), 2))));
		}else if(actualTransformation.equals(SHEAR)) {
			return Math.abs((int)(Double.parseDouble(shearEntry1.getText())*100));
		}else if(actualTransformation.equals(REFLECTION)) {
			double maxDistance = 0;
			double distance, A, B, C;
			
			if(reflWrtYflag) {
				A = -1*Double.parseDouble(reflectYEntry1.getText());
				B = 1;
				C = -1*Double.parseDouble(reflectYEntry2.getText());
			}else {
				A = 1;
				B = -1*Double.parseDouble(reflectXEntry1.getText());
				C = -1*Double.parseDouble(reflectXEntry2.getText());
			} 
			
			for(int i=0; i<xOriginalPoints.size(); i++) {
				distance = Math.abs((A*xOriginalPoints.get(i))+(B*yOriginalPoints.get(i))+C)/Math.sqrt((Math.pow(A, 2))+(Math.pow(B, 2)));
				if(distance>maxDistance) {
					maxDistance = distance;
				}
			}

			return (int)(2*100*maxDistance+0.5);
		}
		return 0;
	}

	public void transform(ArrayList<Double> xPoints, ArrayList<Double> yPoints, int transFrameNumber) {
		this.xPoints = xPoints;
		this.yPoints = yPoints;
		this.transFrameNumber = transFrameNumber;
		if(actualTransformation.equals(TRANSLATION)) {
			translate();
		}else if(actualTransformation.equals(NORMAL_ROTATION)||actualTransformation.equals(FIXED_POINT_ROTATION)) {
			rotate();
		}else if(actualTransformation.equals(NORMAL_SCALING)||actualTransformation.equals(FIXED_POINT_SCALING)) {
			scale();
		}else if(actualTransformation.equals(SHEAR)) {
			shear();
		}else if(actualTransformation.equals(REFLECTION)) {
			reflect();
		}
	}

	private void reflect() {
		double k;
		double intercept;
		@SuppressWarnings("unchecked")
		ArrayList<Double> preTransformX = (ArrayList<Double>) this.xPoints.clone();
		@SuppressWarnings("unchecked")
		ArrayList<Double> preTransformY = (ArrayList<Double>) this.yPoints.clone();
		if(reflWrtYflag) {
			k = Double.parseDouble(reflectYEntry1.getText());
			intercept = Double.parseDouble(reflectYEntry2.getText());
		}else {
			k = Double.parseDouble(reflectXEntry1.getText());
			if(k==0) {
				k = 0x7fff_ffff_ffff_ffffL;
			}else {
				k = 1/k;
			}
			intercept = Double.parseDouble(reflectXEntry2.getText());
		}

		int entireFrameCount = getFramecount();
		
		double[][] T = {{(2/(1+Math.pow(k, 2)))-1, (2*k)/(1+Math.pow(k, 2)), 0},
						{(2*k)/(1+Math.pow(k, 2)), ((2*Math.pow(k, 2))/(1+Math.pow(k, 2)))-1, 0},
						{0, 0, 1}};
		
		if(reflWrtYflag) {
			offset(0, -1*intercept);
		}else {
			offset(-1*intercept, 0);
		}
		
		processTransformation(T);
		
		if(reflWrtYflag) {
			offset(0, intercept);
		}else {
			offset(intercept, 0);
		}
		
		if(transFrameNumber<entireFrameCount) {
			double x, y;
			for(int i=0; i<xPoints.size(); i++) {
				x = preTransformX.get(i) + (1.0*transFrameNumber/entireFrameCount)*(xPoints.get(i)-preTransformX.get(i));
				y = preTransformY.get(i) + (1.0*transFrameNumber/entireFrameCount)*(yPoints.get(i)-preTransformY.get(i));
				xPoints.set(i, x);
				yPoints.set(i, y);
			}	
		}
	}

	private void shear() {
		double s = Double.parseDouble(shearEntry1.getText());

		int entireFrameCout = getFramecount();
		
		if(transFrameNumber<entireFrameCout) {
			s = (s*transFrameNumber)/entireFrameCout;
		}
		
		double[][] T = {{1, s, 0},
						{0, 1, 0},
						{0, 0, 1}};
		
		processTransformation(T);
	}

	private void scale() {
		double a, b;
		if(actualTransformation.equals(NORMAL_SCALING)) {
			a = Double.parseDouble(normScaleEntry1.getText());
			b = Double.parseDouble(normScaleEntry2.getText());
		}else {
			a = Double.parseDouble(fixPScaleEntry1.getText());
			b = Double.parseDouble(fixPScaleEntry1.getText());
		}

		int entireFrameCount = getFramecount();
		if(transFrameNumber<entireFrameCount) {
			if(Math.abs(a)>=1) {
				a = 1+((a-1)*transFrameNumber)/entireFrameCount;
			}else if(Math.abs(a)<=1){
				a = 1-((1-a)*transFrameNumber)/entireFrameCount;
			}
			
			if(Math.abs(b)>=1) {
				b = 1+((b-1)*transFrameNumber)/entireFrameCount;
			}else if(Math.abs(b)<=1){
				b = 1-((1-b)*transFrameNumber)/entireFrameCount;
			}
		}
			
		double[][] T = {{a, 0, 0},
						{0, b, 0},
						{0, 0, 1}};
		
		if(actualTransformation.equals(FIXED_POINT_SCALING)) {
			offset(Double.parseDouble(fixPScaleEntry3.getText())*(-1), Double.parseDouble(fixPScaleEntry4.getText())*(-1));
		}
		
		processTransformation(T);
		
		if(actualTransformation.equals(FIXED_POINT_SCALING)) {
			offset(Double.parseDouble(fixPScaleEntry3.getText()), Double.parseDouble(fixPScaleEntry4.getText()));
		}
	}

	private void rotate() {
		double theta = Double.parseDouble((normRotEntry1.getText().length()>0)?normRotEntry1.getText():fixPRotEntry1.getText());

		
		int entireFrameCount = getFramecount();
		
		if(Math.abs((theta*transFrameNumber)/entireFrameCount)<Math.abs(theta)) {
			theta = (theta*transFrameNumber)/entireFrameCount;
		}
		
		theta = (theta*Math.PI)/180;
			
		double[][] T = {{Math.cos(theta), Math.sin(theta), 0},
						{-Math.sin(theta), Math.cos(theta), 0},
						{0, 0, 1}};
		
		if(actualTransformation.equals(FIXED_POINT_ROTATION)) {
			offset(Double.parseDouble(fixPRotEntry2.getText())*(-1), Double.parseDouble(fixPRotEntry3.getText())*(-1));
		}
		
		processTransformation(T);
		
		if(actualTransformation.equals(FIXED_POINT_ROTATION)) {
			offset(Double.parseDouble(fixPRotEntry2.getText()), Double.parseDouble(fixPRotEntry3.getText()));
		}
	}

	private void offset(double offsetX, double offsetY) {
		double x, y;
		for(int i=0; i<xPoints.size(); i++) {
			x = xPoints.get(i)+offsetX;
			y = yPoints.get(i)+offsetY;
			xPoints.set(i, x);
			yPoints.set(i, y);
		}
	}

	private void translate() {
		double a = Double.parseDouble(transEntry1.getText());
		double b = Double.parseDouble(transEntry2.getText());
		int entireFrameCount = getFramecount();
		
		if(Math.abs((a*transFrameNumber)/entireFrameCount)<Math.abs(a) || Math.abs((b*transFrameNumber)/entireFrameCount)<Math.abs(b)) {
			a = (a*transFrameNumber)/entireFrameCount;
			b = (b*transFrameNumber)/entireFrameCount;
		}
		
		double[][] T = {{1, 0, a},
						{0, 1, b},
						{0, 0, 1}};
		
		processTransformation(T);
	}

	private void processTransformation(double[][] t) {
		double x, y;
		for(int i=0; i<xPoints.size(); i++) {
			x = xPoints.get(i)*t[0][0] + yPoints.get(i)*t[0][1] + t[0][2];
			y = xPoints.get(i)*t[1][0] + yPoints.get(i)*t[1][1] + t[1][2];
			xPoints.set(i, x);
			yPoints.set(i, y);
		}
	}
	
	public void setPoints(ArrayList<Double> xPoints, ArrayList<Double> yPoints) {
		this.xOriginalPoints = xPoints;
		this.yOriginalPoints = yPoints;
	}

}
