import java.util.Calendar;

/**
 Represents a date object, containing day, month, and year,
 and provides functionalities to validate the date, determine leap years,
 and compare two Date objects.

 @author Ethan, Mykola
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    // Constants representing the number of years and days for leap year calculations
    // and the number of days in each month
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int JANUARY_DAYS = 31;
    public static final int FEBRUARY_DAYS  = 28;
    public static final int FEBRUARY_LEAP_YEAR_DAYS  = 29;
    public static final int MARCH_DAYS  = 31;
    public static final int APRIL_DAYS  = 30;
    public static final int MAY_DAYS  = 31;
    public static final int JUNE_DAYS  = 30;
    public static final int JULY_DAYS  = 31;
    public static final int AUGUST_DAYS  = 31;
    public static final int SEPTEMBER_DAYS  = 30;
    public static final int OCTOBER_DAYS  = 31;
    public static final int NOVEMBER_DAYS  = 30;
    public static final int DECEMBER_DAYS  = 31;
    public static final int JANUARY = 1;
    public static final int DECEMBER = 12;
    public static final int FIRST_DAY_OF_MONTH = 1;
    public static final int CURRENT_YEAR = 2023;

    /**
     Initializes a new Date object from a string representation in the form MM/DD/YYYY.

     @param date a string representation of the date
     */
    public Date(String date){
        String[] dateArray = date.split("/");
        this.month = Integer.parseInt(dateArray[0]);
        this.day = Integer.parseInt(dateArray[1]);
        this.year = Integer.parseInt(dateArray[2]);
    }

    /**
     Retrieves the day of the month.

     @return the day of the month
     */
    public int getDay(){
        return this.day;
    }

    /**
     Retrieves the month of the year.

     @return the month of the year
     */
    public int getMonth(){
        return this.month;
    }

    /**
     Retrieves the year.

     @return the year
     */
    public int getYear(){
        return this.year;
    }

    /**
     Updates the year of the date.

     @param year the new year
     */
    public void setYear(int year){
        this.year = year;
    }

    /**
     Updates the month of the date.

     @param month the new month
     */
    public void setMonth(int month){
        this.month = month;
    }

    /**
     Updates the day of the month.

     @param day the new day of the month
     */
    public void setDay(int day){
        this.day = day;
    }

    /**
     Returns a string representation of the Date object in the format MM/DD/YYYY.

     @return a string representation of the date
     */
    @Override
    public String toString(){
        return (getMonth() + "/" + getDay() + "/" + getYear());
    }

    /**
     Validates the date to ensure it corresponds to a valid calendar date, considering the month, day, and leap years.

     @return true if the date is valid, false otherwise
     */
    public boolean isValid(){
        if(month < JANUARY || month > DECEMBER) return false;

        int[] daysInMonth = new int[] {JANUARY_DAYS, FEBRUARY_DAYS, MARCH_DAYS, APRIL_DAYS, MAY_DAYS, JUNE_DAYS, JULY_DAYS, AUGUST_DAYS, SEPTEMBER_DAYS, OCTOBER_DAYS, NOVEMBER_DAYS, DECEMBER_DAYS};

        if(month == 2 && IsLeapYear(year)) {
            return day >= FIRST_DAY_OF_MONTH && day <= FEBRUARY_LEAP_YEAR_DAYS;
        }

        if (day > daysInMonth[month-1] || day < FIRST_DAY_OF_MONTH) return false;

        return true;
    }

    /**
     Determines if the given year is a leap year according to the Gregorian calendar.

     @param year the year to be checked
     @return true if the year is a leap year, false otherwise
     */
    private boolean IsLeapYear(int year){
        if (year % QUADRENNIAL != 0) return false;
        if(year % CENTENNIAL != 0) return true;
        return year % QUATERCENTENNIAL == 0;
    }

    /**
     Compares this Date object to another Date object for ordering.

     @param other the other Date object to compare to
     @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Date other) {
        if (this.year > other.year) return 1;
        if (this.year < other.year) return -1;
        if (this.month > other.month) return 1;
        if (this.month < other.month) return -1;
        if (this.day > other.day) return 1;
        if (this.day < other.day) return -1;
        return 0;
    }

    /**
     * Calculates the age based on the given date of birth.
     * @return age based on the date
     */
    public int calculateAge() {
        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1; // 0-based index
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

        int age = currentYear - this.year;

        // If the birthday hasn't occurred this year yet, subtract 1 from the age.
        if (this.month > currentMonth || (this.month == currentMonth && this.day > currentDay)) {
            age--;
        }

        return age;
    }

    /** TEST CASES FOR isValid() METHOD */
    public static void main(String[] args) {
        // Test for isValid() method
        testDaysInFeb_NonLeap();
        testDaysInFeb_Leap();
        testDay_OutOfRange();
        testMonth_OutOfRange();
        testValidDate();

    }

    /** Test case 1 */
    private static void testDaysInFeb_NonLeap(){
        Date date = new Date("2/29/2011");//test data
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #1: # of days in Feb. in a non-leap year is 28");
        testResult(date,expectedOutput,actualOutput);

    }

    /** Test case 2 */
    private static void testDaysInFeb_Leap(){
        Date date = new Date("2/29/2020"); // Leap year
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        System.out.println("Test case #2: # of days in Feb. in a leap year is 29");
        testResult(date,expectedOutput,actualOutput);
    }

    /** Test case 3 */
    private static void testDay_OutOfRange(){
        Date date = new Date("4/31/2020"); // April has 30 days
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("Test case #3: # of days in April is 30");
        testResult(date,expectedOutput,actualOutput);
    }

    /** Test case 4 */
    private static void testMonth_OutOfRange(){
        Date date = new Date("13/32/2020"); // Only 12 months in a year
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #4: # of months in year is 12");
        testResult(date,expectedOutput,actualOutput);
    }

    /** Test case 5 */
    private static void testValidDate(){
        Date date = new Date("5/15/2020"); // Only 12 months in a year
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #5: valid day");
        testResult(date,expectedOutput,actualOutput);
    }

    /** Check if a given test case PASS or FAIL */
    private static void testResult(Date date, boolean expectedOutput, boolean actualOutput){
        System.out.println(date.toString() + ": " + (expectedOutput == actualOutput));
    }


}