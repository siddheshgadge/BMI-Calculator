import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.border.*;
import javax.swing.BorderFactory.*;
import java.sql.*;
import java.text.SimpleDateFormat;  
import java.util.Date;  

class CalculateBmi extends JFrame {
	private JLabel nameLabel, ageLabel, phoneLabel, genderLabel, heightLabel, weightLabel;
	private JTextField nameField, ageField, phoneField, heightField, weightField;
	private JButton calcBtn, backBtn, convertBtn;
	private JPanel formPanel, btnPanel;
	private JRadioButton maleRadio, femaleRadio;
	private ButtonGroup genderGroup;
	private double bmi;
	private String bmiDecision;

	// Creating person instance
	Person p = new Person();

	public CalculateBmi() {

		nameLabel = new JLabel("Enter Name : ");
		ageLabel  = new JLabel("Enter age : ");
		phoneLabel = new JLabel("Enter phone : ");
		genderLabel = new JLabel("Gender : ");
		heightLabel = new JLabel("Enter height in mtrs : ");
		weightLabel = new JLabel("Enter weight in kgs : ");
		nameField = new JTextField(13);
		ageField = new JTextField(13);
		phoneField = new JTextField(13);
		heightField = new JTextField(13);
		weightField = new JTextField(13);
		calcBtn = new JButton("CALCULATE");
		backBtn = new JButton("BACK");
		convertBtn = new JButton("CONVERT");
		formPanel = new JPanel();
		btnPanel = new JPanel();

		//Radio Buttons

		maleRadio = new JRadioButton("Male");
		femaleRadio = new JRadioButton("Female");

		maleRadio.setActionCommand("male");
		femaleRadio.setActionCommand("female");
		maleRadio.setSelected(true);

		genderGroup = new ButtonGroup();
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);

		// Font style
		Font f = new Font("Times New Roman",Font.BOLD,20);
		nameLabel.setFont(f);
		nameField.setFont(f);
		ageLabel.setFont(f);
		ageField.setFont(f);
		phoneLabel.setFont(f);
		phoneField.setFont(f);
		genderLabel.setFont(f);
		maleRadio.setFont(f);
		femaleRadio.setFont(f);
		heightLabel.setFont(f);
		heightField.setFont(f);
		convertBtn.setFont(f);
		weightLabel.setFont(f);
		weightField.setFont(f);
		calcBtn.setFont(f);
		backBtn.setFont(f);
		
		layoutControl();

		// set BG
		formPanel.setBackground(Color.decode("#a3d2ca"));
		calcBtn.setBackground(Color.decode("#f7f7e8"));
		convertBtn.setBackground(Color.decode("#f7f7e8"));
		backBtn.setBackground(Color.decode("#f7f7e8"));

		// btnSize
		Dimension btnSize = calcBtn.getPreferredSize();
		backBtn.setPreferredSize(btnSize);

		// Border

		Border spaceBorder = BorderFactory.createEmptyBorder(15,15,15,15);
		Border lineBorder = BorderFactory.createLineBorder(Color.decode("#121013"));
		formPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,lineBorder));

		// Button click events

		backBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				MainFrame m = new MainFrame();
				dispose();
			}
		});

		convertBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				HeightConvert h = new HeightConvert();
			}
		});

		calcBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				boolean validated = validateTextFields();


			if(validated){
				try{
					Class.forName("com.mysql.cj.jdbc.Driver");  
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bmicalculator","root","abc456");  
					 
					
					String sql = "{ call calculateBmi(?,?,?,?) }";
					CallableStatement stmt = con.prepareCall(sql);
					stmt.setDouble(1,p.getWeight());
					stmt.setDouble(2,p.getHeight());

					stmt.registerOutParameter(3, Types.DOUBLE);
					stmt.execute();

					bmi = stmt.getDouble(3);
					bmiDecision = stmt.getString(4);

					String finalbmi = String.format("%.2f",bmi);
					
					// date 
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
   					Date date = new Date(); 
   					String today = formatter.format(date);

   					// Inserting values to the database
   					String insert_sql = "insert into person(name,age,phone,gender,height,weight,caldate,bmi,bmidecision) values(?,?,?,?,?,?,?,?,?)";
   					
   					PreparedStatement statmt = con.prepareStatement(insert_sql);

   					statmt.setString(1,p.getName());
   					statmt.setInt(2,p.getAge());
   					statmt.setLong(3,p.getPhone());
   					statmt.setString(4,p.getGender());
   					statmt.setDouble(5,p.getHeight());
   					statmt.setDouble(6,p.getWeight());
   					statmt.setString(7,today);
   					statmt.setDouble(8,bmi);
   					statmt.setString(9,bmiDecision);

   					statmt.executeUpdate();

   					showMessage(finalbmi,bmiDecision);

   					nameField.setText("");
   					ageField.setText("");
   					phoneField.setText("");
   					maleRadio.setSelected(true);
   					heightField.setText("");
   					weightField.setText("");
   					nameField.requestFocus();

					con.close();  
				} catch(Exception ve) {
					JOptionPane.showMessageDialog(CalculateBmi.this,ve.getMessage());
				}
			}	
			}
		});

		setTitle("Calculate");
		setSize(600,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	private void showMessage(String finalbmi, String bmiDecision) {
		JFrame resultMsg = new JFrame("Result");
		JLabel bmi = new JLabel("BMI : " + finalbmi);
		JLabel bmidec = new JLabel("Remark : " + bmiDecision);
		JButton okBtn = new JButton("THANK YOU");
		JPanel resPanel = new JPanel();
		JPanel btnPanel = new JPanel();

		okBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				resultMsg.dispose();
			}
		});

		// set BG
		resPanel.setBackground(Color.decode("#91d18b"));
		btnPanel.setBackground(Color.decode("#91d18b"));
		okBtn.setBackground(Color.decode("#f7f7e8"));

		// Border

		Border spaceBorder = BorderFactory.createEmptyBorder(15,15,15,15);
		Border lineBorder = BorderFactory.createLineBorder(Color.decode("#121013"));
		resPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,lineBorder));

		// Font
		Font f = new Font("Times New Roman",Font.BOLD,40);
		bmi.setFont(f);
		bmidec.setFont(f);
		Font fBtn = new Font("Times New Roman",Font.BOLD,20);
		okBtn.setFont(fBtn);

		resPanel.setLayout(new BorderLayout());
		resPanel.add(bmidec,BorderLayout.NORTH);
		resPanel.add(bmi,BorderLayout.CENTER);
		
		btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		btnPanel.add(okBtn);
		resPanel.add(btnPanel,BorderLayout.SOUTH);

		resultMsg.setLayout(new BorderLayout());
		resultMsg.add(resPanel,BorderLayout.CENTER);

		resultMsg.setSize(450,230);
		resultMsg.setLocationRelativeTo(CalculateBmi.this);
		resultMsg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		resultMsg.setVisible(true);

	}

	private boolean validateTextFields() {

		// Validating name
		String name = nameField.getText().toLowerCase().trim();
		if(name.length() < 2) {
			JOptionPane.showMessageDialog(CalculateBmi.this,"Invalid Name","Wrong input",JOptionPane.ERROR_MESSAGE);
			nameField.setText("");
			nameField.requestFocus();
			return false;
		} else if(isNotAlphabet(name)) {
			JOptionPane.showMessageDialog(CalculateBmi.this,"Invalid Name","Wrong input",JOptionPane.ERROR_MESSAGE);
			nameField.setText("");
			nameField.requestFocus();
			return false;
		} else {
			p.setName(name);
		}

		// Validating age

		try{

			int age = Integer.parseInt(ageField.getText());
			p.setAge(age);

		} catch(NumberFormatException ve) {
			JOptionPane.showMessageDialog(CalculateBmi.this,"Invalid age","Wrong Input",JOptionPane.ERROR_MESSAGE);
			ageField.setText("");
			ageField.requestFocus();
			return false;
		}

		// Validating phone no
		try{
			long phone = Long.parseLong(phoneField.getText());
			p.setPhone(phone);
		} catch(NumberFormatException ve) {
			JOptionPane.showMessageDialog(CalculateBmi.this,"Invalid phone no.","Wrong Input",JOptionPane.ERROR_MESSAGE);
			phoneField.setText("");
			phoneField.requestFocus();
			return false;
		}

		// As gender is auto selected no error can occur
		String gender = genderGroup.getSelection().getActionCommand();
		p.setGender(gender);

		// validating height
		try {
			double height = Double.parseDouble(heightField.getText());
			p.setHeight(height);
		} catch(NumberFormatException ve) {
			JOptionPane.showMessageDialog(CalculateBmi.this,"Invalid height","Wrong Input",JOptionPane.ERROR_MESSAGE);
			heightField.setText("");
			heightField.requestFocus();
			return false;
		}

		// validating weight
		try {
			double weight = Double.parseDouble(weightField.getText());
			p.setWeight(weight);
		} catch(NumberFormatException ve) {
			JOptionPane.showMessageDialog(CalculateBmi.this,"Invalid weight","Wrong Input",JOptionPane.ERROR_MESSAGE);
			weightField.setText("");
			weightField.requestFocus();
			return false;
		}
		return true;
	}

	private boolean isNotAlphabet(String name) {
		for(int i = 0; i < name.length(); i++) {
			char ch = name.charAt(i);
			if(Character.isAlphabetic(ch)) {
				continue;
			} else {
				return true;
			}
		}
		return false;
	}

	private void layoutControl() {
		formPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		//First row
		gc.gridy = 0;
		gc.gridx = 0;

		gc.fill = GridBagConstraints.NONE;

		gc.weightx = 0.3;
		gc.weighty = 0.3;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(5,0,0,5);
		formPanel.add(nameLabel,gc);

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(5,0,0,0);
		formPanel.add(nameField,gc);

		//Next row
		gc.gridy++;
		gc.gridx = 0;

		gc.weightx = 0.3;
		gc.weighty = 0.3;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,5);
		formPanel.add(ageLabel,gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		formPanel.add(ageField,gc);

		//Next row
		gc.gridy++;
		gc.gridx = 0;

		gc.weightx = 0.3;
		gc.weighty = 0.3;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,5);
		formPanel.add(phoneLabel,gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		formPanel.add(phoneField,gc);
		
		//Next row
		gc.gridy++;
		gc.gridx = 0;

		gc.weightx = 0.3;
		gc.weighty = 0.3;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,5);
		formPanel.add(genderLabel,gc);
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		formPanel.add(maleRadio,gc);

		gc.gridy++;
		gc.anchor = GridBagConstraints.LINE_START;
		formPanel.add(femaleRadio,gc);

		//Next row
		gc.gridy++;
		gc.gridx = 0;

		gc.weightx = 0.3;
		gc.weighty = 0.3;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,5);
		formPanel.add(heightLabel,gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		formPanel.add(heightField,gc);

		gc.gridx = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		formPanel.add(convertBtn,gc);

		//Next row
		gc.gridy++;
		gc.gridx = 0;

		gc.weightx = 1;
		gc.weighty = 2;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,5);
		formPanel.add(weightLabel,gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		formPanel.add(weightField,gc);

		//Next row
		gc.gridy++;
		gc.gridx = 0;

		gc.weightx = 1;
		gc.weighty = 2.0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,20);
		formPanel.add(calcBtn,gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		formPanel.add(backBtn,gc);
		
		setLayout(new BorderLayout());
		add(formPanel,BorderLayout.CENTER);
		
	}
}