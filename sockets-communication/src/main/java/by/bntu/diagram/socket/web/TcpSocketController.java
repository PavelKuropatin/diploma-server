package by.bntu.diagram.socket.web;

import by.bntu.diagram.socket.tcp.MessageProcessor;
import by.bntu.diagram.socket.tcp.TcpCommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Profile("socket")

@RestController
@RequestMapping("/api/socket")
public class TcpSocketController {

    @Autowired
    private MessageProcessor messageProcessor;

    private Map<String, TcpCommandExecutor> container = new HashMap<>();

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/connect")
    public void connectToSocket(@RequestParam(name = "host") String host, @RequestParam(name = "port") int port) throws IOException {

    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/disconnect")
    public void disconnectFromSocket(@RequestParam(name = "host") String host, @RequestParam(name = "port") int port) {

    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/start")
    public String startReceiveMessages(@RequestParam(name = "host") String host, @RequestParam(name = "port") int port, @RequestParam(name = "pause") int pause) {
        TcpCommandExecutor executor = new TcpCommandExecutor(container, host, port, "get", pause, messageProcessor);
        String uuid = executor.getUuid();
        new Thread(executor).start();
        container.put(uuid, executor);
        return uuid;
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/stop")
    public void stopReceiveMessages(@RequestBody String uuid) {
        TcpCommandExecutor executor = container.get(uuid);
        if (executor != null) {
            executor.stop();
            container.remove(uuid);
        }
    }
}
