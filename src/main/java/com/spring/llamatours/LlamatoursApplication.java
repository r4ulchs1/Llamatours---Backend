package com.spring.llamatours;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.llamatours.enums.RolNombre;
import com.spring.llamatours.model.Usuario;
import com.spring.llamatours.repos.UsuarioRepo;

@SpringBootApplication
public class LlamatoursApplication {

	public static void main(String[] args) {
		SpringApplication.run(LlamatoursApplication.class, args);
	}

	@Bean
	public CommandLineRunner crearAdminSiNoExiste(UsuarioRepo repo, PasswordEncoder encoder) {
		return args -> {
			String email = "admin@llamatours.com";
			if (repo.findByEmail(email).isEmpty()) {
				Usuario admin = new Usuario();
				admin.setNombre("Administrador");
				admin.setEmail(email);
				admin.setPassword(encoder.encode("admin123"));
				admin.setTelefono("999999999");
				admin.setRol(RolNombre.ADMIN);
				admin.setFechaRegistro(LocalDateTime.now());
				repo.save(admin);
				System.out.println("✅ Usuario admin creado con éxito.");
			} else {
				System.out.println("ℹ️ Usuario admin ya existe.");
			}
		};
	}

}
