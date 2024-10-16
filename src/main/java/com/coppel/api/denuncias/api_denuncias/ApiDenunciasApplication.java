package com.coppel.api.denuncias.api_denuncias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ApiDenunciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDenunciasApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// Configura CORS permitiendo múltiples orígenes
				registry.addMapping("/**")
					.allowedOrigins("http://localhost:5173", "https://portal-denuncias.vercel.app")
					.allowedMethods("*") // Permitir todos los métodos
					.allowedHeaders("*"); // Permitir todos los encabezados
			}
		};
	}
}
