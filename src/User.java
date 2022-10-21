public class User {
	private int userID;
	private String role;

	User(int userID, String role) {
		this.userID = userID;
		this.role = role;
	}

	public int getUserID() {
		return userID;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

}
