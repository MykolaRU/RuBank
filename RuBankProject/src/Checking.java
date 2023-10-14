
/**
 * @author User
 * Class representing a Checking.
 */
public class Checking extends Account{
    private static final double INTEREST_RATE = 0.01;        // 1.0% annual interest rate
    private static final double MONTHLY_FEE = 12;            // Monthly fee $12
    private static final double FEE_WAIVER_BALANCE = 1000;   // Monthly fee is waived if balance is >= $1000.

    public Checking(Profile holder, double balance){
        super(holder, balance);
    }

    //-------------- Calculates Monthly Interest
    @Override
    public double monthlyInterest() {
        return this.balance * INTEREST_RATE / 12;           // Assuming monthly interest is 1/12 of annual
    }

    //-------------- Calculates Monthly Fee
    @Override
    public double monthlyFee() {
        return (this.balance >=
                FEE_WAIVER_BALANCE) ? 0 : MONTHLY_FEE;      // if balance >=1000 no fees else $25 fee
    }

    @Override
    public int compareTo(Account o) {
        //TODO:-------------------------
        return 0;
    }

    //-------------- Gets Balance
    public double getBalance() {
        return this.balance;
    }

    //-------------- Sets Balance
    public void setBalance(double balance) {
        this.balance = balance;
    }

    //-------------- Gets Holder
    public Profile getHolder() {
        return this.holder;
    }

    //-------------- Sets Holder
    public void setHolder(Profile holder) {
        this.holder = holder;
    }

    //-------------- Method to withdraw money from the account
    public boolean withdraw(double amount) {
        if(this.balance >= amount) {
            this.balance -= amount;
            return true;                                // Returns true if withdrawal was successfully
        }
        return false;
    }

    //-------------- Deposit money method
    public boolean deposit(double amount){
        this.balance += amount;
        return true;
    }

}
        