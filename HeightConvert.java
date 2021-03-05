import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.border.*;
import javax.swing.BorderFactory.*;
import java.sql.*;

class HeightConvert extends JFrame {
	
	private JLabel heightLabel, feetLabel, inchesLabel;
	private JTextField feetField, inchesField;
	private JButton convertBtn;
	private JPanel formPanel;
	private double feet, inches;

	public HeightConvert() {
		
		heightLabel = new JLabel("Enter your height");
		feetLabel = new JLabel("Feet");
		inchesLabel = new JLabel("Inches");
		feetField = new JTextField(10);
		inchesField = new JTextField(10);
		convertBtn = new JButton("CONVERT");
		formPanel = new JPanel();

		// Font
		Font f = new Font("Times New Roman",Font.BOLD,30);
		heightLabel.setFont(f);
		feetLabel.setFont(f);
		inchesLabel.setFont(f);
		feetField.setFont(f);
		inchesField.setFont(f);
		convertBtn.setFont(f);

		// BG color
		formPanel.setBackground(Color.decode("#91d18b"));
		convertBtn.setBackground(Color.decode("#f7f7e8"));
	
		// Border

		Border spaceBorder = BorderFactory.createEmptyBorder(15,15,15,15);
		Border lineBorder = BorderFactory.createLineBorder(Color.decode("#121013"));
		formPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,lineBorder));

		layoutControl();		

		// Btn click events

		convertBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				boolean validated = validateTextField();
				if(validated){
				try {

					Class.forName("com.mysql.cj.jdbc.Driver");  
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bmicalculator","root","abc456");  
					
					String sql = "{ call heightConverter(?, ?, ?) }";
					CallableStatement stmt = con.prepareCall(sql);
					stmt.setDouble(1,feet);
					stmt.setDouble(2,inches);

					stmt.registerOutParameter(3, Types.DOUBLE);
					stmt.execute();

					double totalMeter = stmt.getDouble(3);
					String total = String.format("%.2f",totalMeter);

					JOptionPane.showMessageDialog(HeightConvert.this,"Height in meters : " + total);
					dispose();

				} catch(Exception ve) {
					JOptionPane.showMessageDialog(HeightConvert.this,"Error --> " + ve);
				}
			}
			}
		});

		setTitle("Convert height");
		setSize(300,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private boolean validateTextField() {
		try{
			feet = Double.parseDouble(feetField.getText());
			if(feet < 0) {
				JOptionPane.showMessageDialog(HeightConvert.this,"Invalid Feet","Wrong input",JOptionPane.ERROR_MESSAGE);
			feetField.setText("");
			feetField.requestFocus();
			return false;
			}
		} catch(NumberFormatException ve) {
			JOptionPane.showMessageDialog(HeightConvert.this,"Invalid Feet","Wrong input",JOptionPane.ERROR_MESSAGE);
			feetField.setText("");
			feetField.requestFocus();
			return false;
		}

		try{
			inches = Double.parseDouble(inchesField.getText());
			if(inches < 0) {
				JOptionPane.showMessageDialog(HeightConvert.this,"Invalid inches","Wrong input",JOptionPane.ERROR_MESSAGE);
			inchesField.setText("");
			inchesField.requestFocus();
			return false;
			}
		} catch(NumberFormatException ve) {
			JOptionPane.showMessageDialog(HeightConvert.this,"Invalid inches","Wrong input",JOptionPane.ERROR_MESSAGE);
			inchesField.setText("");
			inchesField.requestFocus();
			return false;
		}

		return true;		
	}

	private void layoutControl() {
		formPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		formPanel.add(heightLabel);
		formPanel.add(feetLabel);
		formPanel.add(feetField);
		formPanel.add(inchesLabel);
		formPanel.add(inchesField);
		formPanel.add(convertBtn);

		setLayout(new BorderLayout());
		add(formPanel,BorderLayout.CENTER);
	}
}