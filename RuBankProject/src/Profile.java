
/**
 * @authors Mykola, Ethan
 * Represents the profile of an account holder.
 * This class stores the first name, last name, and date of birth of an account holder.
 * It implements the Comparable interface to allow for comparison of profiles.
 */
public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructor for creating a Profile object.
     * @param fName The first name of the account holder.
     * @param lName The last name of the account holder.
     * @param dob The date of birth of the account holder.
     */
    public Profile(String fName, String lName, Date dob){
        this.fname = fName;
        this.lname = lName;
        this.dob = dob;
    }

    /**
     * Compares two profiles based on last name, first name, and date of birth.
     * @param o The Profile object to compare with.
     * @return 1 if this profile is greater, -1 if it is lesser, or 0 if they are equal.
     */
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

    /**
     * Checks if two Profile objects are equal.
     * Profiles are considered equal if their first names, last names, and date of births are the same (case-insensitive).
     * @param o The Profile object to compare with.
     * @return True if the profiles are equal, false otherwise.
     */
    public boolean equals(Profile o) {
        return this.fname.equalsIgnoreCase(o.fname) && this.lname.equalsIgnoreCase(o.lname) && this.dob.toString().equalsIgnoreCase(o.dob.toString());
    }

    /**
     * Returns a string representation of the Profile object.
     * The string includes the first name, last name, and date of birth.
     * @return A formatted string representation of the profile.
     */
    @Override
    public String toString(){
        return String.format("%s %s %s",fname,lname,dob.toString());
    }

}
    