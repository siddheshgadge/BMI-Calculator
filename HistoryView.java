import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class HistoryView extends JFrame {

	private JButton backBtn;
	private TablePanel tablePanel;
	private JPanel btnPanel;

	public HistoryView() {

		backBtn = new JButton("BACK");
		tablePanel = new TablePanel();
		btnPanel = new JPanel();

		layoutControl();

		// OnClick Event

		backBtn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {

				MainFrame m = new MainFrame();
				dispose();
			}

		});

		setTitle("History");
		setSize(500,350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void layoutControl() {

		btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		btnPanel.add(backBtn);

		setLayout(new BorderLayout());
		add(tablePanel,BorderLayout.CENTER);
		add(btnPanel,BorderLayout.SOUTH);

	}

}