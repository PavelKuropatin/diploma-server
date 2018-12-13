package by.bntu.diploma.diagram.web.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class SocketController {

    private SimpMessagingTemplate template;

    @Autowired
    public SocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/hello/{uuid}")
    public void process(@DestinationVariable String uuid, Message from) {
        System.out.println(from);
        Message to = new Message("Hello, " + uuid + " - " + HtmlUtils.htmlEscape(from.getText()) + "!");
        this.template.convertAndSend("/topic/greetings/" + uuid, to);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Message {

        @JsonProperty("text")
        private String text;
    }
}