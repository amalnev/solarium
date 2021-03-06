package ru.amalnev;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.amalnev.solarium.interpreter.Interpreter;
import ru.amalnev.solarium.interpreter.errors.InterpreterException;
import ru.amalnev.solarium.language.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class InterpreterTest
{
    @Parameterized.Parameter(0)
    public String fileName;

    @Parameterized.Parameter(1)
    public Object expectedResult;

    @Parameterized.Parameters
    public static List<Object[]> parameters()
    {
        return Arrays.asList(new Object[][] {
                {"/interpreter/interpreter-test-1.txt", 10},
                {"/interpreter/interpreter-test-2.txt", "test string"},
                {"/interpreter/interpreter-test-3.txt", 5},
                {"/interpreter/interpreter-test-4.txt", 1},
                {"/interpreter/interpreter-test-5.txt", 22},
                {"/interpreter/interpreter-test-6.txt", true},
                {"/interpreter/interpreter-test-7.txt", 160},
                {"/interpreter/interpreter-test-8.txt", 10},
                {"/interpreter/interpreter-test-9.txt", 10},
                {"/interpreter/interpreter-test-10.txt", 40},
                {"/interpreter/interpreter-test-11.txt", 1},
                {"/interpreter/interpreter-test-12.txt", 100},
                {"/interpreter/interpreter-test-13.txt", null},
                {"/interpreter/interpreter-test-14.txt", false},
                {"/interpreter/interpreter-test-15.txt", false},
                {"/interpreter/interpreter-test-16.txt", 10},
                {"/interpreter/interpreter-test-17.txt", null},
                {"/interpreter/interpreter-test-18.txt", "value1"},
                {"/interpreter/interpreter-test-19.txt", 70},
                {"/interpreter/interpreter-test-20.txt", 50},
                {"/interpreter/interpreter-test-21.txt", 20},
                {"/interpreter/interpreter-test-22.txt", 80},
                {"/interpreter/interpreter-test-23.txt", 10},
                {"/interpreter/interpreter-test-24.txt", true},
                {"/interpreter/interpreter-test-25.txt", 30},
                {"/interpreter/interpreter-test-26.txt", 20},
                {"/interpreter/interpreter-test-27.txt", true},
                {"/interpreter/interpreter-test-28.txt", "abcdef"},
                {"/interpreter/interpreter-test-29.txt", 30},
                {"/interpreter/interpreter-test-30.txt", "abc10"},
                {"/interpreter/interpreter-test-31.txt", "abc1020"},
                {"/interpreter/interpreter-test-32.txt", 50},
                {"/interpreter/interpreter-test-33.txt", 30},
                {"/interpreter/interpreter-test-34.txt", 10},
                {"/library/library-test-1.txt", 11},
                {"/library/library-test-2.txt", true},
                {"/library/library-test-3.txt", "11"},
                {"/library/library-test-5.txt", true},
                {"/library/library-test-6.txt", false},
                {"/library/library-test-7.txt", true},
                {"/interpreter/interpreter-test-35.txt", "123,null"},
                {"/interpreter/interpreter-test-36.txt", "123456"},
                {"/interpreter/interpreter-test-37.txt", false},
                {"/library/library-test-9.txt", true},
                {"/library/library-test-10.txt", true},
                {"/library/library-test-11.txt", "ASDFRG"},
                {"/library/library-test-12.txt", "Moskva"},
                {"/library/library-test-13.txt", "aaa_111___222_bbb"}
        });
    }

    @Test
    public void interpreterTest() throws IOException, ParserException, InterpreterException
    {
        final InputStream inputStream = getClass().getResourceAsStream(fileName);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder testCode = new StringBuilder();
        while(reader.ready())
        {
            testCode.append(reader.readLine());
        }

        final Object result = (new Interpreter()).runFromString(testCode.toString());
        Assert.assertEquals(result, expectedResult);
    }
}
