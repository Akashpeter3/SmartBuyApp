package com.app.shopping;

import com.app.shopping.dto.user.UserDTO;
import com.app.shopping.repository.AdminRepository;
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

//	@Autowired
//	private AdminRepository repository;
//
//	@PostConstruct
//	public void initUsers() {
//		repository.deleteAll();
//		List<UserDTO> userDTOS = Stream.of(
//			new UserDTO("peter","peter","peterparker3@gmail.com","palathaiparambu",
//					true,"Active","9496133256")
//		).collect(Collectors.toList());
//		repository.saveAll(userDTOS);
//	}

	public static void main(String[] args) {
		SpringApplication.run(SmartBuyApplication.class, args);
	}

}
