import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class SalesEntry extends JFrame {
	JMenuItem i1, i2, i3, i4, updateStock;
	JMenu menu, submenu;
	JMenuBar mb;

	JLabel memberIDLabel, totalHargaLabel, totalItemLabel;

	JTable tabel, tabelPenjualan;
	DefaultTableModel dtm, dtmPenjualan;
	JTextField memberID;

	JDesktopPane desktopPane;
	JInternalFrame itemList, penjualan;
	Connect con = new Connect();
	String[] table;
	int totalPrice = 0;
	int totalItemJual = 0;
	int totalSalesOperator = 0;
	User user;

	public SalesEntry(User user) {
		this.user = user;
		frameSettings();
		desktopPane = new JDesktopPane();
		itemList = new JInternalFrame("Item List");
		penjualan = new JInternalFrame("Penjualan");
		desktopPane.setVisible(true);
		updateStock = new JMenuItem("Update Stock");
		mb = new JMenuBar();

		setJMenuBar(mb);

		submenu = new JMenu("Menu");
		menu = new JMenu("Menu");
		i2 = new JMenuItem("Exit");
		i3 = new JMenuItem("Sales Entry");
		i4 = new JMenuItem("Total Sales Report");

		submenu.add(i3);
		submenu.add(i4);

		menu.add(submenu);
		menu.add(i2);

		mb.add(menu);
		mb.add(updateStock);

		i2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {

				System.exit(0);
			}
		});

		i3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {

				// JInternalFrame
				penjualan = new JInternalFrame("Penjualan");
				itemList = new JInternalFrame("Item List");
				SpinnerModel value = new SpinnerNumberModel(1, 0, 99, 1);
				JSpinner quantity = new JSpinner(value);
				JLabel inputQuantity = new JLabel("Input Qty");

				// JLabel
				memberIDLabel = new JLabel("Member ID");
				memberID = new JTextField();
				JPanel buttonPanel = new JPanel(new GridLayout());
				JPanel label = new JPanel(new GridLayout());
				JTextField totalHargaInput = new JTextField();
				JTextField totalItemInput = new JTextField();
				totalHargaInput.setEditable(false);
				totalItemInput.setEditable(false);

				// JButton
				String[] columnNames = { "Item ID", "Item Name", "Quantity", "Price" };
				JButton addItem = new JButton("Add Item");
				JButton checkOut = new JButton("CheckOut");

				// Setting color
				addItem.setBackground(Color.decode("#F5FFBB"));
				checkOut.setBackground(Color.decode("#82E480"));

				// JTable
				dtm = new DefaultTableModel(columnNames, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};

				refreshTable();
				tabel = new JTable(dtm);
				tabel.setFillsViewportHeight(true);
				tabel.getTableHeader().setReorderingAllowed(false);

				JScrollPane scrollPane = new JScrollPane(tabel);

				// Set setting for InternalFrame ItemList
				itemList.setSize(480, 484);
				itemList.setVisible(true);
				itemList.setLocation(407, 53);
				itemList.setResizable(false);

				// Add to the panel
				label.add(memberIDLabel);
				label.add(memberID);
				buttonPanel.add(addItem);
				buttonPanel.add(checkOut);
				buttonPanel.add(inputQuantity);
				buttonPanel.add(quantity);

				// Add to the InternalFrame
				itemList.add(label, BorderLayout.NORTH);
				itemList.add(scrollPane, BorderLayout.CENTER);
				itemList.add(buttonPanel, BorderLayout.SOUTH);

				// Fungsi

				addItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int row = tabel.getSelectedRow();
						int valueSpinInt = Integer.parseInt(quantity.getValue().toString());
						int updateQty = Integer.parseInt(dtm.getValueAt(row, 2).toString());
						int cek = 0;

						for (int i = 0; i < dtmPenjualan.getRowCount(); i++) {

							if (dtm.getValueAt(row, 0).toString().equals(dtmPenjualan.getValueAt(i, 0))) {
								cek = 1;
							}
						}
						if (cek == 0) {
							if (valueSpinInt == 0 || valueSpinInt > updateQty) {
								JOptionPane.showMessageDialog(null, "Input yang bener", "Warning",
										JOptionPane.WARNING_MESSAGE);

							} else {
								String[] table = new String[4];

								table[0] = dtm.getValueAt(row, 0).toString();
								table[1] = dtm.getValueAt(row, 1).toString();
								table[2] = quantity.getValue().toString();
								table[3] = dtm.getValueAt(row, 3).toString();
								int price = 0;

								price = valueSpinInt * Integer.parseInt(dtm.getValueAt(row, 3).toString());
								totalPrice += price;
								totalHargaInput.setText(totalPrice + "");
								totalItemJual += valueSpinInt;
								totalItemInput.setText(totalItemJual + "");
								dtm.setValueAt(updateQty - valueSpinInt, row, 2);
								dtmPenjualan.addRow(table);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Sudah ada.", "Warning", JOptionPane.WARNING_MESSAGE);
						}

					}
				});

				// JButton
				JButton panggilSupervisor = new JButton("Panggil Supervisor");
				panggilSupervisor.setBackground(Color.decode("#FF7B73"));

				// JPanel
				JPanel southPanel = new JPanel(new GridLayout(3, 1));

				// JLabel
				JLabel totalHarga = new JLabel("Total Harga");
				JLabel totalItem = new JLabel("Total Items");

				// JTextField

				// Set setting for InternalFrame Penjualan
				penjualan.setSize(371, 484);
				penjualan.setVisible(true);
				penjualan.setLocation(14, 53);
				penjualan.setResizable(false);

				// Table
				String[] columnNames1 = { "Item ID", "Item Name", "Quantity", "Price" };
				dtmPenjualan = new DefaultTableModel(columnNames1, 0) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};

				panggilSupervisor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String usernameInput = "";
						String passwordInput = "";
						String username = "";
						String password = "";
						boolean loginTest = true;
						do {
							usernameInput = JOptionPane.showInputDialog("Please input username");
							passwordInput = JOptionPane.showInputDialog("Please input password");
							String query = "SELECT * FROM staff WHERE staffUsername = '" + usernameInput
									+ "' AND staffPassword = '" + passwordInput + "' AND staffRole = 'Supervisor'";
							ResultSet rs = con.executeQuery(query);

							try {
								if (!(rs.next())) {
									JOptionPane.showMessageDialog(null, "Username / Password is Wrong", "Warning",
											JOptionPane.WARNING_MESSAGE);

								} else {

									dtmPenjualan.setRowCount(0);
									totalPrice = 0;
									totalHargaInput.setText(totalPrice + "");
									totalItemJual = 0;
									totalItemInput.setText(totalItemJual + "");
									refreshTable();
									loginTest = false;
								}

							} catch (Exception e2) {
								// TODO: handle exception
							}

						} while (loginTest);

					}
				});

				checkOut.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (dtmPenjualan.getRowCount() == 0) {
							JOptionPane.showMessageDialog(null, "Isi penjualan masih kosong", "Warning",
									JOptionPane.WARNING_MESSAGE);
						} else {
							con.insertPrepareStatementHeader(memberID.getText().toString(), user.getUserID());
							String query = "SELECT transactionID FROM headerTransaction ORDER BY transactionID DESC LIMIT 1";
							ResultSet rs = con.executeQuery(query);
							int headerTransactionID = 0;
							String inputUang = "";
							do {
								inputUang = JOptionPane.showInputDialog("Masukkan uang yang diberikan");
								if (Integer.parseInt(inputUang) < totalPrice) {
									JOptionPane.showMessageDialog(null, "Uang yang diberikan kurang.", "Warning",
											JOptionPane.WARNING_MESSAGE);
								}
							} while (Integer.parseInt(inputUang) < totalPrice);
							try {
								while (rs.next()) {
									headerTransactionID = rs.getInt(1);
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							for (int i = 0; i < dtmPenjualan.getRowCount(); i++) {
								int itemId = Integer.parseInt(dtmPenjualan.getValueAt(i, 0).toString());
								int qty = Integer.parseInt(dtmPenjualan.getValueAt(i, 2).toString());
								con.insertPrepareStatementDetail(headerTransactionID, itemId, qty);
								String query1 = "SELECT itemQuantity FROM item WHERE itemID ='" + itemId + "'";

								ResultSet rs1 = con.executeQuery(query1);
								int quantityItem = 0;

								try {
									while (rs1.next()) {
										quantityItem = rs1.getInt(1);

									}
								} catch (Exception e2) {
									// TODO: handle exception
								}

								con.updatePrepareStatement(quantityItem - qty, itemId);
							}

							refreshTable();
							dtmPenjualan.setRowCount(0);
							totalSalesOperator += totalPrice;
							JOptionPane.showMessageDialog(null,
									"Berhasil " + (Integer.parseInt(inputUang) - totalPrice), "Warning",
									JOptionPane.WARNING_MESSAGE);
							totalPrice = 0;
							totalItemJual = 0;
							totalHargaInput.setText("");
							totalItemInput.setText("");

						}

					}

				});

				tabelPenjualan = new JTable(dtmPenjualan);
				tabelPenjualan.setFillsViewportHeight(true);
				tabelPenjualan.getTableHeader().setReorderingAllowed(false);
				JScrollPane scrollPane1 = new JScrollPane(tabelPenjualan);

				// Add ke dalam southPanel
				southPanel.add(totalHarga);
				southPanel.add(totalHargaInput);
				southPanel.add(totalItem);
				southPanel.add(totalItemInput);
				southPanel.add(panggilSupervisor);

				// Add ke dalam JInternalFrame
				penjualan.add(scrollPane1, BorderLayout.CENTER);
				penjualan.add(southPanel, BorderLayout.SOUTH);
				memberID.setText("");
				desktopPane.add(penjualan);
				desktopPane.add(itemList);

			}
		});
		i4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {

				String usernameInput = "";
				String passwordInput = "";
				String username = "";
				String password = "";
				boolean loginTest = true;
				do {
					usernameInput = JOptionPane.showInputDialog("Please input username");
					passwordInput = JOptionPane.showInputDialog("Please input password");
					String query = "SELECT * FROM staff WHERE staffUsername = '" + usernameInput
							+ "' AND staffPassword = '" + passwordInput + "' AND staffRole = 'Supervisor'";
					ResultSet rs = con.executeQuery(query);

					try {
						if (!(rs.next())) {
							JOptionPane.showMessageDialog(null, "Username / Password is Wrong", "Warning",
									JOptionPane.WARNING_MESSAGE);

						} else {

							JOptionPane.showMessageDialog(null,
									"Total penjualan yang didapatkan oleh operator adalah " + totalSalesOperator,
									"Warning", JOptionPane.WARNING_MESSAGE);
							dispose();
							new Login();
							loginTest = false;
						}

					} catch (Exception e2) {
						// TODO: handle exception
					}

				} while (loginTest);

			}

		});

		updateStock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				if (!user.getRole().equals("Supervisor")) {
					JOptionPane.showMessageDialog(null, "Hanya operator yang bisa mengakses", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					dispose();
					new UpdateStock(user);
				}
			}

		});
		add(desktopPane);
		revalidate();

	}

	public void refreshTable() {
		dtm.setRowCount(0);
		String query = "SELECT * FROM item";
		ResultSet res = con.executeQuery(query);

		try {
			while (res.next()) {
				dtm.addRow(new Object[] { res.getObject(1), res.getObject(2), res.getObject(3), res.getObject(4), });

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void frameSettings() {
		setTitle("Sales Entry");
		setVisible(true);
		setSize(900, 600);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
	}

}
