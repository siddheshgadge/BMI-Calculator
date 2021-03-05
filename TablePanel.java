import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;
import java.awt.event.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class TablePanel extends JPanel {

	private JTable table;
	private String[] colNames = {"Person ID","Name","Age","Gender","Date","BMI"};

	public TablePanel(){
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(colNames);
		
		table = new JTable();
		table.setModel(model);

		int personid = 0;
		String name = "";
		int age = 0;
		String gender = "";
		String caldate = "";
		String bmidecision = "";
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bmicalculator","root","abc456");  
					
			String sql = "select * from history";
					
			PreparedStatement ps = con.prepareStatement(sql);
		
			ResultSet rs = ps.executeQuery();
		
			int i = 0;
		
			while(rs.next()) {
				personid = rs.getInt(1);
				name = rs.getString(2);
				age = rs.getInt(3);
				gender = rs.getString(4);
				caldate = rs.getString(5);
				bmidecision = rs.getString(6);
				model.addRow(new Object[]{personid,name,age,gender,caldate,bmidecision});
				i++;
			}
			if(i < 1) {
				JOptionPane.showMessageDialog(new JDialog(),"No record found");
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(new JDialog(),"issue -> " + e);
		}

		setLayout(new BorderLayout());

		add(new JScrollPane(table),BorderLayout.CENTER);
	}
}