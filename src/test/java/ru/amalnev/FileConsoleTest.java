package ru.amalnev;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.amalnev.solarium.library.console.IoStreamConsole;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class FileConsoleTest
{
    @Parameterized.Parameter(0)
    public String fileName;

    @Parameterized.Parameters
    public static List<Object[]> parameters()
    {
        return Arrays.asList(new Object[][]{
                {"/console-test-1.txt"}

        });
    }

    @Test
    public void runTest() throws IOException
    {
        final InputStream inputStream = getClass().getResourceAsStream(fileName);
        final IoStreamConsole console = new IoStreamConsole(inputStream);
        final String invitationPattern = "[a-z]+>";
        String result = console.expect(invitationPattern);
        System.out.println(result);
        result = console.expect(invitationPattern);
        System.out.println(result);
    }
}
