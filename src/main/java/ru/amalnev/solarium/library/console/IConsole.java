package ru.amalnev.solarium.library.console;

public interface IConsole
{
    void writeLine(String line);

    String expect(String pattern);
}
