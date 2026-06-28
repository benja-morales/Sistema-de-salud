package com.consultorio.msfacturacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsfacturacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsfacturacionApplication.class, args);
	}

}
