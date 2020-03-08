package GeometricTransformation;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Main {
	public static void main(String[] args) { 
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame("Geometric Transformation");
		frame.add(new mainBoard());
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
