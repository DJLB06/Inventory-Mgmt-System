package Inventory;

/** Creates a concrete extension of the parts class for inhouse parts. */
public class InHouse extends Part {

    private int machineID;

    /** Extends the parts constructor and adds a machine ID field. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** Getter to return machine ID. */
    public int getMachineID() {
        return machineID;
    }

    /** Setter to set machine ID. */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
