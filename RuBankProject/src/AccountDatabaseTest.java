import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountDatabaseTest {

    @Test
    void accountInDatabase() {
        Checking testChecking1 = new Checking(new Profile("Ethan","DiVine",new Date("12/25/2000")),100);
        Checking testChecking2 = new Checking(new Profile("Ethan","DiVine",new Date("12/25/2000")),100);
        AccountDatabase accountDatabase = new AccountDatabase();
        accountDatabase.open(testChecking1);
        assertTrue(accountDatabase.close(testChecking2));
    }

    @Test
    void accountNotInDatabase() {
        Checking testChecking1 = new Checking(new Profile("Ethan","DiVine",new Date("12/25/2000")),100);
        Checking testChecking2 = new Checking(new Profile("Grethan","BroVine",new Date("12/23/2000")),100);
        AccountDatabase accountDatabase = new AccountDatabase();
        accountDatabase.open(testChecking1);
        assertFalse(accountDatabase.close(testChecking2));
    }
}