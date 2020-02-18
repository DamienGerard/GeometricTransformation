package GeometricTransformation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class mainBoard extends JPanel{
	public Graph myGraph;
	private JScrollPane shapeList;
	private ShapeList shapeListPanel;
	private JButton addShape;
	private JLabel helper;
	
	mainBoard(){
		setLayout(new BorderLayout());
		
		addShape = new JButton("Add Shape");
		addShape.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				shapeListPanel.incIndex();
				shapeListPanel.add(new ShapeItem(myGraph, shapeListPanel, shapeListPanel.getIndex()),0);
			}
			
		});
		
		add(BorderLayout.NORTH, addShape);
		
		myGraph = new Graph(600, 450);
		add(BorderLayout.CENTER, myGraph);
		
		shapeListPanel = new ShapeList(this) {
			public Dimension getPreferredSize() {
			    return new Dimension(350, this.length()*220);
			}
			
		};
		shapeListPanel.setLayout(new FlowLayout());
		shapeList = new JScrollPane(shapeListPanel);
		shapeList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		shapeList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		shapeList.getVerticalScrollBar().setUnitIncrement(16);
		//shapeList.setBorder(new LineBorder(Color.ORANGE, 4, true));
		add(BorderLayout.EAST, shapeList);
		
		helper = new JLabel("I'm here to help");
		add(BorderLayout.SOUTH, helper);
		
	
	}
}
