import java.sql.*;

public class Connect {
	public Connection connection;
	public Statement statement;
	public ResultSet resultSet;
	public ResultSetMetaData resultSetMetaData;
	public PreparedStatement preparedStatement;

	public Connect() {
		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/uasbad", "root", "");
			statement = connection.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String query) {
		resultSet = null;
		try {
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;

	}

	public void insertPrepareStatementHeader(String memberID, int staffID) {
		try {
			preparedStatement = connection
					.prepareStatement("INSERT INTO `headertransaction`(`memberID`, `staffID`) " + "VALUES (?,?)");
			preparedStatement.setString(1, memberID);
			preparedStatement.setInt(2, staffID);
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertPrepareStatementDetail(int headerTransaction, int itemID, int qty) {
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO `detailtransaction`" + "VALUES (?,?,?)");
			preparedStatement.setInt(1, headerTransaction);
			preparedStatement.setInt(2, itemID);
			preparedStatement.setInt(3, qty);
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updatePrepareStatement(int itemQuantity, int itemID) {
		try {
			preparedStatement = connection.prepareStatement("UPDATE `item` SET `itemQuantity`=? " + "WHERE itemID =?");
			preparedStatement.setInt(1, itemQuantity);
			preparedStatement.setInt(2, itemID);
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
