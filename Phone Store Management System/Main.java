import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static MobilePhoneStore mobilePhoneStore;
    private static Scanner scanner;

    public static void main(String[] args) {
        String inventoryDataFile = "inventory_data.txt";
        String employeeDataFile = "employee_data.txt";
        String transactionDataFile = "transaction_data.txt";

        InventoryManager inventoryManager = new InventoryManager(inventoryDataFile);
        EmployeeManager employeeManager = new EmployeeManager(employeeDataFile);
        TransactionManager transactionManager = new TransactionManager(transactionDataFile);

        mobilePhoneStore = new MobilePhoneStore(inventoryManager, employeeManager, transactionManager);

        scanner = new Scanner(System.in);

        System.out.println("Welcome to the Mobile Phone Store!");

        System.out.println("Are you a sales person or an admin?");
        System.out.println("1. Sales Person");
        System.out.println("2. Admin");
        System.out.print("Enter your choice: ");
        int userTypeChoice = scanner.nextInt();

        switch (userTypeChoice) {
            case 1:
                loginAsSalesPerson();
                break;
            case 2:
                loginAsAdmin();
                break;
            default:
                System.out.println("Invalid choice.");
        }

        scanner.close();
    }

    private static void loginAsSalesPerson() {
        System.out.println("Enter your ID:");
        int salesPersonId = scanner.nextInt();
        SalesPerson salesPerson = mobilePhoneStore.getEmployeeManager().findSalesPersonById(salesPersonId);
        if (salesPerson != null) {
            System.out.println("Welcome, " + salesPerson.getName() + "! Please enter your password:");
            String enteredPassword = scanner.next();
            if (enteredPassword.equals(salesPerson.getPassword())) {
                System.out.println("Password correct. Logging in...");
                salesPersonMenu(salesPerson);
            } else {
                System.out.println("Incorrect password. Access denied.");
            }
        } else {
            System.out.println("Sales person not found.");
        }
    }
    
    private static void salesPersonMenu(SalesPerson salesPerson) {
        int salesPersonChoice;
        do {
            System.out.println("\nSales Person Menu:");
            System.out.println("1. View Stocks");
            System.out.println("2. Sell Phone");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            salesPersonChoice = scanner.nextInt();
    
            switch (salesPersonChoice) {
                case 1:
                    viewStocks();
                    break;
                case 2:
                    sellPhone(salesPerson);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (salesPersonChoice != 3);
    }
    
    private static void viewStocks() {
        List<MobilePhone> phones = mobilePhoneStore.getInventory().getAllPhones();
    
        System.out.println("List of Stocks:");
        for (MobilePhone phone : phones) {
            System.out.println(phone);
        }
    }
    
    private static void sellPhone(SalesPerson salesPerson) {
        viewStocks();
    
        System.out.print("Enter the ID of the phone to sell: ");
        int phoneId = scanner.nextInt();
    
        System.out.print("Enter the quantity to sell: ");
        int quantity = scanner.nextInt();
    
        MobilePhone phoneToSell = mobilePhoneStore.getInventory().getPhoneById(phoneId);
    
        if (phoneToSell != null) {
            if (phoneToSell.getQuantity() >= quantity) {
                float totalPrice = phoneToSell.getPrice() * quantity;
    
                phoneToSell.setQuantity(phoneToSell.getQuantity() - quantity);
                mobilePhoneStore.getInventory().saveData();
    
                Transaction transaction = new Transaction(0, salesPerson.getId(), phoneId, LocalDate.now());
                mobilePhoneStore.getTransactionManager().addTransaction(transaction);
                mobilePhoneStore.getTransactionManager().saveData();
    
                System.out.println("Sale successful!");
                System.out.println("Total price: $" + totalPrice);
            } else {
                System.out.println("Insufficient quantity available.");
            }
        } else {
            System.out.println("Phone not found.");
        }
    }
    

    private static void loginAsAdmin() {
        System.out.println("Enter your admin username:");
        String username = scanner.next();
        System.out.println("Enter your admin password:");
        String password = scanner.next();
        if (username.equals("admin") && password.equals("admin")) {
            System.out.println("Welcome, Admin!");

            adminMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void adminMenu() {
        int adminChoice;
        do {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Control Phones");
            System.out.println("2. Manage Salesperson");
            System.out.println("3. Browse Stocks");
            System.out.println("4. Transaction Report");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            adminChoice = scanner.nextInt();

            switch (adminChoice) {
                case 1:
                    controlPhones();
                    break;
                case 2:
                    manageSalesperson();
                    break;
                case 3:
                    browseStocks();
                    break;
                case 4:
                    generateTransactionReport();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (adminChoice != 5);
    }

    private static void controlPhones() {
        int controlPhonesChoice;
        do {
            System.out.println("\nControl Phones:");
            System.out.println("1. Add Phone");
            System.out.println("2. Edit Phone");
            System.out.println("3. Delete Phone");
            System.out.println("4. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            controlPhonesChoice = scanner.nextInt();

            switch (controlPhonesChoice) {
                case 1:
                    addPhone();
                    break;
                case 2:
                    editPhone();
                    break;
                case 3:
                    deletePhone();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (controlPhonesChoice != 4);
    }

    private static void addPhone() {
        System.out.println("Enter the details of the new phone:");
        System.out.print("Enter the ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter the brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter the model: ");
        String model = scanner.nextLine();
        System.out.print("Enter the price: ");
        float price = scanner.nextFloat();
        System.out.print("Enter the quantity: ");
        int quantity = scanner.nextInt();
    
        MobilePhone newPhone = new MobilePhone(id, brand, model, price, quantity);
    
        mobilePhoneStore.getInventory().addPhone(newPhone);
        System.out.println("Phone added successfully!");
        mobilePhoneStore.getInventory().saveData();

    }
    

    private static void editPhone() {
        System.out.print("Enter the ID of the phone you want to edit: ");
        int id = scanner.nextInt();
    
        MobilePhone phoneToEdit = mobilePhoneStore.getInventory().getPhoneById(id);
    
        if (phoneToEdit != null) {
            System.out.println("Enter the updated details:");
    
            System.out.print("Enter the brand: ");
            String brand = scanner.next();
            System.out.print("Enter the model: ");
            String model = scanner.next();
            System.out.print("Enter the price: ");
            float price = scanner.nextFloat();
            System.out.print("Enter the quantity: ");
            int quantity = scanner.nextInt();
    
            phoneToEdit.setBrand(brand);
            phoneToEdit.setModel(model);
            phoneToEdit.setPrice(price);
            phoneToEdit.setQuantity(quantity);
    
            mobilePhoneStore.getInventory().saveData();
    
            System.out.println("Phone details updated successfully!");
        } else {
            System.out.println("Phone with ID " + id + " not found.");
        }
    }
    
    private static void deletePhone() {
        System.out.print("Enter the ID of the phone you want to delete: ");
        int id = scanner.nextInt();
    
        MobilePhone phoneToDelete = mobilePhoneStore.getInventory().getPhoneById(id);
    
        if (phoneToDelete != null) {
            System.out.println("Are you sure you want to delete the following phone?");
            System.out.println(phoneToDelete);
            System.out.print("Enter 'yes' to confirm deletion, or 'no' to cancel: ");
            String confirmation = scanner.next();
    
            if (confirmation.equalsIgnoreCase("yes")) {
                mobilePhoneStore.getInventory().deletePhone(phoneToDelete);
                mobilePhoneStore.getInventory().saveData();
    
                System.out.println("Phone deleted successfully!");
            } else if (confirmation.equalsIgnoreCase("no")) {
                System.out.println("Deletion canceled.");
            } else {
                System.out.println("Invalid input. Deletion canceled.");
            }
        } else {
            System.out.println("Phone with ID " + id + " not found.");
        }
    }
    
    private static void manageSalesperson() {
        int manageSalespersonChoice;
        do {
            System.out.println("\nManage Salesperson:");
            System.out.println("1. Add User");
            System.out.println("2. Delete User");
            System.out.println("3. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            manageSalespersonChoice = scanner.nextInt();

            switch (manageSalespersonChoice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    deleteUser();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (manageSalespersonChoice != 3);
    }

    private static void addUser() {
        System.out.println("Enter the details of the new sales person:");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter salary: ");
        float salary = scanner.nextFloat();
    
        SalesPerson newSalesPerson = new SalesPerson(name, id, password, phoneNumber, address, salary);
    
        mobilePhoneStore.getEmployeeManager().addSalesPerson(newSalesPerson);
    
        mobilePhoneStore.getEmployeeManager().saveData();
    
        System.out.println("Sales person added successfully!");
    }
    
    private static void deleteUser() {
        System.out.print("Enter the ID of the sales person you want to delete: ");
        int id = scanner.nextInt();
    
        SalesPerson salesPersonToDelete = mobilePhoneStore.getEmployeeManager().findSalesPersonById(id);
    
        if (salesPersonToDelete != null) {
            System.out.println("Are you sure you want to delete the following sales person?");
            System.out.println(salesPersonToDelete);
            System.out.print("Enter 'yes' to confirm deletion, or 'no' to cancel: ");
            String confirmation = scanner.next();
    
            if (confirmation.equalsIgnoreCase("yes")) {
                mobilePhoneStore.getEmployeeManager().removeSalesPerson(salesPersonToDelete);
    
                mobilePhoneStore.getEmployeeManager().saveData();
    
                System.out.println("Sales person deleted successfully!");
            } else if (confirmation.equalsIgnoreCase("no")) {
                System.out.println("Deletion canceled.");
            } else {
                System.out.println("Invalid input. Deletion canceled.");
            }
        } else {
            System.out.println("Sales person with ID " + id + " not found.");
        }
    }
    
    private static void browseStocks() {
        int browseStocksChoice;
        do {
            System.out.println("\nBrowse Stocks:");
            System.out.println("1. List");
            System.out.println("2. Search based on name");
            System.out.println("3. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            browseStocksChoice = scanner.nextInt();

            switch (browseStocksChoice) {
                case 1:
                    listStocks();
                    break;
                case 2:
                    searchByName();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (browseStocksChoice != 3);
    }

    private static void listStocks() {
        List<MobilePhone> phones = mobilePhoneStore.getInventory().getAllPhones();

        System.out.println("List of Stocks:");
        for (MobilePhone phone : phones) {
            System.out.println(phone);
        }
    }

    private static void searchByName() {
        System.out.print("Enter the name of the phone to search for: ");
        String name = scanner.nextLine();

        List<MobilePhone> phones = mobilePhoneStore.getInventory().searchByBrand(name);

        System.out.println("Search Results:");
        if (phones.isEmpty()) {
            System.out.println("No phones found with the specified name.");
        } else {
            for (MobilePhone phone : phones) {
                System.out.println(phone);
            }
        }
    }

    private static void generateTransactionReport() {
        System.out.print("Enter the start date (YYYY-MM-DD): ");
        String startDateStr = scanner.next();
        LocalDate startDate = LocalDate.parse(startDateStr);
        System.out.print("Enter the end date (YYYY-MM-DD): ");
        String endDateStr = scanner.next();
        LocalDate endDate = LocalDate.parse(endDateStr);

        List<Transaction> transactions = mobilePhoneStore.getTransactionManager().getTransactionsByDateRange(startDate, endDate);

        System.out.println("Transaction Report:");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found within the specified date range.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

}
