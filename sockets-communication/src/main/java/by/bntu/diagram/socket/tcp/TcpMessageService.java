package by.bntu.diagram.socket.tcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("socket")
public class TcpMessageService {

//    private static final Logger LOGGER = LoggerFactory.getLogger(TcpSocket.class);

    @Value("${web.socket.send-to}")
    private String destination;

    @Autowired
    private SimpMessagingTemplate template;

    public String process(String input) {
        System.out.println("Message" + input);
        if ("FAIL".equals(input)) {
            throw new RuntimeException("Failure Demonstration");
        }
        this.template.convertAndSend(destination, input);
        return "echo:" + input;
    }

}
