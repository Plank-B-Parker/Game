import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VariableChanger extends JFrame implements ActionListener{

	//300
	int width = 300;
	int height = 800;
	game Game;
	JPanel toolPanel;
	JTextField springConstVal;
	JTextField springLengthVal1;
	JTextField springLengthVal2;
	JButton submit;
	
	public VariableChanger(game Game) {
		super("VariableChanger");
		this.Game = Game;
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setMinimumSize(new Dimension(0,0));
		
		JLabel springConstant = new JLabel();
		springConstant.setText("Spring constant = ");
		
		springConstVal = new JTextField("10                 ");
		springConstVal.setEditable(true);
		
		
		JLabel springLength1 = new JLabel();
		springLength1.setText("Spring length point = ");
		
		springLengthVal1 = new JTextField("10                   ");
		springLengthVal1.setEditable(true);
		
		JLabel springLength2 = new JLabel();
		springLength2.setText("Spring length object = ");
		
		springLengthVal2 = new JTextField("10                     ");
		springLengthVal2.setEditable(true);
		
		
		submit = new JButton();
		submit.setText("Submit");
		submit.addActionListener(this);
		
		toolPanel = new JPanel();
		toolPanel.add(springConstant);
		toolPanel.add(springConstVal);
		toolPanel.add(springLength1);
		toolPanel.add(springLengthVal1);
		toolPanel.add(springLength2);
		toolPanel.add(springLengthVal2);
		toolPanel.add(submit);
		
		add(toolPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String num = springConstVal.getText();
		double number = Double.parseDouble(num);
		Physics.springConstant = number;
		
		num = springLengthVal1.getText();
		number = Double.parseDouble(num);
		Physics.springWallLength = number;
		
		num = springLengthVal2.getText();
		number = Double.parseDouble(num);
		Physics.springLength = number;
		
	}
	
	

}
