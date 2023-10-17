import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {

    @Test
    void testDaysInFebNonLeap() {
        Date date = new Date("02/29/1995");
        assertFalse(date.isValid());
    }

    @Test
    void testDaysInFebLeap(){
        Date date = new Date("02/29/2000");
        assertTrue(date.isValid());
    }

    @Test
    void testDaysInJanTooMany(){
        Date date = new Date("01/32/2000");
        assertFalse(date.isValid());
    }

    @Test
    void testYearTooLow(){
        Date date = new Date("03/32/1");
        assertFalse(date.isValid());
    }

    @Test
    void testMonthOutOfBounds(){
        Date date = new Date("13/32/2000");
        assertFalse(date.isValid());
    }

    @Test
    void negativeDays(){
        Date date = new Date("6/-1/2000");
        assertFalse(date.isValid());
    }
    @Test
    void myBirthday(){
        Date date = new Date("1/25/2003");
        assertTrue(date.isValid());
    }
}