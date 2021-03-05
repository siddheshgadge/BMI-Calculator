import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.*;
import javax.swing.BorderFactory.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


class SplashScreen extends JFrame {
	
	private JLabel header1, header2, wIcon;
	private JPanel headerPanel;

	public SplashScreen() {
		header1 = new JLabel("BMI CALCULATOR");
		header2 = new JLabel("By Siddhesh");
		headerPanel = new JPanel();

		try{
  	    	BufferedImage wPic = ImageIO.read(this.getClass().getResource("bmi.png"));
   	    	wIcon = new JLabel(new ImageIcon(wPic));
    	 } catch(Exception e) {
    	    JOptionPane.showMessageDialog(SplashScreen.this,"Image not found","Image Error",JOptionPane.ERROR_MESSAGE);
  	    }

		// Font
		Font f = new Font("Times New Roman",Font.BOLD,30);
		header1.setFont(f);
		header2.setFont(f);
		// Border

		Border spaceBorder = BorderFactory.createEmptyBorder(30,15,15,15);
		Border lineBorder = BorderFactory.createLineBorder(Color.decode("#f6f5f5"));
		headerPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,lineBorder));

		headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		headerPanel.add(header1);
		headerPanel.add(header2);
		headerPanel.add(wIcon);

		setLayout(new BorderLayout());
		add(headerPanel,BorderLayout.CENTER);

		// BG color
		headerPanel.setBackground(Color.decode("#03506f"));
		header1.setForeground(Color.decode("#f6f5f5"));
		header2.setForeground(Color.decode("#f6f5f5"));

		setTitle("WELCOME");
		setSize(350,370);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}