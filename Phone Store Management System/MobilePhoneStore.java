
public class MobilePhoneStore {
    private InventoryManager inventory;
    private EmployeeManager employeeManager;
    private TransactionManager transactionManager;
    private SalesPerson admin;

    // Constructor
    public MobilePhoneStore(String file1, String file2) {
        // Initialize inventory and employee manager
        this.inventory = new InventoryManager(file1);
        this.employeeManager = new EmployeeManager(file2);
    }

    // Constructor with parameters
    public MobilePhoneStore(InventoryManager inventory, EmployeeManager employeeManager, TransactionManager transactionManager) {
        this.inventory = inventory;
        this.employeeManager = employeeManager;
        this.transactionManager=transactionManager;
        this.admin = new SalesPerson("admin", 0, "admin", null, null, 0);
    }

    public InventoryManager getInventory() {
        return inventory;
    }
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setInventory(InventoryManager inventory) {
        this.inventory = inventory;
    }

    public EmployeeManager getEmployeeManager() {
        return employeeManager;
    }

    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    public SalesPerson getAdmin() {
        return admin;
    }

    public void setAdmin(SalesPerson admin) {
        this.admin = admin;
    }

    // Other methods for managing the mobile phone store can be added here
}
