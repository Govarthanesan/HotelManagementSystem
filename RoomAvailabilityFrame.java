import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomAvailabilityFrame extends JFrame {

    private ArrayList<Staff> staffList;

    public RoomAvailabilityFrame() {
        setTitle("Room Availability and Pricing");
        setLayout(new GridLayout(4, 2));
        getContentPane().setBackground(Color.LIGHT_GRAY);

        JComboBox<String> roomTypeComboBox = new JComboBox<>();
        JComboBox<Double> pricingComboBox = new JComboBox<>();

        add(createLabel("Room Type: "));
        add(roomTypeComboBox);
        add(createLabel("Price Range: "));
        add(pricingComboBox);

        RoomAvailabilityController controller = new RoomAvailabilityController(roomTypeComboBox, pricingComboBox, this);

        JButton staffManageButton = new JButton("Staff Management");
        staffManageButton.setPreferredSize(new Dimension(150, 40));
        staffManageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStaffManagementFrame();
            }
        });
        add(staffManageButton);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton proceedButton = new JButton("Proceed");
        proceedButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(proceedButton);
        add(buttonPanel);

        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleProceedButtonClick();
            }
        });

        // Initialize staffList
        staffList = new ArrayList<>();
        staffList.add(new Staff("John Doe", "9:00 AM - 5:00 PM", "Front Desk"));
        staffList.add(new Staff("Jane Smith", "1:00 PM - 9:00 PM", "Housekeeping"));
        staffList.add(new Staff("Alice Johnson", "10:00 AM - 6:00 PM", "Housekeeping"));
        staffList.add(new Staff("Bob Williams", "2:00 PM - 10:00 PM", "Front Desk"));
        staffList.add(new Staff("Eva Davis", "9:00 AM - 5:00 PM", "Security"));
        staffList.add(new Staff("Mike Wilson", "3:00 PM - 11:00 PM", "Concierge"));
        staffList.add(new Staff("Olivia Smith", "11:00 PM - 7:00 AM", "Night Auditor"));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private void showStaffManagementFrame() {
        if (staffList != null && !staffList.isEmpty()) {
            StaffManagementFrame staffManagementFrame = new StaffManagementFrame(staffList);
            staffManagementFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No staff information available.");
        }
    }

    public void setStaffList(ArrayList<Staff> staffList) {
        this.staffList = staffList;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RoomAvailabilityFrame::new);
    }
}

class RoomAvailabilityController {
    private final JComboBox<String> roomTypeComboBox;
    private final JComboBox<Double> pricingComboBox;
    private final RoomAvailabilityFrame roomAvailabilityFrame;

    public RoomAvailabilityController(JComboBox<String> roomTypeComboBox, JComboBox<Double> pricingComboBox, RoomAvailabilityFrame roomAvailabilityFrame) {
        this.roomTypeComboBox = roomTypeComboBox;
        this.pricingComboBox = pricingComboBox;
        this.roomAvailabilityFrame = roomAvailabilityFrame;
        fetchData();
    }

    private void fetchData() {
        roomTypeComboBox.addItem("Single");
        roomTypeComboBox.addItem("Double");
        roomTypeComboBox.addItem("Deluxe");
        roomTypeComboBox.addItem("Suite");

        pricingComboBox.addItem(100.0);
        pricingComboBox.addItem(150.0);
        pricingComboBox.addItem(300.0);
        pricingComboBox.addItem(500.0);
    }

    public void handleProceedButtonClick() {
        String selectedRoomType = (String) roomTypeComboBox.getSelectedItem();
        double selectedPrice = (Double) pricingComboBox.getSelectedItem();

        GuestDetailsFrame detailsFrame = new GuestDetailsFrame(selectedRoomType, selectedPrice);
        detailsFrame.setVisible(true);
    }
}

class GuestDetailsFrame extends JFrame {
    // ... (unchanged)
    public GuestDetailsFrame(String selectedRoomType, double selectedPrice) {
        setTitle("Guest Details");
        setLayout(new GridLayout(9, 2));
        getContentPane().setBackground(Color.LIGHT_GRAY);

        JTextField nameTextField = createTextField();
        JTextField phoneTextField = createTextField();
        JTextField adharTextField = createTextField();
        JTextField stateTextField = createTextField();
        JTextField daysTextField = createTextField();
        JTextField gmailTextField = createTextField();
        JTextField mealsTextField = createTextField();
        JTextField registrationDetailsTextField = createTextField();

        add(createLabel("Name: "));
        add(nameTextField);
        add(createLabel("Phone Number: "));
        add(phoneTextField);
        add(createLabel("Adhar ID: "));
        add(adharTextField);
        add(createLabel("State: "));
        add(stateTextField);
        add(createLabel("Number of Days: "));
        add(daysTextField);
        add(createLabel("Gmail: "));
        add(gmailTextField);
        add(createLabel("Preferred Meals: "));
        add(mealsTextField);
        add(createLabel("Registration Details: "));
        add(registrationDetailsTextField);

        GuestDetailsController controller = new GuestDetailsController(
                selectedRoomType, selectedPrice, nameTextField, phoneTextField,
                adharTextField, stateTextField, daysTextField, gmailTextField,
                mealsTextField, registrationDetailsTextField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(submitButton);
        add(buttonPanel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleDetailsSubmit();
                setVisible(false);
                dispose();
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 750);
        setLocationRelativeTo(null);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        return textField;
    }
}

class GuestDetailsController {
    private final String selectedRoomType;
    private final double selectedPrice;
    private final JTextField nameTextField;
    private final JTextField phoneTextField;
    private final JTextField adharTextField;
    private final JTextField stateTextField;
    private final JTextField daysTextField;
    private final JTextField gmailTextField;
    private final JTextField mealsTextField;
    private final JTextField registrationDetailsTextField;

    public GuestDetailsController(String selectedRoomType, double selectedPrice,
                                  JTextField nameTextField, JTextField phoneTextField,
                                  JTextField adharTextField, JTextField stateTextField,
                                  JTextField daysTextField, JTextField gmailTextField,
                                  JTextField mealsTextField, JTextField registrationDetailsTextField) {
        this.selectedRoomType = selectedRoomType;
        this.selectedPrice = selectedPrice;
        this.nameTextField = nameTextField;
        this.phoneTextField = phoneTextField;
        this.adharTextField = adharTextField;
        this.stateTextField = stateTextField;
        this.daysTextField = daysTextField;
        this.gmailTextField = gmailTextField;
        this.mealsTextField = mealsTextField;
        this.registrationDetailsTextField = registrationDetailsTextField;
    }

    public void handleDetailsSubmit() {
        System.out.println("Room Type: " + selectedRoomType);
        System.out.println("Price: " + selectedPrice);
        System.out.println("Name: " + nameTextField.getText());
        System.out.println("Phone Number: " + phoneTextField.getText());
        System.out.println("Adhar ID: " + adharTextField.getText());
        System.out.println("State: " + stateTextField.getText());
        System.out.println("Number of Days: " + daysTextField.getText());
        System.out.println("Gmail: " + gmailTextField.getText());
        System.out.println("Preferred Meals: " + mealsTextField.getText());
        System.out.println("Registration Details: " + registrationDetailsTextField.getText());

        double totalAmount = Integer.parseInt(daysTextField.getText()) * selectedPrice;

        // Display total amount and details on a new JFrame
        BillDetailsFrame billDetailsFrame = new BillDetailsFrame(selectedRoomType, selectedPrice,
                nameTextField.getText(), phoneTextField.getText(), adharTextField.getText(),
                stateTextField.getText(), Integer.parseInt(daysTextField.getText()),
                gmailTextField.getText(), mealsTextField.getText(), registrationDetailsTextField.getText(),
                totalAmount);

        billDetailsFrame.setVisible(true);

        // Store details in the database
        DatabaseHandler.insertGuestDetails(nameTextField.getText(), phoneTextField.getText(), totalAmount);
    }
}

class BillDetailsFrame extends JFrame 
{
    // ... (unchanged)
    public BillDetailsFrame(String roomType, double roomPrice, String name, String phoneNumber,
                            String adharId, String state, int numberOfDays, String gmail,
                            String mealsPreference, String registrationDetails, double totalAmount) {
        setTitle("Bill Details");
        setLayout(new GridLayout(11, 2));
        getContentPane().setBackground(Color.LIGHT_GRAY);

        addLabelAndField("Room Type:", roomType);
        addLabelAndField("Room Price:", String.valueOf(roomPrice));
        addLabelAndField("Name:", name);
        addLabelAndField("Phone Number:", phoneNumber);
        addLabelAndField("Adhar ID:", adharId);
        addLabelAndField("State:", state);
        addLabelAndField("Number of Days:", String.valueOf(numberOfDays));
        addLabelAndField("Gmail:", gmail);
        addLabelAndField("Meals Preference:", mealsPreference);
        addLabelAndField("Registration Details:", registrationDetails);
        addLabelAndField("Total Amount:", String.valueOf(totalAmount));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 750);
        setLocationRelativeTo(null);
    }

    private void addLabelAndField(String labelText, String value) {
        JLabel label = createLabel(labelText);
        JTextField textField = createTextField();
        textField.setText(value);
        textField.setEditable(false); // Make the text field read-only
        add(label);
        add(textField);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        return textField;
    }
}

class Staff {
    private String name;
    private String timing;
    private String position;

    public Staff(String name, String timing, String position) {
        this.name = name;
        this.timing = timing;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getTiming() {
        return timing;
    }

    public String getPosition() {
        return position;
    }
}


class StaffManagementFrame extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;

    public StaffManagementFrame(ArrayList<Staff> staffList) {
        setTitle("Staff Management");
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);

        String[] columnNames = {"Staff Name", "Timing", "Position"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        updateStaffList(staffList);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
    }

    public void updateStaffList(ArrayList<Staff> staffList) {
        tableModel.setRowCount(0); // Clear existing rows

        for (Staff staff : staffList) {
            Object[] rowData = {staff.getName(), staff.getTiming(), staff.getPosition()};
            tableModel.addRow(rowData);
        }
    }
}

class StaffDutyFrame extends JFrame {

    public StaffDutyFrame() {
        setTitle("Staff Duty Roster");
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLUE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        // Add staff duty details (replace this with actual data)
        textArea.append("FRONT OFFICE AGENT\n");
        textArea.append("FOA 1\nFOA 2 FOA 3 FOA 4\n");
        textArea.append("CASHIER 1 CASHIER 2 CASHIER 3 BELL DESK STAFF 1\n");
        textArea.append("STAFF 2\n");
        textArea.append("STAFF 3\n");
        textArea.append("SECURITY 1 SECURITY 2 SECURITY 3 CONCIERGE NIGHT AUDITOR\n");
        textArea.append("Eva Davis Mike Wilson Olivia Smith\n");
        textArea.append("09:00-17:00 15:00-23:00 23:00-07:00 Weekly OFF Comp OFF\n");

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}

class DatabaseHandler {

    //private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hotel";
    //private static final String USER = "root";
    //private static final String PASSWORD = "root";
private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hotel";
private static final String USER = "root";
private static final String PASSWORD = "root";




    public static void insertGuestDetails(String name, String phoneNumber, double billAmount) {
        String query = "INSERT INTO tabel1 (name, phone_number, bill_amount) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.setDouble(3, billAmount);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}