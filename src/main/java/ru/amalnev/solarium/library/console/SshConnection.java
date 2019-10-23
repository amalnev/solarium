package ru.amalnev.solarium.library.console;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;

public class SshConnection implements Closeable, ISerialInterface
{
    private final JSch jSch;

    private final Session session;

    private static class JSchConsole implements IConsole
    {
        private final Session session;

        private Channel channel = null;

        private InputStream inputStream = null;

        private PrintStream outputStream = null;

        public JSchConsole(Session session) throws IOException
        {
            try
            {
                this.session = session;
                channel = session.openChannel("shell");
                outputStream = new PrintStream(channel.getOutputStream(), true);
                inputStream = channel.getInputStream();
                channel.connect();
            }
            catch (JSchException e)
            {
                throw new IOException(e);
            }
        }

        @Override
        public void writeLine(String line)
        {
            outputStream.println(line);
        }

        @Override
        public String expect(String pattern) throws IOException
        {
            if (inputStream != null)
            {
                return IoStreamConsole.expectPattern(new InputStreamReader(inputStream), pattern);
            }

            return null;
        }

        @Override
        public void close() throws IOException
        {
            channel.disconnect();
        }
    }

    ;

    public SshConnection(String host, String username, String password) throws JSchException
    {
        jSch = new JSch();
        session = jSch.getSession(username, host);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();
    }

    @Override
    public void close() throws IOException
    {
        session.disconnect();
    }

    @Override
    public IConsole getConsole() throws IOException
    {
        return new JSchConsole(session);
    }
}
