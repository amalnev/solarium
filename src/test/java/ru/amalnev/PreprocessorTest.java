package ru.amalnev;

import org.junit.Assert;
import org.junit.Test;
import ru.amalnev.solarium.interpreter.Preprocessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class PreprocessorTest
{
    private String readResourceFile(String fileName) throws IOException
    {
        final InputStream inputStream = getClass().getResourceAsStream(fileName);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder builder = new StringBuilder();
        while (reader.ready())
        {
            builder.append(reader.readLine());
            builder.append("\n");
        }
        return builder.toString();
    }

    private void createFile(Path path, String contents) throws IOException
    {
        Files.write(path,
                    new ArrayList<String>()
                    {{
                        add(contents);
                    }},
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE_NEW);
    }

    @Test
    public void runTest() throws IOException
    {
        //createFile(Paths.get("preprocessor-test-1.txt"), readResourceFile("preprocessor-test-1.txt"));
        createFile(Paths.get("preprocessor-test-2.txt"), readResourceFile("/preprocessor/preprocessor-test-2.txt"));
        Files.createDirectory(Paths.get("test-3"));
        createFile(Paths.get("test-3/preprocessor-test-3.txt"), readResourceFile("/preprocessor/preprocessor-test-3.txt"));
        createFile(Paths.get("preprocessor-test-4.txt"), readResourceFile("/preprocessor/preprocessor-test-4.txt"));

        Preprocessor preprocessor = new Preprocessor();
        String result = preprocessor.process(readResourceFile("/preprocessor/preprocessor-test-1.txt"));

        Files.deleteIfExists(Paths.get("preprocessor-test-2.txt"));
        Files.deleteIfExists(Paths.get("test-3/preprocessor-test-3.txt"));
        Files.deleteIfExists(Paths.get("test-3"));
        Files.deleteIfExists(Paths.get("preprocessor-test-4.txt"));

        result = result.replace("\n", "");
        Assert.assertEquals(result, "123456789");
    }
}
