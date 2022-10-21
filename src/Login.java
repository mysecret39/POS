import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Login extends JFrame {
	JLabel usernameLabel, passwordLabel, showPasswordLabel;
	JTextField username;
	JPasswordField password;
	JPanel centerBottom, northPanel, buttonPanel, southPanel;
	JButton login;
	JCheckBox showPassword;
	Connect con = new Connect();

	public Login() {

		frameSettings();

		// JPanel;
		centerBottom = new JPanel(new GridLayout(2, 2));
		northPanel = new JPanel();
		buttonPanel = new JPanel(new GridLayout(2, 1));
		southPanel = new JPanel();

		// JLabel

		usernameLabel = new JLabel("Username");
		passwordLabel = new JLabel("Password");
		showPasswordLabel = new JLabel("Show Password");

		// JButton
		login = new JButton("Login");

		// JCheckBox

		showPassword = new JCheckBox();

		// JTextField
		username = new JTextField();

		// JPasswordField
		password = new JPasswordField();

		// Add to the panel

		centerBottom.add(usernameLabel);
		centerBottom.add(username);
		centerBottom.add(passwordLabel);
		centerBottom.add(password);
		buttonPanel.add(showPassword);
		buttonPanel.add(showPasswordLabel);
		buttonPanel.add(login);
		southPanel.add(buttonPanel, BorderLayout.CENTER);

		// Add to the frame
		add(centerBottom, BorderLayout.CENTER);
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
		revalidate();

		// Function checkbox
		showPassword.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					password.setEchoChar((char) 0);
				} else {
					password.setEchoChar('\u2022');
				}

			}
		});

		// Login buttonActionListener
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String usernameGetText = username.getText().toString();
				String passwordGetText = new String(password.getPassword());
				String query = "SELECT * FROM staff WHERE staffUsername = '" + usernameGetText
						+ "' AND staffPassword = '" + passwordGetText + "'";

				ResultSet rs = con.executeQuery(query);
				if (username.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Username cannot be empty", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else if (password.getPassword().length == 0) {
					JOptionPane.showMessageDialog(null, "Password cannot be empty", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else if (username.getText().length() > 20) {
					JOptionPane.showMessageDialog(null, "Username cannot more than 20", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else if (password.getPassword().length > 20) {
					JOptionPane.showMessageDialog(null, "Password cannot more than 20", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						if (!(rs.next())) {
							JOptionPane.showMessageDialog(null, "Username / Password is Wrong", "Warning",
									JOptionPane.WARNING_MESSAGE);
						} else {
							int operatorID = rs.getInt(1);
							String role = rs.getString(4);
							dispose();
							new SalesEntry(new User(operatorID, role));
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}

				}

			}
		});
	}

	public void frameSettings() {
		setTitle("Penjualan Baju");
		getContentPane().setLayout(null);
		setVisible(true);
		setSize(400, 200);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		((JComponent) getContentPane()).setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Welcome to the application", TitledBorder.LEFT, TitledBorder.TOP));
	}

}
