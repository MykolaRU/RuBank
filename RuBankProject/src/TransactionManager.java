import java.util.Scanner;
/**
 * @author User
 * Class representing a TransactionManager.
 */
public class TransactionManager {


    public TransactionManager(){
    }

    public void run(){                                  // RUN simply records user input
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to RU Bank. Enter a command below: ");

        while(true) {
            String accountType="", fName="", lName="", date="", deposit="";
            int campusCode = -1, customerStatus = -1;

            String userInput = scanner.nextLine().trim();
            if (userInput.equals("Q")) {
                System.out.println("Transaction Manager is terminated.");
                running = false;
            } else {
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
                    deposit = tokens[5];
                    if(tokens.length==7) campusCode = Integer.parseInt(tokens[6]);  // if 7 inputs, retrieve campus code
                    switch (command) {
                        case "O":
                            openCommand(accountType,fName,lName,date,deposit,campusCode,customerStatus);
                            break;
                            //TODO: MORE COMMANDS TO HANDLE
                        default:
                            System.out.println("Invalid command!");
                            break;
                    }
                }

            }
        }
    }

    /** this method will handle all the COMMANDs stemming from O -> OPEN **/
    public void openCommand(String accountType, String fName, String lName,
                            String date,String deposit, int campusCode, int customerStatus){

        //TODO: Create the accounts, all the paramaters are passed into the function

        switch (accountType) {
            case "C":               // Checking

                break;
            case "CC":              // College checking
               // openCommand(tokens);
                break;
            case "S":               // Savings
                //openCommand(tokens);
                break;
            case "MM":               // Money market savings
                //openCommand(tokens);
                break;
            default:
                System.out.println("OpenCommand sent an error;");
                break;
        }

    }
}
        