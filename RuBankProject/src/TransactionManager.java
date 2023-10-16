import java.util.Scanner;
/**
 * @author User
 * Class representing a TransactionManager.
 */
public class TransactionManager {

    private AccountDatabase accountDatabase;
    public static final int MONEY_MARKET_MIN_DEPOSIT = 2000;


    public TransactionManager(){
    }

    public void run(){                                  // RUN simply records user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to RU Bank. Enter a command below: ");

        while(true) {
            String accountType="", fName="", lName="", date="", deposit="";
            int campusCode = -1, customerStatus = -1;

            String userInput = scanner.nextLine().trim();
            if (userInput.equals("Q")) {
                System.out.println("Transaction Manager is terminated.");
                break;
            }

            String[] tokens = userInput.split("\\s+");
            String command = tokens[0];
            if(tokens.length<5 || tokens.length>7){
                System.out.println("Error: too many or too few tokens entered!");
            }
            else {
                accountType = tokens[1];
                fName = tokens[2];
                lName = tokens[3];
                date = tokens[4];
                if(tokens.length>5) deposit = tokens[5];
                if(tokens.length==7) campusCode = Integer.parseInt(tokens[6]);  // if 7 inputs, retrieve campus code
                switch (command) {
                    case "O":
                        openCommand(accountType,fName,lName,date,deposit,campusCode,customerStatus);
                        break;
                    case "C":
                        closeCommand(accountType,fName,lName,date,deposit,campusCode,customerStatus);

                        //TODO: MORE COMMANDS TO HANDLE
                    default:
                        System.out.println("Invalid command!");
                        break;
                }
            }

        }
    }

    /** this method will handle all the COMMANDs stemming from O -> OPEN **/
    public void openCommand(String accountType, String fName, String lName,
                            String date,String depositString, int campusCode, int customerStatus){

        int deposit = Integer.parseInt(depositString);

        switch (accountType) {
            case "C":               // Checking
                accountDatabase.open(new Checking(new Profile(fName,lName,new Date(date)),deposit));
                break;
            case "CC":              // College checking
                accountDatabase.open(new CollegeChecking(new Profile(fName,lName,new Date(date)),deposit, Campus.getCampus(campusCode)));
                break;
            case "S":               // Savings
                accountDatabase.open(new Savings(new Profile(fName,lName,new Date(date)),deposit, customerStatus == 1));
                break;
            case "MM":               // Money market savings
                if(deposit < MONEY_MARKET_MIN_DEPOSIT) {
                    System.out.println("Minimum of $2000 to open a Money Market account.");
                    break;
                }
                accountDatabase.open(new MoneyMarket(new Profile(fName,lName,new Date(date)),deposit, true));
                break;
            default:

                break;
        }

    }

    public void closeCommand(String accountType, String fName, String lName,
                             String date,String depositString, int campusCode, int customerStatus){

        int deposit = Integer.parseInt(depositString);

        switch (accountType) {
            case "C":               // Checking
                accountDatabase.close(new Checking(new Profile(fName,lName,new Date(date)),deposit));
                break;
            case "CC":              // College checking
                accountDatabase.close(new CollegeChecking(new Profile(fName,lName,new Date(date)),deposit, Campus.getCampus(campusCode)));
                break;
            case "S":               // Savings
                accountDatabase.close(new Savings(new Profile(fName,lName,new Date(date)),deposit, customerStatus == 1));
                break;
            case "MM":               // Money market savings
                accountDatabase.close(new MoneyMarket(new Profile(fName,lName,new Date(date)),deposit, true));
                break;
            default:

                break;
        }
    }

}
        