package Inventory;

/** Creates a concrete extension of the parts class for outsourced parts. */
public class OutSourced extends Part  {

    private String companyName;

    /** Getter to return Company Name. */
    public String getCompanyName() {
        return companyName;
    }

    /** Setter to set company Name. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** Extends the parts constructor and adds a Company Name field. */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
}
