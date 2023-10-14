
/**
 * @author User
 * Class representing a CollegeChecking.
 */
public class CollegeChecking extends Checking{               // NO MONTHLY FEE FOR THIS ACCOUNT
    private static final double INTEREST_RATE = 0.01;        // 1.0% annual interest rate
    private static final double FEE = 12;                    // Monthly fee
    private Campus campus;                                  // Campus code

    public CollegeChecking(Profile holder, double balance, Campus campus){
        super(holder,balance);
        this.campus = campus;
    }

    //-------------- Defines Monthly Fee --------------
    @Override
    public double monthlyFee() {
        return 0;                               // No fee for CC account
    }

    //-------------- Calculates Monthly Interest --------------
    @Override
    public double monthlyInterest() {
        return this.balance * INTEREST_RATE / 12; // Monthly interest
    }

    //-------------- Sets Campus --------------
    public void setCampus(Campus campus){
        this.campus = campus;
    }

    //-------------- Gets Campus --------------
    public Campus getCampus(){
        return this.campus;
    }


    /** I probably don't need this as CC extends Checking account that has these methods **/
//    //-------------- Method to withdraw money from the account
//    public boolean withdrawMoney(double amount) {
//        if(super.withdrawMoney(amount)) {
//            return true;                                // Returns true if withdrawal was successfully
//        }
//        return false;
//    }
//
//    //-------------- Deposit money method
//    public void depositMoney(double amount){
//        this.balance += amount;
//    }
}
