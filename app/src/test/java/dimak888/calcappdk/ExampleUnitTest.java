package dimak888.calcappdk;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    ArbeitenRPN schnelle = new ArbeitenRPN();
    @Test
    public void main_Test() throws Exception{
        assertEquals(157.7727, schnelle.outputResult("1+2*5 - 5/22 + (45 +34)*3"));
    }
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, schnelle.outputResult("2+2"));
    }
    @Test
    public void subtraction_isCorrect() throws Exception {
        assertEquals(-111,schnelle.outputResult("854-965"));
    }
    @Test
    public void multiplication_isCorrect() throws Exception {
        assertEquals(774, schnelle.outputResult("3*258"));
    }
    @Test
    public void division_isCorrect() throws Exception {
        assertEquals(18.5185, schnelle.outputResult("500/27"));
    }
}