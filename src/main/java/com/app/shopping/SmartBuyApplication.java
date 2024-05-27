package com.app.shopping;

import com.app.shopping.dto.user.User;
import com.app.shopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableSwagger2
@SpringBootApplication
public class SmartBuyApplication {

	@Autowired
	private UserRepository repository;

	@PostConstruct
	public void initUsers() {
		repository.deleteAll();
		List<User> users = Stream.of(
			new User("peter","peter","peterparker3@gmail.com","palathaiparambu",
					true,"Active","9496133256")
		).collect(Collectors.toList());
		repository.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(SmartBuyApplication.class, args);
	}

}
