package com.myblog.blog;

import com.myblog.blog.model.Role;
import com.myblog.blog.model.User;
import com.myblog.blog.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthService service
	) {
		return args -> {
			var admin = User.builder()
					.name("Admin")
					.surname("Admin")
					.email("admin@gmail.com")
					.login("Admin")
					.password("password")
					.telephone("")
					.role(Role.ADMIN)
					.build();


			var moderator = User.builder()
					.name("Admin")
					.surname("Admin")
					.login("Moderator")
					.email("moderator@gmail.com")
					.telephone("")
					.password("password")
					.role(Role.MODERATOR)
					.build();

			System.out.println("Admin token: " + service.register(admin).getToken());
			System.out.println("Moderator token: " + service.register(moderator).getToken());
		};
	}

}
