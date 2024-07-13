
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ComputerLaboratoryFrame extends JFrame {
    // Define components
    private JPanel studentPanel;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField studentNameField;
    private JTextField studentNumberField;
    private JTextField test1Field;
    private JTextField test2Field;
    private JTextField assignmentField;
    private JTextField presentationField;
    private JTextField semesterMarkField;
    private JTextField examMarkField;
    private JTextField finalMarkField;
    private JButton addStudentButton;
    private JButton calculateSemesterButton;
    private JButton calculateFinalButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton logoutButton;
    private JButton downloadButton;
    private JButton clearButton;

    public ComputerLaboratoryFrame() {
        setTitle("Computer Laboratory");
        // Set initial size (optional)
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose frame when closed
        setLocationRelativeTo(null);

        // Maximize the window on startup
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Title label
        JLabel titleLabel = new JLabel("Welcome to Computer Laboratory");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Initialize table model with columns
        String[] columnNames = { "Student Name", "Student Number", "Test 1", "Test 2", "Assignment", "Presentation",
                "Semester Mark", "Exam Mark", "Final Mark" };
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(studentTable);

        // Panel for student list
        studentPanel = new JPanel(new BorderLayout());
        studentPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel for marks input and display
        JPanel marksPanel = new JPanel(new GridLayout(15, 2));

        // Labels and fields for marks input and display
        JLabel studentNameLabel = new JLabel("Student Name:");
        studentNameField = new JTextField();

        JLabel studentNumberLabel = new JLabel("Student Number:");
        studentNumberField = new JTextField();

        JLabel test1Label = new JLabel("Enter Test 1 Mark:");
        test1Field = new JTextField();

        JLabel test2Label = new JLabel("Enter Test 2 Mark:");
        test2Field = new JTextField();

        JLabel assignmentLabel = new JLabel("Enter Assignment Mark:");
        assignmentField = new JTextField();

        JLabel presentationLabel = new JLabel("Enter Presentation Mark:");
        presentationField = new JTextField();

        JLabel semesterLabel = new JLabel("Semester Mark:");
        semesterMarkField = new JTextField();
        semesterMarkField.setEditable(false);

        JLabel examLabel = new JLabel("Enter Exam Mark:");
        examMarkField = new JTextField();

        JLabel finalLabel = new JLabel("Final Mark:");
        finalMarkField = new JTextField();
        finalMarkField.setEditable(false);

        // Button to add a student
        addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(e -> addStudent());

        // Button to calculate semester mark
        calculateSemesterButton = new JButton("Calculate Semester Mark");
        calculateSemesterButton.addActionListener(e -> calculateSemesterMark());

        // Button to calculate final mark
        calculateFinalButton = new JButton("Calculate Final Mark");
        calculateFinalButton.addActionListener(e -> calculateFinalMark());

        // Buttons for edit and delete
        editButton = new JButton("UPDATE");
        editButton.setBackground(Color.ORANGE); // Set background color
        editButton.setForeground(Color.BLACK); // Set text color
        editButton.setFont(new Font("Arial", Font.BOLD, 12)); // Set font and size
        editButton.addActionListener(e -> editStudent());

        deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.RED); // Set background color
        deleteButton.setForeground(Color.WHITE); // Set text color
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12)); // Set font and size
        deleteButton.addActionListener(e -> deleteStudent());

        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.GRAY); // Set background color
        logoutButton.setForeground(Color.WHITE); // Set text color
        logoutButton.setFont(new Font("Arial", Font.BOLD, 12)); // Set font and size
        logoutButton.addActionListener(e -> logout());

        // Download button
        downloadButton = new JButton("Download Report");
        downloadButton.setBackground(Color.BLUE); // Set background color
        downloadButton.setForeground(Color.WHITE); // Set text color
        downloadButton.setFont(new Font("Arial", Font.BOLD, 12)); // Set font and size
        downloadButton.addActionListener(e -> downloadReport());

        // Clear button
        clearButton = new JButton("Clear");
        clearButton.setBackground(Color.GREEN); // Set background color
        clearButton.setForeground(Color.WHITE); // Set text color
        clearButton.setFont(new Font("Arial", Font.BOLD, 12)); // Set font and size
        clearButton.addActionListener(e -> clearInputFields());

        // Add components to marks panel
        marksPanel.add(studentNameLabel);
        marksPanel.add(studentNameField);
        marksPanel.add(studentNumberLabel);
        marksPanel.add(studentNumberField);
        marksPanel.add(addStudentButton);
        marksPanel.add(new JLabel()); // Empty cell to align the button
        marksPanel.add(test1Label);
        marksPanel.add(test1Field);
        marksPanel.add(test2Label);
        marksPanel.add(test2Field);
        marksPanel.add(assignmentLabel);
        marksPanel.add(assignmentField);
        marksPanel.add(presentationLabel);
        marksPanel.add(presentationField);
        marksPanel.add(calculateSemesterButton);
        marksPanel.add(semesterMarkField);
        marksPanel.add(examLabel);
        marksPanel.add(examMarkField);
        marksPanel.add(calculateFinalButton);
        marksPanel.add(finalMarkField);

        // Create a new panel for the buttons at the bottom
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomButtonPanel.add(editButton);
        bottomButtonPanel.add(deleteButton);
        bottomButtonPanel.add(logoutButton);
        bottomButtonPanel.add(downloadButton);
        bottomButtonPanel.add(clearButton);

        // Layout for main frame
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(studentPanel, BorderLayout.CENTER);
        add(marksPanel, BorderLayout.EAST);
        add(bottomButtonPanel, BorderLayout.SOUTH); // Add the new panel at the bottom

        // Pack the frame to ensure preferred sizes are respected initially
        pack();

        // Load student data
        loadStudentData();

        // Save data when the frame is closed
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveStudentData();
            }
        });
    }

    // Method to add a student
    private void addStudent() {
        String studentName = studentNameField.getText();
        String studentNumber = studentNumberField.getText();

        if (studentName.isEmpty() || studentNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both student name and student number.");
        } else {
            // Add the new student to the table
            tableModel.addRow(new Object[] { studentName, studentNumber, "", "", "", "", "", "", "" });

            // Clear the input fields after adding
            clearInputFields();
        }
    }

    // Method to calculate semester mark
    private void calculateSemesterMark() {
        try {
            double test1 = Double.parseDouble(test1Field.getText());
            double test2 = Double.parseDouble(test2Field.getText());
            double assignment = Double.parseDouble(assignmentField.getText());
            double presentation = Double.parseDouble(presentationField.getText());

            // Calculate semester mark (example calculation)
            double semesterMark = (test1 + test2 + assignment + presentation) / 4;

            // Display semester mark
            semesterMarkField.setText(String.format("%.2f", semesterMark)); // Display formatted semester mark
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric marks.");
        }
    }

    // Method to calculate final mark
    private void calculateFinalMark() {
        try {
            double semesterMark = Double.parseDouble(semesterMarkField.getText());
            double examMark = Double.parseDouble(examMarkField.getText());

            // Calculate final mark (example calculation)
            double finalMark = 0.6 * semesterMark + 0.4 * examMark;

            // Display final mark
            finalMarkField.setText(String.format("%.2f", finalMark)); // Display formatted final mark
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric marks.");
        }
    }

    // Method to clear input fields
    private void clearInputFields() {
        studentNameField.setText("");
        studentNumberField.setText("");
        test1Field.setText("");
        test2Field.setText("");
        assignmentField.setText("");
        presentationField.setText("");
        semesterMarkField.setText("");
        examMarkField.setText("");
        finalMarkField.setText("");
    }

    // Method to edit student marks
    private void editStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.");
            return;
        }

        String test1 = test1Field.getText();
        String test2 = test2Field.getText();
        String assignment = assignmentField.getText();
        String presentation = presentationField.getText();
        String semesterMark = semesterMarkField.getText();
        String examMark = examMarkField.getText();
        String finalMark = finalMarkField.getText();

        tableModel.setValueAt(test1, selectedRow, 2);
        tableModel.setValueAt(test2, selectedRow, 3);
        tableModel.setValueAt(assignment, selectedRow, 4);
        tableModel.setValueAt(presentation, selectedRow, 5);
        tableModel.setValueAt(semesterMark, selectedRow, 6);
        tableModel.setValueAt(examMark, selectedRow, 7);
        tableModel.setValueAt(finalMark, selectedRow, 8);

        JOptionPane.showMessageDialog(this, "Student marks updated successfully.");
    }

    // Method to delete student
    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            return;
        }

        tableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "Student deleted successfully.");
    }

    // Method to logout
    private void logout() {
        dispose();

        // Open login frame
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }

    // Method to download report
    private void downloadReport() {
        try (FileWriter writer = new FileWriter("StudentReport.csv")) {
            // Write header
            writer.write(
                    "Student Name,Student Number,Test 1,Test 2,Assignment,Presentation,Semester Mark,Exam Mark,Final Mark\n");

            // Write student data
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    writer.write(tableModel.getValueAt(i, j).toString() + ",");
                }
                writer.write("\n");
            }

            JOptionPane.showMessageDialog(this, "Report downloaded successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error downloading report: " + e.getMessage());
        }
    }

    // Method to load student data
    private void loadStudentData() {
        File file = new File("StudentData.csv");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    tableModel.addRow(data);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading student data: " + e.getMessage());
            }
        }
    }

    // Method to save student data
    private void saveStudentData() {
        try (FileWriter writer = new FileWriter("StudentData.csv")) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    writer.write(tableModel.getValueAt(i, j).toString() + ",");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving student data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Create and display the frame
        SwingUtilities.invokeLater(() -> {
            ComputerLaboratoryFrame frame = new ComputerLaboratoryFrame();
            frame.setVisible(true);
        });
    }
}
