package ru.amalnev.solarium.library.console;

import java.io.Closeable;
import java.io.IOException;

public interface IConsole extends Closeable
{
    /**
     * Send a line of text to the serial device.
     *
     * @param line
     */
    void writeLine(String line) throws IOException;

    /**
     * Read from a serial device until a pattern is matched.
     * Returns everything that was read before the text matching the pattern.
     *
     * @param pattern
     * @return
     */
    String expect(String pattern) throws IOException;
}
