package school.schoolproject.model;

/** Extension of part class that includes outsourced data */
public class Outsourced extends Part {
    private String CompanyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String CompanyName) {
        super(id, name, price, stock, min, max);
        this.CompanyName = CompanyName;
    }
    /** Company Name setter */
    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }
    /** Company Name getter */
    public String getCompanyName(){
        return CompanyName;
    }
}