package nts.sixblack.findservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FindServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FindServiceApplication.class, args);
    }

}
