package ru.amalnev.solarium.library.console;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

public class TelnetConnection implements Closeable, ISerialInterface
{
    private final Socket socket;

    public TelnetConnection(String host) throws IOException
    {
        socket = new Socket(host, 23);
        socket.setSoTimeout(5000);
    }

    public IConsole getConsole() throws IOException
    {
        return new IoStreamConsole(socket.getInputStream(), socket.getOutputStream());
    }

    @Override
    public void close() throws IOException
    {
        socket.close();
    }
}
