import java.util.Scanner;
import java.util.Calendar;
/**
 * @authors Mykola, Ethan
 * Class representing a TransactionManager.
 */
public class TransactionManager {

    private AccountDatabase accountDatabase;
    public static final int MONEY_MARKET_MIN_DEPOSIT = 2000;
    public static final int SIX_MONTHS = 6;


    public TransactionManager(){this.accountDatabase = new AccountDatabase();
    }

    public void run(){                                  // RUN simply records user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to RU Bank. Enter a command below: ");
        String accountType="", fName="", lName="", date="", deposit="", userInput="";
        int campusCode = -1, customerStatus = -1;

        while(true) {
            userInput = scanner.nextLine();

            if (userInput.equals("Q")) {
                System.out.println("Transaction Manager is terminated.");
                break;
            }

            String[] tokens = userInput.trim().split("\\s+");
            String command = tokens[0];

            if (command.equals("C") || command.equals("D") || command.equals("O") || command.equals("W")) {
                if(!checkLength(command, tokens)) continue;
                accountType = tokens[1];
                fName = tokens[2];
                lName = tokens[3];
                date = tokens[4];
                if(!command.equals("C")) deposit = tokens[5];
                if (tokens.length == 7 && tokens[1].equals("CC"))
                    campusCode = Integer.parseInt(tokens[6]);  // if 7 inputs, retrieve campus code
                if (tokens.length == 7 && tokens[1].equals("S")) customerStatus = Integer.parseInt(tokens[6]);
            }
            action(command, accountType, fName, lName, date, deposit, campusCode, customerStatus);
        }
    }


    /**
     * Checks the length of command arguments.
     *
     * @param command The command being checked.
     * @param tokens  An array of command tokens.
     * @return True if the command has the correct number of arguments, false otherwise.
     */
    private boolean checkLength(String command,String[] tokens){
        if(command.equals("C") && tokens.length < 5) {
            System.out.println("Missing data for closing an account.");
            return false;
        }
        if(command.equals("O") && tokens.length < 6) {
            System.out.println("Missing data for opening an account.");
            return false;
        }
        if(command.equals("D") && tokens.length < 6) {
            System.out.println("Missing data for depositing to an account.");
            return false;
        }
        if(command.equals("W") && tokens.length < 6) {
            System.out.println("Missing data for withdrawing from an account.");
            return false;
        }
        return true;
    }

    /**
     * Performs actions based on user commands.
     *
     * @param command        The user command.
     * @param accountType    The type of account.
     * @param fName          The first name of the account holder.
     * @param lName          The last name of the account holder.
     * @param date           The date of birth of the account holder.
     * @param deposit        The deposit amount.
     * @param campusCode     The campus code (for college checking accounts).
     * @param customerStatus The customer status (1 for loyal, 0 for non-loyal).
     */
    private void action(String command, String accountType, String fName, String lName, String date, String deposit, int campusCode, int customerStatus){
        switch (command) {
            case "O":
                openCommand(accountType,fName,lName,date,deposit,campusCode,customerStatus);
                break;
            case "C":
                closeCommand(accountType,fName,lName,date,deposit,campusCode,customerStatus);
                break;
            case "D":
                depositCommand(accountType,fName,lName,date,deposit,campusCode,customerStatus);
                break;
            case "W":
                withdrawCommand(accountType,fName,lName,date,deposit,campusCode,customerStatus);
                break;
            case "P":
                if(accountDatabase.getNumAcct() != 0)
                    accountDatabase.printSorted();
                else System.out.println("Account Database is empty!");
                break;
            case "PI":
                if(accountDatabase.getNumAcct() != 0)
                    accountDatabase.printFeesAndInterests();
                else System.out.println("Account Database is empty!");
                break;
            case "UB":
                if(accountDatabase.getNumAcct() != 0)
                    accountDatabase.printUpdatedBalances();
                else System.out.println("Account Database is empty!");
                break;
            case "":
                break;
            default:
                System.out.println(command + " Invalid command!");
                break;
        }
    }

        /**
         * Handles the opening of an account.
         *
         * @param accountType    The type of account to be opened.
         * @param fName          The first name of the account holder.
         * * @param lName The last name of the account holder.
         * * @param date The date of birth of the account holder.
         * * @param depositString The deposit amount as a string.
         * * @param campusCode The campus code (for college checking accounts).
         * * @param customerStatus The customer status (1 for loyal, 0 for non-loyal).
         * */
    public void openCommand(String accountType, String fName, String lName,
                            String date,String depositString, int campusCode, int customerStatus){

        if(!checkValid(accountType,fName,lName,date,depositString,campusCode,customerStatus)) return;

        if(contains(accountType,fName,lName,date,depositString,campusCode,customerStatus)) {
            System.out.println(String.format("%s %s %s(%s) is already in the database",fName,lName,date,accountType)); return;
        }

        double deposit = Double.parseDouble(depositString);

        switch (accountType) {
            case "C":               // Checking
                Checking newChecking = new Checking(new Profile(fName,lName,new Date(date)),deposit);
                if(accountDatabase.open(newChecking)) System.out.println(newChecking.getHolder().toString()+"(C) opened");
                break;
            case "CC":              // College checking
                CollegeChecking newCollegeChecking = new CollegeChecking(new Profile(fName,lName,new Date(date)),deposit,Campus.getCampus(campusCode));
                if(accountDatabase.open(newCollegeChecking)) System.out.println(newCollegeChecking.getHolder().toString()+"(CC) opened");
                break;
            case "S":               // Savings
                Savings newSavings = new Savings(new Profile(fName,lName,new Date(date)),deposit, customerStatus == 1);
                if(accountDatabase.open(newSavings)) System.out.println(newSavings.getHolder().toString()+"(S) opened");
                break;
            case "MM":               // Money market savings
                if(deposit < MONEY_MARKET_MIN_DEPOSIT) {
                    System.out.println("Minimum of $2000 to open a Money Market account.");
                    break;
                }
                MoneyMarket newMoneyMarket = new MoneyMarket(new Profile(fName,lName,new Date(date)),deposit, true);
                if(accountDatabase.open(newMoneyMarket)) System.out.println(newMoneyMarket.getHolder().toString()+"(MM) opened");
                break;
            default:
                break;
        }
    }

    /**
     * Checks if the provided data for an account is valid.
     *
     * @param accountType    The type of account.
     * @param fName          The first name of the account holder.
     * @param lName          The last name of the account holder.
     * @param date           The date of birth of the account holder.
     * @param depositString  The deposit amount as a string.
     * @param campusCode     The campus code (for college checking accounts).
     * @param customerStatus The customer status (1 for loyal, 0 for non-loyal).
     * @return True if the data is valid, false otherwise.
     */

    private boolean checkValid(String accountType, String fName, String lName,
                               String date,String depositString, int campusCode, int customerStatus){

        try{double deposit = Double.parseDouble(depositString);}
        catch(NumberFormatException formatException){System.out.println("Not a valid amount"); return false;}

        double deposit = Double.parseDouble(depositString);
        if(deposit <=0) {System.out.println("Initial deposit cannot be 0 or negative."); return false;}

        if(campusCode>2){System.out.println("Invalid campus code.");return false;}

        Date dateTest = new Date(date);
        if(!dateTest.isValid()) {System.out.println("DOB invalid: "+ dateTest.toString() + " not a valid calendar date!"); return false;}

        Calendar today = Calendar.getInstance();
        Calendar daten = Calendar.getInstance();
        daten.set(dateTest.getYear(), dateTest.getMonth()-1, dateTest.getDay());

        if(today.compareTo(daten) < 0){ System.out.println("DOB invalid: "+ dateTest.toString() + " cannot be today or a future day.");  return false;}

        daten.add(Calendar.YEAR, 16);

        if(today.compareTo(daten) < 0){ System.out.println("DOB invalid: "+ dateTest.toString() + " under 16."); return false;}
        if(accountType.equals("CC")){
            daten.add(Calendar.YEAR,8);
            if(today.compareTo(daten) > 0){ System.out.println("DOB invalid: "+ dateTest.toString() + " over 24."); return false;}
        }
        return true;
    }

    /**
     * Checks if an account with the provided data already exists in the database.
     *
     * @param accountType    The type of account.
     * @param fName          The first name of the account holder.
     * @param lName          The last name of the account holder.
     * @param date           The date of birth of the account holder.
     * @param depositString  The deposit amount as a string.
     * @param campusCode     The campus code (for college checking accounts).
     * @param customerStatus The customer status (1 for loyal, 0 for non-loyal).
     * @return True if the account exists, false otherwise.
     */
    private boolean contains(String accountType, String fName, String lName,
                             String date,String depositString, int campusCode, int customerStatus){

        try{double deposit = Double.parseDouble(depositString);}
        catch(NumberFormatException formatException){System.out.println("Not a valid amount"); return false;}

        double deposit = Double.parseDouble(depositString);

        Account newPerson = null;
        if(accountType.equals("C")) newPerson = new Checking(new Profile(fName, lName, new Date(date)), deposit);
        if(accountType.equals("CC")) newPerson = new CollegeChecking(new Profile(fName,lName,new Date(date)),deposit,Campus.getCampus(campusCode));
        if(accountType.equals("S")) newPerson = new Savings(new Profile(fName,lName,new Date(date)),deposit, customerStatus == 1);
        if(accountType.equals("MM")) newPerson = new MoneyMarket(new Profile(fName,lName,new Date(date)),deposit, true);

        return accountDatabase.contains(newPerson);
    }

    /**
     * Handles the closing of an account based on the provided data.
     *
     * @param accountType    The type of account.
     * @param fName          The first name of the account holder.
     * @param lName          The last name of the account holder.
     * @param date           The date of birth of the account holder.
     * @param depositString  The deposit amount as a string.
     * @param campusCode     The campus code (for college checking accounts).
     * @param customerStatus The customer status (1 for loyal, 0 for non-loyal).
     */
    public void closeCommand(String accountType, String fName, String lName,
                             String date,String depositString, int campusCode, int customerStatus){

        if(!checkValid(accountType,fName,lName,date,depositString,campusCode,customerStatus)) return;

        double deposit = Double.parseDouble(depositString);

        switch (accountType) {
            case "C":               // Checking
                Checking newChecking = new Checking(new Profile(fName,lName,new Date(date)),deposit);
                if(accountDatabase.close(newChecking)) System.out.println(newChecking.getHolder().toString()+"(C) has been closed.");
                else{System.out.println(String.format("%s %s %s(%s) is not in the database",fName,lName,date,accountType));}
                break;
            case "CC":              // College checking
                CollegeChecking newCollegeChecking = new CollegeChecking(new Profile(fName,lName,new Date(date)),deposit,Campus.getCampus(campusCode));
                if(accountDatabase.close(newCollegeChecking)) System.out.println(newCollegeChecking.getHolder().toString()+"(CC) has been closed.");
                else{System.out.println(String.format("%s %s %s(%s) is not in the database",fName,lName,date,accountType));}
                break;
            case "S":               // Savings
                Savings newSavings = new Savings(new Profile(fName,lName,new Date(date)),deposit, customerStatus == 1);
                if(accountDatabase.open(newSavings)) System.out.println(newSavings.getHolder().toString()+"(S) has been closed.");
                else{System.out.println(String.format("%s %s %s(%s) is not in the database",fName,lName,date,accountType));}
                break;
            case "MM":               // Money market savings
                MoneyMarket newMoneyMarket = new MoneyMarket(new Profile(fName,lName,new Date(date)),deposit, true);
                if(accountDatabase.open(newMoneyMarket)) System.out.println(newMoneyMarket.getHolder().toString()+"(MM) has been closed.");
                else{System.out.println(String.format("%s %s %s(%s) is not in the database",fName,lName,date,accountType));}
                break;
            default:
                break;
        }
    }


    /**
     * Handles the deposit of an amount to an account based on the provided data.
     *
     * @param accountType    The type of account.
     * @param fName          The first name of the account holder.
     * @param lName          The last name of the account holder.
     * @param date           The date of birth of the account holder.
     * @param depositString  The deposit amount as a string.
     * @param campusCode     The campus code (for college checking accounts).
     * @param customerStatus The customer status (1 for loyal, 0 for non-loyal).
     */
    public void depositCommand(String accountType, String fName, String lName,
                            String date,String depositString, int campusCode, int customerStatus) {

        if(!checkValid(accountType,fName,lName,date,depositString,campusCode,customerStatus)) return;

        double deposit = Double.parseDouble(depositString);

        if(deposit<=0){System.out.println("Deposit - amount cannot be 0 or negative."); return;}

        if(!contains(accountType,fName,lName,date,depositString,campusCode,customerStatus)) {
            System.out.println(String.format("%s %s %s(%s) is not in the database",fName,lName,date,accountType)); return;
        }

        switch (accountType) {
            case "C":               // Checking
                Checking newChecking = new Checking(new Profile(fName,lName,new Date(date)),deposit);
                if(accountDatabase.deposit(newChecking,deposit)) System.out.println(newChecking.getHolder().toString()+"(C) Deposit - balance updated.");
                break;
            case "CC":              // College checking
                CollegeChecking newCollegeChecking = new CollegeChecking(new Profile(fName,lName,new Date(date)),deposit,Campus.getCampus(campusCode));
                if(accountDatabase.deposit(newCollegeChecking,deposit)) System.out.println(newCollegeChecking.getHolder().toString()+"(CC) Deposit - balance updated.");
                break;
            case "S":               // Savings
                Savings newSavings = new Savings(new Profile(fName,lName,new Date(date)),deposit, customerStatus == 1);
                if(accountDatabase.deposit(newSavings,deposit)) System.out.println(newSavings.getHolder().toString()+"(S) Deposit - balance updated.");
                break;
            case "MM":               // Money market savings
                MoneyMarket newMoneyMarket = new MoneyMarket(new Profile(fName,lName,new Date(date)),deposit, true);
                if(accountDatabase.deposit(newMoneyMarket,deposit)) System.out.println(newMoneyMarket.getHolder().toString()+"(MM) Deposit - balance updated.");
                break;
            default:

                break;
        }
    }

    /**
     * Handles the withdrawal of an amount from an account based on the provided data.
     *
     * @param accountType    The type of account.
     * @param fName          The first name of the account holder.
     * @param lName          The last name of the account holder.
     * @param date           The date of birth of the account holder.
     * @param depositString  The deposit amount as a string.
     * @param campusCode     The campus code (for college checking accounts).
     * @param customerStatus The customer status (1 for loyal, 0 for non-loyal).
     */

    public void withdrawCommand(String accountType, String fName, String lName,
                                String date,String depositString, int campusCode, int customerStatus){
        if(!checkValid(accountType,fName,lName,date,depositString,campusCode,customerStatus)) return;

        double deposit = Double.parseDouble(depositString);

        if(!contains(accountType,fName,lName,date,depositString,campusCode,customerStatus)) {
            System.out.println(String.format("%s %s %s(%s) is not in the database",fName,lName,date,accountType)); return;
        }

        switch (accountType) {
            case "C":               // Checking
                Checking newChecking = new Checking(new Profile(fName,lName,new Date(date)),deposit);
                if(accountDatabase.withdraw(newChecking,deposit)) System.out.println(newChecking.getHolder().toString()+"(C) Withdraw - balance updated.");
                break;
            case "CC":              // College checking
                CollegeChecking newCollegeChecking = new CollegeChecking(new Profile(fName,lName,new Date(date)),deposit,Campus.getCampus(campusCode));
                if(accountDatabase.withdraw(newCollegeChecking,deposit)) System.out.println(newCollegeChecking.getHolder().toString()+"(CC) Withdraw - balance updated.");
                break;
            case "S":               // Savings
                Savings newSavings = new Savings(new Profile(fName,lName,new Date(date)),deposit, customerStatus == 1);
                if(accountDatabase.withdraw(newSavings,deposit)) System.out.println(newSavings.getHolder().toString()+"(S) Withdraw - balance updated.");
                break;
            case "MM":               // Money market savings
                MoneyMarket newMoneyMarket = new MoneyMarket(new Profile(fName,lName,new Date(date)),deposit, true);
                if(accountDatabase.withdraw(newMoneyMarket,deposit)) System.out.println(newMoneyMarket.getHolder().toString()+"(MM) Withdraw - balance updated.");
                break;
            default:
                break;
        }
    }
}
        