import javax.swing.*;
import java.sql.*;

public class HotelMenuApp {

    static Connection con;

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hotel_db",
                "root",
                "root"
            );

            showMenu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showMenu() {
        JFrame frame = new JFrame("Hotel Reservation System");
        frame.setSize(400, 400);
        frame.setLayout(null);

        JButton addBtn = new JButton("Add Reservation");
        JButton viewBtn = new JButton("View Reservations");
        JButton updateBtn = new JButton("Update Reservation");
        JButton deleteBtn = new JButton("Delete Reservation");

        addBtn.setBounds(100, 50, 200, 30);
        viewBtn.setBounds(100, 100, 200, 30);
        updateBtn.setBounds(100, 150, 200, 30);
        deleteBtn.setBounds(100, 200, 200, 30);

        frame.add(addBtn);
        frame.add(viewBtn);
        frame.add(updateBtn);
        frame.add(deleteBtn);

        frame.setVisible(true);

        addBtn.addActionListener(e -> addReservation());
        viewBtn.addActionListener(e -> viewReservations());
        updateBtn.addActionListener(e -> updateReservation());
        deleteBtn.addActionListener(e -> deleteReservation());
    }

    static void addReservation() {
        String name = JOptionPane.showInputDialog("Guest Name");
        String room = JOptionPane.showInputDialog("Room Number");
        String contact = JOptionPane.showInputDialog("Contact");
        String type = JOptionPane.showInputDialog("Room Type");
        String date = JOptionPane.showInputDialog("Check-in Date");

        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO reservations(guest_name,room_number,contact,room_type,check_in_date) VALUES(?,?,?,?,?)"
            );

            ps.setString(1, name);
            ps.setInt(2, Integer.parseInt(room));
            ps.setString(3, contact);
            ps.setString(4, type);
            ps.setString(5, date);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Reservation Added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void viewReservations() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM reservations");

            String data = "";

            while (rs.next()) {
                data += rs.getInt("id") + " | "
                      + rs.getString("guest_name") + " | "
                      + rs.getInt("room_number") + " | "
                      + rs.getString("contact") + " | "
                      + rs.getString("room_type") + " | "
                      + rs.getString("check_in_date") + "\n";
            }

            JOptionPane.showMessageDialog(null, data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void updateReservation() {
        String id = JOptionPane.showInputDialog("Enter ID");
        String name = JOptionPane.showInputDialog("Guest Name");
        String room = JOptionPane.showInputDialog("Room Number");
        String contact = JOptionPane.showInputDialog("Contact");
        String type = JOptionPane.showInputDialog("Room Type");
        String date = JOptionPane.showInputDialog("Check-in Date");

        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE reservations SET guest_name=?, room_number=?, contact=?, room_type=?, check_in_date=? WHERE id=?"
            );

            ps.setString(1, name);
            ps.setInt(2, Integer.parseInt(room));
            ps.setString(3, contact);
            ps.setString(4, type);
            ps.setString(5, date);
            ps.setInt(6, Integer.parseInt(id));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteReservation() {
        String id = JOptionPane.showInputDialog("Enter ID");

        try {
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM reservations WHERE id=?"
            );

            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Deleted!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}