package ru.amalnev.solarium.interpreter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class Preprocessor
{
    private String includeDirectory;

    public String process(String source) throws IOException
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
                contentBuilder.append(source.substring(0, matcher.start()));
                try (Stream<String> stream = Files.lines(includePath, StandardCharsets.UTF_8))
                {
                    stream.forEach(s -> contentBuilder.append(s).append("\n"));
                }
                contentBuilder.append(source.substring(matcher.end() + 1, source.length()));
                source = contentBuilder.toString();
            }
        }
        while (matchFound);

        return source;
    }
}
