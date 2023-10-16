
/**
 * @author User
 * Defines the profile of an account holder.
 */
public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    public Profile(String fName, String lName, Date dob){
        this.fname = fName;
        this.lname = lName;
        this.dob = dob;
    }

    @Override
    public int compareTo(Profile o){
        return 0;
    }
    // TODO: Add required implementation for Profile

    public boolean equals(Profile o) {
        return this.fname.equals(o.fname) && this.lname.equals(o.lname) && this.dob.toString().equals(o.dob.toString());
    }

}
    