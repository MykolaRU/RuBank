
/**
 * @author User
 * A database to manage different account types.
 */
public class AccountDatabase {
    private Account[] accounts;
    private int numAcct;

    public AccountDatabase(){
        this.accounts = new Account[4];         // Initial Capacity Of Accounts
        numAcct = 0;                            // Number Of Accounts
    }

    // Method to find an account in the array
    private int find(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].equals(account)) {
                return i;
            }
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

    // Check if an account exists in the database
    public boolean contains(Account account) {
        return find(account) != -1;
    }

    // Method to add a new account to the database
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

    // Removes account from the database
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

    // Method to withdraw money from an account
    public boolean withdraw(Account account, double amount) {
        int index = find(account);
        if (index == -1) {
            return false; // account not found
        }
        return accounts[index].withdraw(amount);    // Calls the with draw method of the specific account type
    }

    // Method to deposit money into an account
    public boolean deposit(Account account, double amount) {
        int index = find(account);
        if (index == -1) {
            return false;                           // account not found
        }
        return accounts[index].deposit(amount);     // Calls the deposit method of the specific account type
    }

    public void printSorted() {
        // TODO: Implement the printSorted method
    }

    public void printFeesAndInterests() {
        // TODO: Implement the printFeesAndInterests method
    }

    public void printUpdatedBalances() {
        // TODO: Implement the printUpdatedBalances method
    }
}
    