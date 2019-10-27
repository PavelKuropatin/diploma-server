package by.bntu.diagram.socket.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;

public class TcpCommandExecutor implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpCommandExecutor.class);
    private Map<String, TcpCommandExecutor> container;
    private String host;
    private int port;
    private String command;
    private boolean isRun;
    private int times;
    private int interval;
    private String uuid;
    private MessageProcessor processor;

    public TcpCommandExecutor(Map<String, TcpCommandExecutor> container, String host, int port, String command, int interval, MessageProcessor processor, boolean isInfinite) {
        this.uuid = UUID.randomUUID().toString();
        this.processor = processor;
        this.container = container;
        this.host = host;
        this.port = port;
        this.command = command;
        this.isRun = isInfinite;
        this.interval = interval;
    }

    public TcpCommandExecutor(Map<String, TcpCommandExecutor> container, String host, int port, String command, int interval, MessageProcessor processor) {
        this(container, host, port, command, interval, processor, true);
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public void run() {
        try (Socket clientSocket = new Socket(host, port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String response;

            do {

                LOGGER.info("send: " + command);
                response = sendMessage(in, out);

                LOGGER.info("process: " + response);
                processor.process(response);

                LOGGER.info("start interval..");
                Thread.sleep(interval);

            } while (isRun);

            LOGGER.info("Success finished Command(" + uuid + ")");

        } catch (IOException | InterruptedException e) {
            LOGGER.error("Error :", e);
        } finally {
            container.remove(uuid);
        }
    }

    public void stop() {
        isRun = false;
        LOGGER.info("Manual stop Command(" + uuid + ")");
    }

    private String sendMessage(BufferedReader in, PrintWriter out) throws IOException {
        out.println(command);
        return in.readLine();
    }

}
