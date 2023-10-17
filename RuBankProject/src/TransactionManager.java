import java.util.Scanner;
import java.util.Calendar;
/**
 * @author User
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

    private boolean checkLength(String command,String[] tokens){
        if(command.equals("C") && tokens.length < 5) {
            System.out.println("Missing data for closing an account.");
            return false;
        }
        if(command.equals("O") && tokens.length < 6) {
            System.out.println("Missing data for closing an account.");
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

    /** this method will handle all the COMMANDs stemming from O -> OPEN **/
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
                CollegeChecking testChecking = new CollegeChecking(new Profile(fName,lName,new Date(date)),deposit, Campus.CAMDEN);
                if(accountDatabase.contains(testChecking)){System.out.println(String.format("%s %s %s(%s) is already in the database",fName,lName,date,accountType)); return;}
                if(accountDatabase.open(newChecking)) System.out.println(newChecking.getHolder().toString()+"(C) opened");
                break;
            case "CC":              // College checking
                CollegeChecking newCollegeChecking = new CollegeChecking(new Profile(fName,lName,new Date(date)),deposit,Campus.getCampus(campusCode));
                Checking test2Checking = new Checking(new Profile(fName,lName,new Date(date)),deposit);
                if(accountDatabase.contains(test2Checking)){System.out.println(String.format("%s %s %s(%s) is already in the database",fName,lName,date,accountType)); return;}
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

    public void closeCommand(String accountType, String fName, String lName,
                             String date,String depositString, int campusCode, int customerStatus){
        if(!checkValid(accountType,fName,lName,date,depositString,campusCode,customerStatus)) return;
        if(!contains(accountType,fName,lName,date,depositString,campusCode,customerStatus)) {
            System.out.println(String.format("%s %s %s(%s) is not in the database",fName,lName,date,accountType)); return;
        }

        double deposit = Double.parseDouble(depositString);

        switch (accountType) {
            case "C":               // Checking
                Checking newChecking = new Checking(new Profile(fName,lName,new Date(date)),deposit);
                if(accountDatabase.close(newChecking)) System.out.println(newChecking.getHolder().toString()+"(C) has been closed.");
                else{System.out.println(String.format("%s %s %s(%s) is not in the database",fName,lName,date,accountType)); return;}
                break;
            case "CC":              // College checking
                CollegeChecking newCollegeChecking = new CollegeChecking(new Profile(fName,lName,new Date(date)),deposit,Campus.getCampus(campusCode));
                if(accountDatabase.close(newCollegeChecking)) System.out.println(newCollegeChecking.getHolder().toString()+"(CC) has been closed.");
                else{System.out.println(String.format("%s %s %s(%s) is not in the database",fName,lName,date,accountType)); return;}
                break;
            case "S":               // Savings
                Savings newSavings = new Savings(new Profile(fName,lName,new Date(date)),deposit, customerStatus == 1);
                if(accountDatabase.close(newSavings)) System.out.println(newSavings.getHolder().toString()+"(S) has been closed.");
                else{System.out.println(String.format("%s %s %s(%s) is not in the database",fName,lName,date,accountType)); return;}
                break;
            case "MM":               // Money market savings
                MoneyMarket newMoneyMarket = new MoneyMarket(new Profile(fName,lName,new Date(date)),deposit, true);
                if(accountDatabase.close(newMoneyMarket)) System.out.println(newMoneyMarket.getHolder().toString()+"(MM) has been closed.");
                else{System.out.println(String.format("%s %s %s(%s) is not in the database",fName,lName,date,accountType)); return;}
                break;
            default:
                break;
        }
    }

    public void depositCommand(String accountType, String fName, String lName,
                            String date,String depositString, int campusCode, int customerStatus) {

        if(!contains(accountType,fName,lName,date,depositString,campusCode,customerStatus)) {
            System.out.println(String.format("%s %s %s(%s) is not in the database",fName,lName,date,accountType)); return;
        }

        if(!checkValid(accountType,fName,lName,date,depositString,campusCode,customerStatus)) return;

        double deposit = Double.parseDouble(depositString);

        if(deposit<=0){System.out.println("Deposit - amount cannot be 0 or negative."); return;}

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

    public void withdrawCommand(String accountType, String fName, String lName,
                                String date,String depositString, int campusCode, int customerStatus){
        if(!contains(accountType,fName,lName,date,depositString,campusCode,customerStatus)) {
            System.out.println(String.format("%s %s %s(%s) is not in the database",fName,lName,date,accountType)); return;
        }

        if(!checkValid(accountType,fName,lName,date,depositString,campusCode,customerStatus)) return;

        double deposit = Double.parseDouble(depositString);

        switch (accountType) {
            case "C":               // Checking
                Checking newChecking = new Checking(new Profile(fName,lName,new Date(date)),deposit);
                if(accountDatabase.withdraw(newChecking,deposit)) System.out.println(newChecking.getHolder().toString()+"(C) Withdraw - balance updated.");
                else{System.out.println(newChecking.getHolder().toString()+"(C) Withdraw - insufficient fund."); return;}
                break;
            case "CC":              // College checking
                CollegeChecking newCollegeChecking = new CollegeChecking(new Profile(fName,lName,new Date(date)),deposit,Campus.getCampus(campusCode));
                if(accountDatabase.withdraw(newCollegeChecking,deposit)) System.out.println(newCollegeChecking.getHolder().toString()+"(CC) Withdraw - balance updated.");
                else{System.out.println(newCollegeChecking.getHolder().toString()+"(C) Withdraw - insufficient fund."); return;}
                break;
            case "S":               // Savings
                Savings newSavings = new Savings(new Profile(fName,lName,new Date(date)),deposit, customerStatus == 1);
                if(accountDatabase.withdraw(newSavings,deposit)) System.out.println(newSavings.getHolder().toString()+"(S) Withdraw - balance updated.");
                else{System.out.println(newSavings.getHolder().toString()+"(C) Withdraw - insufficient fund."); return;}
                break;
            case "MM":               // Money market savings
                MoneyMarket newMoneyMarket = new MoneyMarket(new Profile(fName,lName,new Date(date)),deposit, true);
                if(accountDatabase.withdraw(newMoneyMarket,deposit)) System.out.println(newMoneyMarket.getHolder().toString()+"(MM) Withdraw - balance updated.");
                else{System.out.println(newMoneyMarket.getHolder().toString()+"(C) Withdraw - insufficient fund."); return;}
                break;
            default:
                break;
        }
    }
}
        