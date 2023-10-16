
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
        if (this.lname.compareTo(o.lname)>0) return 1;
        if (this.lname.compareTo(o.lname)<0) return -1;
        if (this.fname.compareTo(o.fname)>0) return 1;
        if (this.fname.compareTo(o.fname)<0) return -1;
        if (this.dob.compareTo(o.dob)>0) return 1;
        if (this.dob.compareTo(o.dob)<0) return -1;
        return 0;
    }

    public boolean equals(Profile o) {
        return this.fname.equalsIgnoreCase(o.fname) && this.lname.equalsIgnoreCase(o.lname) && this.dob.toString().equalsIgnoreCase(o.dob.toString());
    }
    @Override
    public String toString(){
        return String.format("%s %s %s",fname,lname,dob.toString());
    }

}
    