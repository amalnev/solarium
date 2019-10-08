package ru.amalnev.solarium.library.console;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SshConnection implements Closeable, ISerialInterface
{
    private final JSch jSch;

    private final Session session;

    private static class JSchConsole implements IConsole
    {
        private final Session session;

        private ChannelExec channel = null;

        private InputStream inputStream = null;

        public JSchConsole(Session session)
        {
            this.session = session;
        }

        @Override
        public void writeLine(String line) throws IOException
        {
            try
            {
                if (channel != null)
                    channel.disconnect();

                channel = (ChannelExec) session.openChannel("exec");
                channel.setCommand(line);
                channel.setInputStream(null);
                inputStream = channel.getInputStream();
                channel.connect();

            }
            catch (JSchException e)
            {
                throw new IOException(e);
            }
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
