
/**
 * @author User
 * Class representing a MoneyMarket.
 */
public class MoneyMarket extends Savings{
    private int withdrawal;                                         //Number of withdrawals
    private static final double FEE_WAIVER_BALANCE = 2000;          // If balance >=$2000 no fee
    private static final double INTEREST_RATE = 0.045;              // Standard interest rate 4.5%
    private static final double INTEREST_RATE_ISLOYAL = 0.0475;     // If Loyal customer interest 4.75%
    private static final double MAX_WITHDRAWALS = 3;                // Max withdrawals with no fee
    private static final double WITHDRAWAL_FEE = 10;                // Fee if withdrawal >3


    public MoneyMarket(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance, true);                       // Automatically sets the account to isLoyal
        this.withdrawal = 0;
    }

    @Override
    public double monthlyInterest() {
        if (this.balance < FEE_WAIVER_BALANCE) {
            this.setIsLoyal(false);
            return this.balance * INTEREST_RATE / 12;              // Monthly interest wihtout loyal bonus
        }
        return this.balance * (INTEREST_RATE_ISLOYAL) / 12;         // Monthly interest with a loyal bonus
    }

    @Override
    public double monthlyFee() {
        double fee = 0;                                        // Calculates fee
        if (this.balance < FEE_WAIVER_BALANCE) {               // If balance 2000< adds $25 fee
            fee += super.monthlyFee();
        }
        if (withdrawal > MAX_WITHDRAWALS) {                    // If withdrawal > 3 adds $10 fee
            fee += WITHDRAWAL_FEE;
        }
        return fee;                                            // Returns fee
    }


    @Override
    public String toString() {
        if(isLoyal) {
            return String.format("Money Market::Savings::%s::Balance $%.2f::is loyal::withdrawal: %s", getHolder().toString(), getBalance(), withdrawal);
        }
        else{
            return String.format("Money Market::Savings::%s::Balance $%.2f::withdrawal: %s", getHolder().toString(), getBalance(), withdrawal);
        }
    }

    @Override
    public boolean withdraw(double amount) {
        if(this.balance >= amount) {
            this.balance -= amount;
            withdrawal++;
            return true;
        }
        return false;
    }

    //-------------- Getter for number of withdrawals
    public int getWithdrawal() {
        return this.withdrawal;
    }

    //-------------- Setter for number of withdrawals
    public void setWithdrawal(int withdrawal) {
        this.withdrawal = withdrawal;
    }

    //-------------- Method to increment the withdrawal count
    public void incrementWithdrawal() {
        this.withdrawal++;
    }

    /** I probably don't need this as MM extends Savings account that has these methods **/
    //    //-------------- Method to deposit money to the account
//    public void depositMoney(double amount) {
//        super.depositMoney(amount);                         // Use the depositMoney method from the Savings class
//    }
//
//    //-------------- Method for withdrawing money from balance
//    public boolean withdrawMoney(double amount){
//        if(super.withdrawMoney(amount)){                    // Calls super and checks if enough money on Balance
//            incrementWithdrawal();                          // Increments number of withdrawals by one
//            return true;
//        }
//        return false;
//    }



}
        