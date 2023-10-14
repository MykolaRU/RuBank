
/**
 * @author User
 * Class representing a Savings.
 */
public class Savings  extends Account{
    protected boolean isLoyal; //loyal customer status
    private static final double INTEREST_RATE = 0.04;
    private static final double INTEREST_RATE_ISLOYAL = 0.0425;
    private static final double FEE_WAIVER_BALANCE = 500;
    private static final double MONTHLY_FEE = 25;

    public Savings(Profile holder, double balance, boolean isLoyal){
        super(holder,balance);
        this.isLoyal = isLoyal;
    }


    @Override
    public double monthlyInterest() {
        if ( isLoyal) return this.balance * (INTEREST_RATE_ISLOYAL)/12;      // Interest if Loyal customer
        return this.balance * (INTEREST_RATE)/12;                            // Interest if not Loyal customer
    }

    @Override
    public double monthlyFee() {
        return (this.balance>=FEE_WAIVER_BALANCE) ? 0 : MONTHLY_FEE;         // If more than $500 no fee
    }

    //-------------- Sets isLoyal status
    public void setIsLoyal(boolean isLoyal){
        this.isLoyal = isLoyal;
    }

    //-------------- Gets isloyal status
    public boolean getIsLoyal(){
        return this.isLoyal;
    }

    //-------------- Method to withdraw money from the account
    public boolean withdraw(double amount) {
        if(this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    //-------------- Deposits money
    public boolean deposit(double amount){
        this.balance += amount;
        return true;
    }


    @Override
    public int compareTo(Account o) {
        //TODO:-------------------------------------
        return 0;
    }
}
        