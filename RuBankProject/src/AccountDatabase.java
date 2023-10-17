
/**
 * @authors Mykola and Ethan
 * A database to manage different account types.
 */
public class AccountDatabase {
    private Account[] accounts;
    private int numAcct;

    /**
     * Constructor for the AccountDatabase class.
     * Initializes the array of accounts and sets the initial number of accounts to zero.
     */
    public AccountDatabase(){
        this.accounts = new Account[4];         // Initial Capacity Of Accounts
        this.numAcct = 0;                            // Number Of Accounts
    }

    /**
     * Gets the current number of accounts in the database.
     * @return The number of accounts.
     */
    public int getNumAcct(){
        return this.numAcct;
    }

    // Method to find an account in the array
    private int find(Account account) {

        for(int i = 0; i<numAcct; i++){
            if(accounts[i].getHolder().equals(account.getHolder())&& accounts[i].getClass().equals(account.getClass())) return i;
        }

        return -1;                              // if account not found
    }


    // Method to grow the array by 4 slots when it's full
    private void grow() {
        if (numAcct >= accounts.length) {
            Account[] newAccounts = new Account[accounts.length + 4];
            for (int i = 0; i < numAcct; i++) {
                newAccounts[i] = accounts[i];
            }
            accounts = newAccounts;
        }
    }

    /**
     * Checks if the database contains a given account.
     * @param account The account to check for.
     * @return True if the account exists in the database, false otherwise.
     */
    public boolean contains(Account account) {
        return find(account) != -1;
    }


    /**
     * Adds a new account to the database.
     * @param account The account to be added.
     * @return True if the account was successfully added, false if it already exists or there was an error.
     */
    public boolean open(Account account) {
        if (contains(account)) {
            return false;                           // Returns false if account already exists
        }

        if (numAcct >= accounts.length) {           // If array full, grow
            grow();
        }

        accounts[numAcct] = account;
        numAcct++;
        return true;                                // Returns true when account is created
    }

    /**
     * Removes an account from the database.
     * @param account The account to be removed.
     * @return True if the account was successfully removed, false if it does not exist in the database.
     */
    public boolean close(Account account) {
        int index = find(account);                  // Uses find to get index of the account
        if (index == -1) {
            return false;                           // Returns false if account not found
        }

        // Shift all elements to the left to fill the gap
        for (int i = index; i < numAcct - 1; i++) {
            accounts[i] = accounts[i + 1];
        }
        numAcct--;
        accounts[numAcct] = null;                   // Clears the last position of account after shift
        return true;
    }

    /**
     * Withdraws a specified amount of money from an account.
     * @param account The account to withdraw from.
     * @param amount The amount to withdraw.
     * @return True if the withdrawal was successful, false if the account does not exist or there are insufficient funds.
     */
    public boolean withdraw(Account account, double amount) {
        int index = find(account);

        if(index == -1) return false;

        if (accounts[index].getBalance()<amount)
            return false;

        return accounts[index].withdraw(amount);    // Calls the with draw method of the specific account type
    }

    /**
     * Deposits a specified amount of money into an account.
     * @param account The account to deposit into.
     * @param amount The amount to deposit.
     * @return True if the deposit was successful, false if the account does not exist or there was an error.
     */
    public boolean deposit(Account account, double amount) {
        int index = find(account);
        if (index == -1) {
            return false;                           // account not found
        }
        return accounts[index].deposit(amount);     // Calls the deposit method of the specific account type
    }

     // This method prints the accounts sorted by account type and holder's name.
    public void printSorted() {
        Account holder;
        for (int i = 0; i < numAcct; i++) {
            for (int j = 0; j < numAcct - i - 1; j++) {
                if (accounts[j].getClass().toString().compareTo(accounts[j+1].getClass().toString())>0 ||
                        ((accounts[j].getClass().toString().compareTo(accounts[j+1].getClass().toString()) == 0)
                                && (accounts[j].holder.compareTo(accounts[j+1].holder) > 0))) {
                    holder = accounts[j];
                    accounts[j] = accounts[j + 1];
                    accounts[j + 1] = holder;
                }
            }
        }
        System.out.println();
        System.out.println("*Accounts sorted by account type and profile.");
        for (int i = 0; i < numAcct; i++) {
            System.out.println(accounts[i].toString());
        }
        System.out.println("*end of list.");
        System.out.println();

    }

     // This method prints a list of accounts with associated fees and monthly interest.
    public void printFeesAndInterests() {
        Account holder;
        for (int i = 0; i < numAcct; i++) {
            for (int j = 0; j < numAcct - i - 1; j++) {
                if (accounts[j].getClass().toString().compareTo(accounts[j+1].getClass().toString())>0 ||
                        ((accounts[j].getClass().toString().compareTo(accounts[j+1].getClass().toString()) == 0)
                                && (accounts[j].holder.compareTo(accounts[j+1].holder) > 0))) {
                    holder = accounts[j];
                    accounts[j] = accounts[j + 1];
                    accounts[j + 1] = holder;
                }
            }
        }

        System.out.println();
        System.out.println("*list of accounts with fee and monthly interest");
        for (int i = 0; i < numAcct; i++) {
            System.out.println(String.format("%s::fee $%.2f::monthly interest $",accounts[i].toString(),accounts[i].monthlyFee(),accounts[i].monthlyInterest()));
        }
        System.out.println("*end of list.");
        System.out.println();
    }

     // This method updates account balances by applying fees and interests and then prints the updated balances.
    public void printUpdatedBalances() {

        for(int i = 0; i<numAcct; i++){
            accounts[i].deposit(accounts[i].monthlyInterest() - accounts[i].monthlyFee());
        }

        Account holder;
        for (int i = 0; i < numAcct; i++) {
            for (int j = 0; j < numAcct - i - 1; j++) {
                if (accounts[j].getClass().toString().compareTo(accounts[j+1].getClass().toString())>0 ||
                        ((accounts[j].getClass().toString().compareTo(accounts[j+1].getClass().toString()) == 0)
                                && (accounts[j].holder.compareTo(accounts[j+1].holder) > 0))) {
                    holder = accounts[j];
                    accounts[j] = accounts[j + 1];
                    accounts[j + 1] = holder;
                }
            }
        }

        System.out.println();
        System.out.println("*list of accounts with fees and interests applied.");
        for (int i = 0; i < numAcct; i++) {
            System.out.println(accounts[i].toString());
        }
        System.out.println("*end of list.");
        System.out.println();
    }
}
    