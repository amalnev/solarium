package ru.amalnev;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.amalnev.solarium.interpreter.Program;
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
                /*{"/interpreter-test-1.txt"},
                {"/start-stop-firefox.txt"},*/
                /*{"/yandex-search.txt"},*/
                {"/interpreter-test-1.txt", 10},
                {"/interpreter-test-2.txt", "test string"},
                {"/interpreter-test-3.txt", 5},
                {"/interpreter-test-4.txt", 1},
                {"/interpreter-test-5.txt", 22},
                {"/interpreter-test-6.txt", true},
                {"/interpreter-test-7.txt", 16},
                {"/interpreter-test-8.txt", 10},
                {"/interpreter-test-9.txt", 100},
                {"/interpreter-test-10.txt", 40},
                {"/interpreter-test-11.txt", 1},
                {"/interpreter-test-12.txt", 100}
        });
    }

    @Test
    public void interpreterTest() throws IOException, ParserException
    {
        final InputStream inputStream = getClass().getResourceAsStream(fileName);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder testCode = new StringBuilder();
        while(reader.ready())
        {
            testCode.append(reader.readLine());
        }

        final Object result = Program.runFromString(testCode.toString());
        Assert.assertEquals(result, expectedResult);
    }
}
