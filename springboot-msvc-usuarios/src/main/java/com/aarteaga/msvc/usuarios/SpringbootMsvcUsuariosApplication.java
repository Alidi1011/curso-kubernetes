package com.aarteaga.msvc.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringbootMsvcUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMsvcUsuariosApplication.class, args);
	}

}
