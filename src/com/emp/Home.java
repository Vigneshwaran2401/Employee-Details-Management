package com.emp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Home {
	private static final String CONFIG_FILE_PATH = "resources/config.properties";
	private static String ADMIN_USERNAME;
	private static String ADMIN_PASSWORD;
	private static boolean loggedIn = false;
	private static final Scanner sc = new Scanner(System.in);
	static {
		Properties p = new Properties();
		try (FileInputStream path = new FileInputStream(CONFIG_FILE_PATH)) {
			p.load(path);
			ADMIN_USERNAME = p.getProperty("admin.user");
			ADMIN_PASSWORD = p.getProperty("admin.password");
		} catch (FileNotFoundException e) {
			System.out.println("Error: Configuration file not found - " + e.getMessage());
			throw new RuntimeException("Configuration file not found", e);
		} catch (IOException e) {
			System.out.println("Error: Failed to load configuration file - " + e.getMessage());
			throw new RuntimeException("Failed to load configuration file", e);
		}
	}

	public static void main(String[] args) {
		try {
			login();
			if (loggedIn) {
				displayWelcomeMessage();
				while (true) {
					printMenu();
					String option = sc.nextLine();
					switch (option) {
					case "1":
						Operation.show();
						break;
					case "2":
						Operation.add();
						break;
					case "3": {
						Operation.update(getUserInput("Enter the id:"), getUserInput("Enter the column name:"),
								getUserInput("Enter the value:"));
						break;
					}
					case "4": {
						Operation.delete(getUserInput("Enter the id:"));
						break;
					}
					case "5": {
						searchEmployee();
						break;
					}
					case "6": {
						System.out.println("Your are succuessfully logged out!!");
						return;
					}
					default:
						System.out.println("Kindly, Check the option is correct!");
						break;
					}
				}
			} else {
				System.out.println("Invalid credentials. Exiting...");
			}
		} catch (Exception e) {
			System.out.println("An unexpected error occurred: " + e.getMessage());
		} finally {
			sc.close();
		}
	}

	private static void login() {
		System.out.println("Employee Management System - Login");
		System.out.print("Enter username: ");
		String username = sc.nextLine();
		System.out.print("Enter password: ");
		String password = sc.nextLine();

		if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
			loggedIn = true;
			System.out.println("Login successful. Welcome, " + ADMIN_USERNAME + "!");
		}
	}

	private static String getUserInput(String prompt) {
		System.out.println(prompt);
		return sc.nextLine();
	}

	private static void searchEmployee() {
		System.out.println("Search Employee");
		System.out.println("1) By name");
		System.out.println("2) By id");
		System.out.println("3) By team");
		String searchOption = sc.nextLine();

		switch (searchOption) {
		case "1":
			Operation.search("name", getUserInput("Enter the name: "));

			break;
		case "2":
			Operation.search("id", getUserInput("Enter the id: "));
			break;
		case "3":
			Operation.search("team", getUserInput("Enter the team: "));
			break;
		default:
			System.out.println("Invalid search option. Please try again.");
			break;
		}
	}
	
	private static void displayWelcomeMessage() {
        System.out.println("***********************************");
        System.out.println("*  Welcome to Best Corporate!     *");
        System.out.println("***********************************");
        System.out.println();
    }
	
	private static void printMenu() {
        System.out.println("===================================");
        System.out.println("            Main Menu              ");
        System.out.println("===================================");
        System.out.println("1) Show Employee Details");
        System.out.println("2) Add a New Employee");
        System.out.println("3) Update Employee Details");
        System.out.println("4) Delete Employee Details");
        System.out.println("5) Search for an Employee");
        System.out.println("6) Exit");
        System.out.println("-----------------------------------");
        System.out.print("Enter your choice: ");
    }
}