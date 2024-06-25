import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private List<Transaction> transactions;
    private String filename;

    public TransactionManager(String filename) {
        this.transactions = new ArrayList<>();
        this.filename = filename;
        loadData();
    }

    // Method to add a new transaction
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // Method to retrieve a transaction by ID
    public Transaction getTransactionById(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        return null; // Transaction not found
    }

    // Method to retrieve all transactions within a date range
    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTime();
            if (transactionDate.isEqual(startDate) || (transactionDate.isAfter(startDate) && transactionDate.isBefore(endDate))) {
                result.add(transaction);
            }
        }
        return result;
    }

    // Method to load data from a text file
    public void loadData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                int salesPersonId = Integer.parseInt(parts[1]);
                int itemsid = Integer.parseInt(parts[2]);
                LocalDate time = LocalDate.parse(parts[3]);
                transactions.add(new Transaction(id, salesPersonId, itemsid, time));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading transaction data: " + e.getMessage());
        }
    }

    // Method to save data to a text file
    public void saveData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (Transaction transaction : transactions) {
                writer.write(transaction.getId() + "," + transaction.getSalesPersonId() + "," +
                        transaction.getItemsid() + "," + transaction.getTime());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving transaction data: " + e.getMessage());
        }
    }
}
