package Task7;

import java.sql.*;
import java.util.Scanner;

public class Employee_Database {

    private String Employee_Name;
    private int Employee_ID;
    private String Employee_Department;

    public Employee_Database(String employee_Name, int employee_ID, String employee_Department) {
        Employee_Name = employee_Name;
        Employee_ID = employee_ID;
        Employee_Department = employee_Department;
    }

    public void AddEmployee(Connection conn, Scanner sc) {
        try {
            System.out.println("Enter Employee Name: ");
            String name = sc.nextLine();

            System.out.println("Enter Employee Department: ");
            String dept = sc.nextLine();

            String sql = "INSERT INTO employees (name, department) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, dept);
                ps.executeUpdate();
                System.out.println("Employee added successfully!");
            }
        } catch (Exception e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }


    public void DeleteEmployee(Connection conn) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter Employee ID: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM employees WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Employee deleted successfully ");
                } else {
                    System.out.println("No employee found with that ID ");
                }
            }
        } catch (Exception e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }

    public void UpdateEmployee(Connection conn) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter Employee ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.println("Enter  Name: ");
            String name = sc.nextLine();

            System.out.println("Enter Department: ");
            String dept = sc.nextLine();

            String sql = "UPDATE employees SET name = ?, department = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, dept);
                ps.setInt(3, id);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Employee updated successfully!");
                } else {
                    System.out.println("No employee found with that ID.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }

    public void ShowEmployeeList(Connection conn) {
        String sql = "SELECT * FROM employees";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("Employee List:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Department: " + rs.getString("department"));
            }
        } catch (Exception e) {
            System.out.println("Error showing list: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/employee_database";
        String username = "root";
        String password = "12345";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to database");

            Employee_Database app = new Employee_Database("", 0, "");
            Scanner sc = new Scanner(System.in);


                while (true) {
                    System.out.println("\nAdd -  Employee");
                    System.out.println("View -  Employees");
                    System.out.println("Update - e Employee");
                    System.out.println("Delete -  Employee");
                    System.out.println("Exit -   Program");
                    System.out.print("Enter choice: ");
                    String choice = sc.nextLine().trim();

                    if (choice.equalsIgnoreCase("add")) {
                        app.AddEmployee(conn,sc);
                    } else if (choice.equalsIgnoreCase("view")) {
                        app.ShowEmployeeList(conn);
                    } else if (choice.equalsIgnoreCase("update")) {
                        app.UpdateEmployee(conn);
                    } else if (choice.equalsIgnoreCase("delete")) {
                        app.DeleteEmployee(conn);
                    } else if (choice.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting ");
                        break;
                    } else {
                        System.out.println("Invalid choice plz Try Again ");
                    }
                }
            } catch (Exception e) {
                System.err.println("Connection Failed: " + e.getMessage());
            }
        }

    }

