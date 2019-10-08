package ru.amalnev.solarium.library.console;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IoStreamConsole implements IConsole
{
    private final InputStreamReader reader;

    private final OutputStreamWriter writer;

    public IoStreamConsole(InputStream inputStream, OutputStream outputStream)
    {
        reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        writer = new OutputStreamWriter(outputStream, StandardCharsets.US_ASCII);
    }

    public IoStreamConsole(InputStream inputStream)
    {
        reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        writer = null;
    }

    @Override
    public void writeLine(String line) throws IOException
    {
        writer.write(line);
        writer.write("\n");
        writer.flush();
    }

    protected static String expectPattern(InputStreamReader reader, String pattern) throws IOException
    {
        final Pattern completionPattern = Pattern.compile(pattern);

        StringBuilder lineBuffer = new StringBuilder();
        final StringBuilder resultBuffer = new StringBuilder();
        while (true)
        {
            int nextCharacterInt = reader.read();
            if (nextCharacterInt == -1)
                break;

            char nextCharacter = (char) nextCharacterInt;

            lineBuffer.append(nextCharacter);
            final Matcher completionMatcher = completionPattern.matcher(lineBuffer.toString());
            if (completionMatcher.find())
            {
                resultBuffer.append(lineBuffer.toString());
                lineBuffer = new StringBuilder();
                for (int i = 0; i < completionMatcher.end() - completionMatcher.start(); i++)
                    if (resultBuffer.length() > 0)
                        resultBuffer.deleteCharAt(resultBuffer.length() - 1);
                break;
            }
        }

        resultBuffer.append(lineBuffer.toString());
        return resultBuffer.toString();
    }

    @Override
    public String expect(String pattern) throws IOException
    {
        return expectPattern(reader, pattern);
    }

    @Override
    public void close() throws IOException
    {
        if (reader != null) reader.close();
        if (writer != null) writer.close();
    }
}
