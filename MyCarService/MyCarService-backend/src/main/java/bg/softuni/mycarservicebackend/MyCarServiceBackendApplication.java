package bg.softuni.mycarservicebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyCarServiceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyCarServiceBackendApplication.class, args);
	}

}
