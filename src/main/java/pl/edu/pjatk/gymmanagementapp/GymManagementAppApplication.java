package pl.edu.pjatk.gymmanagementapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import pl.edu.pjatk.gymmanagementapp.configuration.RsaKeyProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class GymManagementAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(GymManagementAppApplication.class, args);
	}

}
