package by.bntu.diagram.socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Profile("socket")
@Configuration
@ImportResource("classpath:META-INF/integration/tcp-context.xml")
public class TcpSocketConfig {

//    @Bean
//    public String azaz(){
//        return "asad";
//    }
}
