package com.aarteaga.msvc.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringbootMsvcCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMsvcCursosApplication.class, args);
	}

}
