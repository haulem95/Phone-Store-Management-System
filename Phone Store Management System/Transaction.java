import java.time.LocalDate;

public class Transaction {
    private int id;
    private int salesPersonId;
    private int itemsid; // Represents the ID of the mobile phone
    private LocalDate time;

    // Constructor
    public Transaction(int id, int salesPersonId, int itemsid, LocalDate time) {
        this.id = id;
        this.salesPersonId = salesPersonId;
        this.itemsid = itemsid;
        this.time = time;
    }

    // Getter and setter methods for all attributes

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalesPersonId() {
        return salesPersonId;
    }

    public void setSalesPersonId(int salesPersonId) {
        this.salesPersonId = salesPersonId;
    }

    public int getItemsid() {
        return itemsid;
    }

    public void setItemsid(int itemsid) {
        this.itemsid = itemsid;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }
}
