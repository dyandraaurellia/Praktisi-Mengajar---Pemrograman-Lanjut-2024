import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        setTitle("LOG IN PERPUSTAKAAN");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension(250, 400));
        leftPanel.setBackground(new Color(22, 160, 133));

        ImageIcon imageIcon = new ImageIcon("img/buku_gui.png"); 
        JLabel imageLabel = new JLabel(imageIcon);
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.insets = new Insets(20, 20, 20, 20); 
        leftPanel.add(imageLabel, gbcLeft);

        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("LOG IN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        rightPanel.add(new JLabel("Full name:"), gbc);
        JTextField fullNameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rightPanel.add(fullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        rightPanel.add(new JLabel("Email:"), gbc);
        JTextField emailField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rightPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        rightPanel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rightPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = fullNameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("dyandra") && password.equals("123")) {
                    LibraryGUI libraryGUI = new LibraryGUI();
                    libraryGUI.setVisible(true);
                    dispose();  
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridy = 5;
        rightPanel.add(loginButton, gbc);

        add(rightPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginPage().setVisible(true);
            }
        });
    }
}

class LibraryGUI extends JFrame {
    private LibraryManager<Book> bookManager;
    private LibraryManager<Student> studentManager;

    private JComboBox<Book> bookComboBox;
    private JComboBox<Student> studentComboBox;

    private JTable bookTable;
    private JTable studentTable;

    public LibraryGUI() {
        bookManager = new LibraryManager<>();
        studentManager = new LibraryManager<>();
  

        setTitle("Library System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(createBorrowPanel());
        panel.add(createDisplayPanel());

        add(panel, BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        updateBookTable();
        updateStudentTable();
    }

    private JPanel createBorrowPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2)); 

        panel.add(new JLabel("Select Book:"));
        bookComboBox = new JComboBox<>(bookManager.getAllItems().toArray(new Book[0]));
        panel.add(bookComboBox);

        panel.add(new JLabel("Select Student:"));
        studentComboBox = new JComboBox<>(studentManager.getAllItems().toArray(new Student[0]));
        panel.add(studentComboBox);

        JButton borrowButton = new JButton("Borrow Book");
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Book selectedBook = (Book) bookComboBox.getSelectedItem();
                    Student selectedStudent = (Student) studentComboBox.getSelectedItem();

                    if (selectedBook != null && selectedStudent != null) {
                        if (selectedBook.isBorrowed()) {
                            throw new Exception("The book is already borrowed.");
                        }
                        selectedBook.setBorrowed(true);
                        saveDataToFile(selectedBook, selectedStudent);
                        updateBookTable();
                        JOptionPane.showMessageDialog(null, "Book borrowed successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a book and a student", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(borrowButton);

        JButton returnButton = new JButton("Return Book");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Book selectedBook = (Book) bookComboBox.getSelectedItem();
                    if (selectedBook != null && selectedBook.isBorrowed()) {
                        selectedBook.setBorrowed(false);
                        updateBookTable();
                        JOptionPane.showMessageDialog(null, "Book returned successfully");
                    } else {
                        throw new Exception("The book is not borrowed or no book selected.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(returnButton);

        return panel;
    }

    private JPanel createDisplayPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));

        bookTable = new JTable(new DefaultTableModel(new Object[]{"ID", "Title", "Borrowed"}, 0));
        studentTable = new JTable(new DefaultTableModel(new Object[]{"ID", "Name"}, 0));

        panel.add(new JScrollPane(bookTable));
        panel.add(new JScrollPane(studentTable));

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookId = JOptionPane.showInputDialog("Enter Book ID:");
                String bookTitle = JOptionPane.showInputDialog("Enter Book Title:");
                if (bookId != null && bookTitle != null) {
                    bookManager.addItem(bookId, new Book(bookId, bookTitle));
                    updateBookTable();
                }
            }
        });

        JButton addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = JOptionPane.showInputDialog("Enter Student ID:");
                String studentName = JOptionPane.showInputDialog("Enter Student Name:");
                if (studentId != null && studentName != null) {
                    studentManager.addItem(studentId, new Student(studentId, studentName));
                    updateStudentTable();
                }
            }
        });

        JButton saveDataButton = new JButton("Save Data");
        saveDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileManager.writeToFile("books.txt", bookManager.getAllItems());
                    FileManager.writeToFile("students.txt", studentManager.getAllItems());
                    JOptionPane.showMessageDialog(null, "Data saved successfully");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to save data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(addBookButton);
        panel.add(addStudentButton);
        panel.add(saveDataButton);
        panel.add(resetButton);
        panel.add(exitButton);

        return panel;
    }

    private void resetFields() {
        bookComboBox.setSelectedIndex(0);
        studentComboBox.setSelectedIndex(0);
    }

    private void updateBookTable() {
        DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
        model.setRowCount(0);
        for (Book book : bookManager.getAllItems()) {
            model.addRow(new Object[]{book.getId(), book.getTitle(), book.isBorrowed()});
        }
        bookComboBox.setModel(new DefaultComboBoxModel<>(bookManager.getAllItems().toArray(new Book[0])));
    }

    private void updateStudentTable() {
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        model.setRowCount(0);
        for (Student student : studentManager.getAllItems()) {
            model.addRow(new Object[]{student.getId(), student.getName()});
        }
        studentComboBox.setModel(new DefaultComboBoxModel<>(studentManager.getAllItems().toArray(new Student[0])));
    }

    private void saveDataToFile(Book book, Student student) {
        String filename = "borrowed_books.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println("Book: " + book.getTitle() + ", Student: " + student.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Book implements Serializable {
    private String id;
    private String title;
    private boolean borrowed;

    public Book(String id, String title) {
        this.id = id;
        this.title = title;
        this.borrowed = false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    @Override
    public String toString() {
        return title;
    }
}

class Student implements Serializable {
    private String id;
    private String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

class LibraryManager<T> {
    private HashMap<String, T> items;

    public LibraryManager() {
        items = new HashMap<>();
    }

    public void addItem(String id, T item) {
        items.put(id, item);
    }

    public T getItem(String id) {
        return items.get(id);
    }

    public ArrayList<T> getAllItems() {
        return new ArrayList<>(items.values());
    }

    public void removeItem(String id) {
        items.remove(id);
    }
}

class FileManager {
    public static <T> void writeToFile(String filename, ArrayList<T> items) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(items);
        }
    }

    public static <T> ArrayList<T> readFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<T>) ois.readObject();
        }
    }
}
