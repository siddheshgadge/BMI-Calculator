import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.text.*;
import java.time.*;
import java.time.format.*;
import javax.swing.border.Border;
import javax.swing.border.*;
import javax.swing.BorderFactory.*;	
import java.sql.*;
import java.io.*;

class MainFrame extends JFrame {

	private JLabel dateLabel, wishLabel, countLabel;
	private JButton calcBtn, historyBtn, exportBtn;
	private JPanel btnPanel;

	public MainFrame() {

		// Date Label 
		Date d = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		String s = df.format(d);
		
		dateLabel = new JLabel(s);

		// Wish label
		LocalTime t = LocalTime.now();
		int h = t.getHour();
		String msg = "";
		if(h < 12)			msg = "Good Morning!";
		else if(h<16)		msg = "Good Afternoon";
		else 				msg = "Good Evening";
		wishLabel = new JLabel(msg);

		// other
		calcBtn = new JButton("Calculate BMI");
		historyBtn = new JButton("History");
		exportBtn = new JButton("Export Data");

		// count label
		int count = 0;
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bmicalculator","root","abc456");  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select countPeople()");  
			while(rs.next())  {
				count = rs.getInt(1);
			} 
			con.close();  
		}catch(Exception e){ 
			JOptionPane.showMessageDialog(MainFrame.this,"Error --> " + e);
		}  

		countLabel = new JLabel(String.valueOf("Count = " + count));
		btnPanel = new JPanel();

		layoutControl();

		// Fonts
		Font f = new Font("Times New Roman",Font.BOLD,20);
		dateLabel.setFont(f);
		wishLabel.setFont(f);
		calcBtn.setFont(f);
		historyBtn.setFont(f);
		exportBtn.setFont(f);
		countLabel.setFont(f);

		// BG Color
		btnPanel.setBackground(Color.decode("#a3d2ca"));
		calcBtn.setBackground(Color.decode("#f7f7e8"));
		historyBtn.setBackground(Color.decode("#f7f7e8"));
		exportBtn.setBackground(Color.decode("#f7f7e8"));

		// Border

		Border spaceBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border lineBorder = BorderFactory.createLineBorder(Color.decode("#121013"));
		btnPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,lineBorder));

		// btn size
		Dimension btnSize = calcBtn.getPreferredSize();
		historyBtn.setPreferredSize(btnSize);
		exportBtn.setPreferredSize(btnSize);

		// Button click events

		calcBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				CalculateBmi b = new  CalculateBmi();
				dispose();
			}
		});

		historyBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				HistoryView h = new HistoryView();
				dispose();
			}
		});

		exportBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				String filePath = "data.csv";

				try{  
					Class.forName("com.mysql.cj.jdbc.Driver");  
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bmicalculator","root","abc456");  
					String sql = "select * from person";
					Statement stmt = con.createStatement();
					ResultSet res = stmt.executeQuery(sql);

					BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath));

					fileWriter.write("personid,name,age,phone,gender,height,weight,date,BMI,Comment");

					while(res.next()) {
						int personid = res.getInt("personid");
						String name = res.getString("name");
						int age = res.getInt("age");
						long phone = res.getLong("phone");
						String gender = res.getString("gender");
						double height = res.getDouble("height");
						double weight = res.getDouble("weight");
						String date = res.getString("caldate");
						double bmi = res.getDouble("bmi");
						String comment = res.getString("bmidecision");

						String line = String.format("%d,%s,%d,%d,%s,%f,%f,%s,%f,%s",personid,name,age,phone,gender,height,weight,date,bmi,comment);
						fileWriter.newLine();
						fileWriter.write(line);
					}
					fileWriter.close();
					con.close();  
					JOptionPane.showMessageDialog(MainFrame.this,"Exported successfully");
				}catch(ClassNotFoundException ve){
					JOptionPane.showMessageDialog(MainFrame.this,"SQL Error","Database Error",JOptionPane.ERROR_MESSAGE);
				}catch(SQLException ve){
					JOptionPane.showMessageDialog(MainFrame.this,"SQL Error","Database Error",JOptionPane.ERROR_MESSAGE);
				}catch(IOException ve){
					JOptionPane.showMessageDialog(MainFrame.this,"IO Error","IO Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		setTitle("BMI Calculator");
		setSize(360,350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void layoutControl() {

		btnPanel.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		// first row
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 0.3;
		gc.weighty = 0.3;

		btnPanel.add(dateLabel,gc);

		// next row
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 0.3;
		gc.weighty = 0.3;
		
		btnPanel.add(wishLabel,gc);

		// next row
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 0.3;
		gc.weighty = 0.3;
		
	
		// next row
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 0.3;
		gc.weighty = 0.3;
	
		btnPanel.add(calcBtn,gc);
	
		// next row
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 0.3;
		gc.weighty = 0.3;
	
		btnPanel.add(historyBtn,gc);

		// next row
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 0.3;
		gc.weighty = 0.3;
		
		btnPanel.add(exportBtn,gc);

		// next row
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 1;
		
		btnPanel.add(countLabel,gc);


		setLayout(new BorderLayout());
		add(btnPanel,BorderLayout.CENTER);
	}
}