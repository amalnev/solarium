package ru.amalnev.solarium.interpreter;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Getter
@Setter
public class Preprocessor
{
    private Path currentDirectory;

    public Preprocessor(String currentDirectory)
    {
        this.currentDirectory = Paths.get(currentDirectory);
        if (!this.currentDirectory.isAbsolute())
            this.currentDirectory = this.currentDirectory.toAbsolutePath();
    }

    public String processFile(String file) throws IOException
    {
        Path filePath = Paths.get(file);
        if (!filePath.isAbsolute())
            filePath = currentDirectory.resolve(filePath);

        final StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(filePath, StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }

        final Preprocessor preprocessor = new Preprocessor(filePath.getParent().toString());
        return preprocessor.processCode(contentBuilder.toString());
    }

    public String processCode(String sourceCode) throws IOException
    {
        final Pattern includePattern = Pattern.compile("#include\\s*\\(\"([^)\r\n]+)\"\\);");
        boolean matchFound = false;
        do
        {
            final Matcher matcher = includePattern.matcher(sourceCode);
            matchFound = matcher.find();
            if (matchFound)
            {
                Path includePath = Paths.get(matcher.group(1));

                final StringBuilder contentBuilder = new StringBuilder();
                contentBuilder.append(sourceCode, 0, matcher.start());
                contentBuilder.append(processFile(includePath.toString()));
                contentBuilder.append(sourceCode.substring(matcher.end() + 1));
                sourceCode = contentBuilder.toString();
            }
        }
        while (matchFound);

        return sourceCode;
    }

    //private String includeDirectory;

    /*public String process(String source) throws IOException
    {
        final Pattern includePattern = Pattern.compile("#include\\s*\\(\"([^)\r\n]+)\"\\);");
        boolean matchFound = false;
        do
        {
            final Matcher matcher = includePattern.matcher(source);
            matchFound = matcher.find();
            if (matchFound)
            {
                Path includePath = Paths.get(matcher.group(1));
                if (!includePath.isAbsolute())
                {
                    if (includeDirectory != null && includeDirectory.length() > 0)
                        includePath = Paths.get(includeDirectory).resolve(includePath);
                    else
                        includePath = includePath.toAbsolutePath();
                }

                final StringBuilder contentBuilder = new StringBuilder();
                contentBuilder.append(source, 0, matcher.start());
                try (Stream<String> stream = Files.lines(includePath, StandardCharsets.UTF_8))
                {
                    stream.forEach(s -> contentBuilder.append(s).append("\n"));
                }
                contentBuilder.append(source.substring(matcher.end() + 1));
                source = contentBuilder.toString();
            }
        }
        while (matchFound);

        return source;
    }*/
}
