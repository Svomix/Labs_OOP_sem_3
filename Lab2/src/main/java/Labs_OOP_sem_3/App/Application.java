package Labs_OOP_sem_3.App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"Labs_OOP_sem_3.App", "Labs_OOP_sem_3.repositories", "Labs_OOP_sem_3.entities"})
@EntityScan(basePackages = {"Labs_OOP_sem_3.entities"})
@EnableJpaRepositories(basePackages = {"Labs_OOP_sem_3.repositories"})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}