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
                {"/interpreter/interpreter-test-24.txt", true}
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
