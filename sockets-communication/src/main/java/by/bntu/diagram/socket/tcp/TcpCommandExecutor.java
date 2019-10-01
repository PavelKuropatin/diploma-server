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
    private int pause;
    private String uuid;

    public TcpCommandExecutor(Map<String, TcpCommandExecutor> container, String host, int port, String command, int pause, boolean isInfinite) {
        this.uuid = UUID.randomUUID().toString();
        this.container = container;
        this.host = host;
        this.port = port;
        this.command = command;
        this.isRun = isInfinite;
        this.pause = pause;
    }

    public TcpCommandExecutor(Map<String, TcpCommandExecutor> container, String host, int port, String command, int pause) {
        this(container, host, port, command, pause, true);
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

                LOGGER.info("Send " + command);
                response = sendMessage(in, out);
                LOGGER.info("response " + response);

                Thread.sleep(pause);

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
