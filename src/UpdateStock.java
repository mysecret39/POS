import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UpdateStock extends JFrame implements MouseListener {
	JTable tabel;
	DefaultTableModel dtm;
	JLabel itemIdLabel, itemHargaLabel, itemQuantityLabel, itemNameLabel;
	JTextField itemIdText, itemHargaText, itemQuantityText, itemNameText;
	Connect con = new Connect();
	JButton updateStockButton, back;
	User user;

	public UpdateStock(User user) {
		this.user = user;
		frameSettings();
		JPanel kananPanel = new JPanel();
		JPanel kiriPanel = new JPanel(new GridLayout(2, 1));
		JPanel buttonPanel = new JPanel();
		itemIdText = new JTextField();
		itemHargaText = new JTextField();
		itemQuantityText = new JTextField();
		itemNameText = new JTextField();
		itemHargaText.setEditable(false);
		itemNameText.setEditable(false);
		itemIdText.setEditable(false);
		back = new JButton("Back");
		updateStockButton = new JButton("Update Stock");

		itemIdLabel = new JLabel("Item ID");
		itemNameLabel = new JLabel("Item Nama");
		itemQuantityLabel = new JLabel("Quantity");
		itemHargaLabel = new JLabel("Harga");
		String[] columnNames = { "Item ID", "Item Name", "Quantity", "Price" };
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
		kananPanel.add(scrollPane);
		kiriPanel.add(itemIdLabel);
		kiriPanel.add(itemIdText);
		kiriPanel.add(itemNameLabel);
		kiriPanel.add(itemNameText);
		kiriPanel.add(itemQuantityLabel);
		kiriPanel.add(itemQuantityText);
		kiriPanel.add(itemHargaLabel);
		kiriPanel.add(itemHargaText);

		buttonPanel.add(updateStockButton);
		buttonPanel.add(back);
		add(kananPanel, BorderLayout.NORTH);
		add(kiriPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		tabel.addMouseListener(this);
		updateStockButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				con.updatePrepareStatement(Integer.parseInt(itemQuantityText.getText().toString()),
						Integer.parseInt(itemIdText.getText().toString()));
				JOptionPane.showMessageDialog(null, "Stock berhasil di update!", "Warning",
						JOptionPane.WARNING_MESSAGE);
				refreshTable();
			}

		});

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				dispose();
				new SalesEntry(user);
			}

		});
	}

	public void frameSettings() {
		setTitle("Update Stock");
		setVisible(true);
		setSize(900, 600);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
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

	@Override
	public void mouseClicked(MouseEvent e) {

		int row = tabel.getSelectedRow();

		try {
			itemIdText.setText(dtm.getValueAt(row, 0).toString());
			itemNameText.setText(dtm.getValueAt(row, 1).toString());
			itemQuantityText.setText(dtm.getValueAt(row, 2).toString());
			itemHargaText.setText(dtm.getValueAt(row, 3).toString());
		} catch (Exception e2) {

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
