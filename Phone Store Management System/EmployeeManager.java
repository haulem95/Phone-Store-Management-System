import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<SalesPerson> salesPersons;
    private String filename;

    public EmployeeManager(String file) {
        this.filename = file;
        this.salesPersons = new ArrayList<>();
        loadData();
    }

    // Method to add a new sales person to the list
    public void addSalesPerson(SalesPerson salesPerson) {
        this.salesPersons.add(salesPerson);
    }

    // Method to remove a sales person from the list
    public void removeSalesPerson(SalesPerson salesPerson) {
        this.salesPersons.remove(salesPerson);
    }

    // Method to get all sales persons
    public List<SalesPerson> getAllSalesPersons() {
        return this.salesPersons;
    }

    // Method to find a sales person by ID
    public SalesPerson findSalesPersonById(int id) {
        for (SalesPerson salesPerson : this.salesPersons) {
            if (salesPerson.getId() == id) {
                return salesPerson;
            }
        }
        return null; // Return null if no sales person with the given ID is found
    }

    // Method to update the information of a sales person
    public void updateSalesPerson(SalesPerson updatedSalesPerson) {
        for (int i = 0; i < this.salesPersons.size(); i++) {
            SalesPerson salesPerson = this.salesPersons.get(i);
            if (salesPerson.getId() == updatedSalesPerson.getId()) {
                this.salesPersons.set(i, updatedSalesPerson); // Replace the old sales person with the updated one
                return;
            }
        }
        // If no sales person with the same ID is found, do nothing
    }

    // Method to calculate the total salary of all sales persons
    public float calculateTotalSalary() {
        float totalSalary = 0;
        for (SalesPerson salesPerson : this.salesPersons) {
            totalSalary += salesPerson.getSalary();
        }
        return totalSalary;
    }

    // Method to load data from a text file
    public void loadData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int id = Integer.parseInt(parts[1]);
                String password = parts[2];
                String phoneNumber = parts[3];
                String address = parts[4];
                float salary = Float.parseFloat(parts[5]);
                salesPersons.add(new SalesPerson(name, id, password, phoneNumber, address, salary));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading employee data: " + e.getMessage());
        }
    }

    // Method to save data to a text file
    public void saveData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (SalesPerson salesPerson : salesPersons) {
                writer.write(salesPerson.getName() + "," + salesPerson.getId() + "," +
                        salesPerson.getPassword() + "," + salesPerson.getPhoneNumber() + "," +
                        salesPerson.getAddress() + "," + salesPerson.getSalary());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving employee data: " + e.getMessage());
        }
    }
}
