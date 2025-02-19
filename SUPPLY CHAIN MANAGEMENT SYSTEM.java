CREATE TABLE products (
product_id INT AUTO_INCREMENT PRIMARY KEY,
product_name VARCHAR(255) NOT NULL,
supplier_id INT,
price DECIMAL(10, 2),
FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id)
);
CREATE TABLE suppliers (
supplier_id INT AUTO_INCREMENT
PRIMARY KEY, supplier_name
VARCHAR(255) NOT NULL, contact_info
VARCHAR(255)
);
CREATE TABLE orders (
order_id INT AUTO_INCREMENT PRIMARY KEY,
product_id
INT,
supplier_id
INT,
quantity
INT,
total_price DECIMAL(10, 2),
FOREIGN KEY (product_id) REFERENCES
products(product_id), FOREIGN KEY (supplier_id)
REFERENCES suppliers(supplier_id)
);
import java.sql.Connection;
import
java.sql.DriverManager;
import
java.sql.SQLException;
public class DatabaseConnection {
private static final String URL =
"jdbc:mysql://localhost:3306/supply_chain_db"; private static final
String USER = "root";
private static final String PASSWORD =
"your_password"; private static Connection
connection;
public static Connection connect() throws
17 | P a g e
SQLException { if (connection == null ||
connection.isClosed()) {
connection = DriverManager.getConnection(URL, USER, PASSWORD);
}
return connection;
}
public static void close() throws SQLException {
if (connection != null && !connection.isClosed()) {
connection.close();
}
}
}
import java.awt.event.ActionListener;
public class MainMenu extends
JFrame { public MainMenu() {
setTitle("Supply Chain Management System");
18 | P a g e
setSize(500,300);
setDefaultCloseOperation(JFrame.EXIT_ON
_CLOSE);setLayout(newGridLayout(4,1))
;
JLabel titleLabel = new JLabel("Supply Chain Management System App",
SwingConstants.CENTER); titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
add(titleLabel);
JButton productButton = new JButton("Manage
Products"); JButton supplierButton = new
JButton("Manage Suppliers"); JButton orderButton =
new JButton("Manage Orders");
productButton.setForeground(Color.BL
ACK);
supplierButton.setForeground(Color.BL
ACK);
orderButton.setForeground(Color.BL
ACK);
productButton.addActionListener(e -> new
ProductFrame().setVisible(true));
supplierButton.addActionListener(e -> new
SupplierFrame().setVisible(true));
orderButton.addActionListener(e -> new
OrderFrame().setVisible(true));
add(productButt
on);
add(supplierButto
n);
add(orderButton
);
}
public static void main(String[] args) {
SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
}
}
import javax.swing.*;
import java.awt.*;
import
java.awt.event.ActionEvent;
import
java.awt.event.ActionListener;
19 | P a g e
public class MainMenu extends
JFrame { public MainMenu() {
setTitle("Supply Chain Management
System"); setSize(500, 300);
setDefaultCloseOperation(JFrame.EXIT_ON
_CLOSE); setLayout(new GridLayout(4,
1));
JLabel titleLabel = new JLabel("Supply Chain Management System App",
SwingConstants.CENTER); titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
add(titleLabel);
JButton productButton = new JButton("Manage
Products"); JButton supplierButton = new
JButton("Manage Suppliers"); JButton orderButton =
new JButton("Manage Orders");
productButton.setForeground(Color.BL
ACK);
supplierButton.setForeground(Color.BL
ACK);
orderButton.setForeground(Color.BL
ACK);
productButton.addActionListener(e->new
ProductFrame().setVisible(true));
supplierButton.addActionListener(e->new
SupplierFrame().setVisible(true));
orderButton.addActionListener(e->new
OrderFrame().setVisible(true));
20 | P a g e
add(productButt
on);
add(supplierButto
n);
add(orderButton
);
}
public static void main(String[] args) {
SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
}
}
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import
java.sql.PreparedStatement;
import java.sql.ResultSet;
public class ProductFrame extends JFrame {
private JTextField nameField, priceField,
supplierIdField; private JTable productTable;
public ProductFrame() {
setTitle("Product
Management");
setSize(600,400);
setLayout(new
BorderLayout());
JPanel inputPanel = new JPanel(new
GridLayout(4, 2)); inputPanel.add(new
JLabel("Product Name:")); nameField = new
JTextField(); inputPanel.add(nameField);
inputPanel.add(new
JLabel("Price:")); priceField = new
JTextField();
inputPanel.add(priceField);
inputPanel.add(new JLabel("Supplier
ID:")); supplierIdField = new
JTextField();
inputPanel.add(supplierIdField);
JButton addButton = new JButton("Add
Product"); JButton deleteButton = new
JButton("Delete Product");
21 | P a g e
addButton.addActionListener(e->addProduct());
deleteButton.addActionListener(e -> deleteProduct());
inputPanel.add(addButton);
inputPanel.add(deleteButton);
add(inputPanel,
BorderLayout.NORTH);
productTable = new JTable();
JScrollPane tableScrollPane = new JScrollPane(productTable);
add(tableScrollPane, BorderLayout.CENTER);
loadProducts();
}
private void addProduct() {
String name = nameField.getText();
String price = priceField.getText();
22 | P a g e
String supplierId = supplierIdField.getText();
try (Connection conn = DatabaseConnection.connect()) {
String sql = "INSERT INTO products (product_name, price, supplier_id) VALUES (?, ?, ?)";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setString(1, name);
stmt.setDouble(2,
Double.parseDouble(price));
stmt.setInt(3,
Integer.parseInt(supplierId));
stmt.executeUpdate();
loadProducts();
} catch (Exception e) {
JOptionPane.showMessageDialog(this, "Error adding product: " + e.getMessage());
}
}
private void deleteProduct() {
int row =
productTable.getSelectedRow(); if
(row >= 0) {
String productId = productTable.getValueAt(row,
0).toString(); try (Connection conn =
DatabaseConnection.connect()) {
String sql = "DELETE FROM products WHERE
product_id = ?"; PreparedStatement stmt =
conn.prepareStatement(sql); stmt.setInt(1,
Integer.parseInt(productId));
stmt.executeUpdate();
loadProducts();
} catch (Exception e){
JOptionPane.showMessageDialog(this, "Error deleting product: " + e.getMessage());
}
} else {
JOptionPane.showMessageDialog(this, "Please select a product to delete.");
}
}
private void loadProducts() {
// Code to load and display products in the JTable from database
}
}