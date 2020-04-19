package GeometricTransformation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class mainBoard extends JPanel{
	private static final long serialVersionUID = 1L;
	public Graph myGraph;
	private JScrollPane itemList;
	private ItemList itemListPanel;
	private JButton addShape;
	private JButton addLine;
	private JButton clip;
	private JButton clear;
	
	mainBoard(){
		setLayout(new BorderLayout());
		JPanel topPane = new JPanel();
		
		addShape = new JButton("Add Shape");
		addShape.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!(myGraph.isRecordingPoint())) {
					myGraph.startRecordPoint();
					itemListPanel.incShapeIndex();
					itemListPanel.add(new ShapeItem(myGraph, itemListPanel, itemListPanel.getShapeIndex()), 0);		
				}
			}
			
		});
		topPane.add(addShape);
		
		addLine = new JButton("Add Line");
		addLine.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!(myGraph.isRecordingPoint())) {
					myGraph.startRecordPoint();
					itemListPanel.incLineIndex();
					LineItem newLine = new LineItem(myGraph, itemListPanel, itemListPanel.getLineIndex());
					itemListPanel.add(newLine, 0);	
					myGraph.setLineToRecord(newLine);
					myGraph.setRecordingLine(true);	
				}
			}
			
		});
		topPane.add(addLine);
		
		clip = new JButton("Clip");
		clip.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				myGraph.toggleClippingState();
			}
			
		});
		topPane.add(clip);
		
		clear = new JButton("Clear");
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!(myGraph.isRecordingPoint())) {
					itemListPanel.clear();
				}
			}
			
		});
		topPane.add(clear);
		
		add(BorderLayout.NORTH, topPane);
		
		itemListPanel = new ItemList(this) {
			private static final long serialVersionUID = 1L;

			public Dimension getPreferredSize() {
			    return new Dimension(350, this.length()*220);
			}
			
		};
		itemListPanel.setLayout(new FlowLayout());
		itemList = new JScrollPane(itemListPanel);
		itemList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		itemList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		itemList.getVerticalScrollBar().setUnitIncrement(16);
		add(BorderLayout.EAST, itemList);
		
		myGraph = new Graph(600, 450, itemListPanel);
		add(BorderLayout.CENTER, myGraph);
	
	}
}
