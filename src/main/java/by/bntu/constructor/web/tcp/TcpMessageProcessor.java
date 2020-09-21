package by.bntu.constructor.web.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("socket")
public class TcpMessageProcessor implements MessageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpMessageProcessor.class);

    @Value("${web.socket.send-to}")
    private String destination;

    @Autowired
    private SimpMessagingTemplate template;

    public void process(String message) {
        LOGGER.info("process message : " + message);
        this.template.convertAndSend(destination, message);
    }

}
