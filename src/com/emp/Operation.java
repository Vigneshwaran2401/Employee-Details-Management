package com.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Operation {
	private static PreparedStatement ps;
	private static Statement st;
	private static final Connection con = DBCon.getDB();
	private static final Scanner sc = new Scanner(System.in);

	static void add() {
		try {
			System.out.println("Enter id");
			String id = sc.nextLine();
			System.out.println("Enter name");
			String name = sc.nextLine();
			System.out.println("Enter education");
			String education = sc.nextLine();
			System.out.println("Enter team");
			String team = sc.nextLine();
			System.out.println("Enter role");
			String role = sc.nextLine();
			System.out.println("Enter salary");
			String salary = sc.nextLine();
			System.out.println("Enter mentor");
			String mentor = sc.nextLine();
			String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, education);
			ps.setString(4, team);
			ps.setString(5, role);
			ps.setString(6, salary);
			ps.setString(7, mentor);
			int rows = ps.executeUpdate();
			if (rows > 0)
				System.out.println("Details registed successfully");
			else
				System.out.println("Details not registed successfully");
		} catch (SQLException e) {
			System.out.println("Error occurred while adding employee: " + e.getMessage());
		}finally {
			sc.nextLine();
		}
	}

	static void show() {
		try {
			st = con.createStatement();
			ResultSet resultSet = st.executeQuery("SELECT * FROM employee");
			int columnCount = resultSet.getMetaData().getColumnCount();
			printBorder(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				System.out.printf("| %-18s", resultSet.getMetaData().getColumnName(i));
			}
			System.out.println();
			printBorder(columnCount);
			while (resultSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					System.out.printf("| %-18s", resultSet.getString(i));
				}
				System.out.println();
				printBorder(columnCount);
			}
		} catch (SQLException e) {
			System.out.println("Error occurred while adding employee: " + e.getMessage());
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					System.out.println("Error occurred while closing statement: " + e.getMessage());
				}
			}
		}
	}

	static void delete(String id) {
		String deleteQuery = "DELETE FROM employee WHERE id = ?";
		try (PreparedStatement ps = con.prepareStatement(deleteQuery)) {
			ps.setString(1, id);
			int rowsDeleted = ps.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("A row has been deleted!");
			} else {
				System.out.println("No rows have been deleted.");
			}
		} catch (SQLException e) {
			System.out.println("Error occurred while deleting employee: " + e.getMessage());
		}
	}

	static void update(String id, String col, String value) {
		String updateQuery = "UPDATE employee SET " + col + " = ? WHERE id = ?";
		try {
			ps = con.prepareStatement(updateQuery);
			ps.setString(1, value);
			ps.setString(2, id);
			int rowsUpdated = ps.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("A row has been Updated!");
			} else {
				System.out.println("No rows have been Updated.");
			}
		} catch (SQLException e) {
			System.out.println("Error occurred while deleting employee: " + e.getMessage());
		}
	}

	static void search(String element, String value) {
		String searchQuery = "SELECT * FROM employee WHERE " + element + " = ?";
		try {
			ps = con.prepareStatement(searchQuery);
			ps.setString(1, value);
			ResultSet resultSet = ps.executeQuery();
			int columnCount = resultSet.getMetaData().getColumnCount();
			printBorder(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				System.out.printf("| %-18s", resultSet.getMetaData().getColumnName(i));
			}
			System.out.println();
			printBorder(columnCount);
			while (resultSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					System.out.printf("| %-18s", resultSet.getString(i));
				}
				System.out.println();
				printBorder(columnCount);
			}
		} catch (SQLException e) {
			System.out.println("Error occurred while deleting employee: " + e.getMessage());
		}
	}

	private static void printBorder(int columnCount) {
		for (int i = 1; i <= columnCount; i++) {
			System.out.print("+-------------------");
		}
		System.out.println("+");
	}
}
