import com.sun.tools.javac.util.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SILab2Test {
    private static SILab2 lab = new SILab2();
    /*
    * This won't break the program since
    * it is not used in the function,
    * you only need to have the correct type so no unit tests will be required
    * since the bug will be appear in compile time
     */
    private static List<String> randomNoUseList = new ArrayList<>();

    private enum TEST_TYPE {
        THROWS, FALSE, TRUE
    };

    private static Pair<User, TEST_TYPE> CreateTest(String user, String pass, String mail, TEST_TYPE type) {
        return new Pair<>(new User(user, pass, mail), type);
    }

    private static void ProcessTest(Pair<User, TEST_TYPE> test)
    {
        switch (test.snd)
        {
            case THROWS:
                try {
                    assertThrows(RuntimeException.class, () -> lab.function(test.fst, randomNoUseList));
                }catch (RuntimeException ex){
                    System.err.println(ex.getMessage().toString());
                }
                break;
            case FALSE:
                assertFalse(lab.function(test.fst, randomNoUseList));
                break;
            case TRUE:
                assertTrue(lab.function(test.fst, randomNoUseList));
                break;
        }
    }

    @Test
    public void EveryBranch() {
        SILab2 o = new SILab2();

        List<Pair<User, TEST_TYPE>> Tests = new ArrayList<>();

        Tests.add(CreateTest("wolfie","ss412000","stojanovangeloutlook.com", TEST_TYPE.FALSE));
        Tests.add(CreateTest("wolfie","ss412000","stojanovangel@outlookcom", TEST_TYPE.FALSE));
        Tests.add(CreateTest("wolfie","ss412000","stojanovangel@@outlookcom", TEST_TYPE.FALSE));
        Tests.add(CreateTest("wolfie","ss412000","stojanovangel@@outlook.com", TEST_TYPE.FALSE));
        Tests.add(CreateTest("wolfie","ss412000","stojanovangeloutlookcom", TEST_TYPE.FALSE));
        Tests.add(CreateTest("wolfie", "ss412000", "stojanovangel@outlook.com", TEST_TYPE.TRUE));


        Tests.forEach(SILab2Test::ProcessTest);
    }

    @Test
    public void MultipleCoverage() {
        List<Pair<User, TEST_TYPE>> Tests = new ArrayList<>();

        Tests.add(CreateTest(null, null, "stojanovangel@outlook.com", TEST_TYPE.THROWS));
        Tests.add(CreateTest(null, "ss412000", "stojanovangel@outlook.com", TEST_TYPE.THROWS));
        Tests.add(CreateTest("wolfie","ss412000","stojanovangeloutlook.com", TEST_TYPE.FALSE));
        Tests.add(CreateTest(null, null, null, TEST_TYPE.THROWS));
        Tests.add(CreateTest("wolfie","ss412000","stojanovangel@@outlookcom", TEST_TYPE.FALSE));
        Tests.add(CreateTest("wolfie","ss412000","stojanovangel@@outlook.com", TEST_TYPE.FALSE));
        Tests.add(CreateTest("wolfie","ss412000","stojanovangel@outlookcom", TEST_TYPE.FALSE));
        Tests.add(CreateTest("wolfie", "ss412000", "stojanovangel@outlook.com", TEST_TYPE.TRUE));

        Tests.forEach(SILab2Test::ProcessTest);
    }
}

