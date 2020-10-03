package pt.com.devdojo.awesome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pt.com.devdojo.awesome.model.User;
import pt.com.devdojo.awesome.repository.UserRepository;

//@EnableAutoConfiguration
//@ComponentScan
//@Configuration
@SpringBootApplication
public class ApplicationStart {

      public static void main(String [] args){
        SpringApplication.run(ApplicationStart.class, args);




    }
}
