import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private List<MobilePhone> allPhones;
    private String filename;

    public InventoryManager(String file) {
        this.filename=file;
        allPhones = new ArrayList<>();
        loadData();
    }

    // Method to add a new phone to the inventory
    public void addPhone(MobilePhone phone) {
        allPhones.add(phone);
    }

    // Method to remove a phone from the inventory
    public void removePhone(MobilePhone phone) {
        allPhones.remove(phone);
    }

    // Method to update the quantity of a phone in the inventory
    public void updatePhoneQuantity(MobilePhone phone, int newQuantity) {
        for (MobilePhone p : allPhones) {
            if (p.equals(phone)) {
                p.setQuantity(newQuantity);
                return;
            }
        }
        // Handle case where phone is not found
        System.out.println("Phone not found in inventory.");
    }

    // Method to search for phones by brand
    public List<MobilePhone> searchByBrand(String brand) {
        List<MobilePhone> result = new ArrayList<>();
        for (MobilePhone phone : allPhones) {
            if (phone.getBrand().equalsIgnoreCase(brand)) {
                result.add(phone);
            }
        }
        return result;
    }

    // Method to search for phones by model
    public List<MobilePhone> searchByModel(String model) {
        List<MobilePhone> result = new ArrayList<>();
        for (MobilePhone phone : allPhones) {
            if (phone.getModel().equalsIgnoreCase(model)) {
                result.add(phone);
            }
        }
        return result;
    }

    // Method to get all phones in the inventory
    public List<MobilePhone> getAllPhones() {
        return allPhones;
    }

    public MobilePhone getPhoneById(Integer key) {
        for (MobilePhone phone : allPhones) {
            if (phone.getId() == key) {
                return phone;
            }
        }
        return null; // Phone not found with the given ID
    }
    public void loadData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String brand = parts[1];
                String model = parts[2];
                float price = Float.parseFloat(parts[3]);
                int quantity = Integer.parseInt(parts[4]);
                allPhones.add(new MobilePhone(id, brand, model, price, quantity));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading mobile phone data: " + e.getMessage());
        }
    }

    // Method to save data to a text file
    public void saveData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (MobilePhone phone : allPhones) {
                writer.write(phone.getId() + "," + phone.getBrand() + "," + phone.getModel() + "," +
                        phone.getPrice() + "," + phone.getQuantity());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving mobile phone data: " + e.getMessage());
        }
    }

    public void deletePhone(MobilePhone phoneToDelete) {
        // Find the index of the phone to delete
        int index = -1;
        for (int i = 0; i < allPhones.size(); i++) {
            if (allPhones.get(i).equals(phoneToDelete)) {
                index = i;
                break;
            }
        }
    
        if (index != -1) {
            // Phone found, remove it from the list
            allPhones.remove(index);
            System.out.println("Phone deleted successfully!");
            
            // Save the updated inventory data to the text file
            saveData();
        } else {
            System.out.println("Phone not found in inventory.");
        }
    }
    
}
