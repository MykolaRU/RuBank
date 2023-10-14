
/**
 * @author User
 * Abstract class representing a general account type.
 */
public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;

    public Account(Profile holder, double balance) {
        this.holder=holder;
        this.balance=balance;
    }


    public abstract double monthlyInterest();
    public abstract double monthlyFee();
    public abstract boolean deposit(double amount);
    public abstract boolean withdraw(double amount);
}
