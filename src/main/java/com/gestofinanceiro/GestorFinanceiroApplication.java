package com.gestofinanceiro;

import com.gestofinanceiro.model.Carteira;
import com.gestofinanceiro.model.Role;
import com.gestofinanceiro.model.Usuario;
import com.gestofinanceiro.repository.RoleRepository;
import com.gestofinanceiro.repository.UsuarioRepository;
import com.gestofinanceiro.services.CarteiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@EntityScan({"br.ufrn.imd.stonks.framework.framework.model", "com.gestofinanceiro.model"})
@EnableJpaRepositories({"br.ufrn.imd.stonks.framework.framework.repository","com.gestofinanceiro.repository"})
@ComponentScan({"com.gestofinanceiro", "br.ufrn.imd.stonks.framework.framework"})
@SpringBootApplication
public class GestorFinanceiroApplication implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	CarteiraService carteiraService;

	public static String EMAIL_ADMIN = "admin@gestorfinanceiro.com";
	public static String SENHA_ADMIN = "123456";

	public static void main(String[] args) {
		SpringApplication.run(GestorFinanceiroApplication.class, args);
	}

	public void run(String... var1) throws Exception {
		System.out.println("Iniciando configuração do banco.");

		Role siteUser = roleRepository.findByRole("SITE_USER");
		Role adminUser = roleRepository.findByRole("ADMIN_USER");
		Role superUser = roleRepository.findByRole("SUPER_USER");

		if(siteUser == null){
			siteUser = new Role("SITE_USER", "Permissao de acesso padrão do site");
			roleRepository.save(siteUser);
		}
		if(adminUser == null){
			adminUser = new Role("ADMIN_USER", "Permissao de acesso de administrador do site");
			roleRepository.save(adminUser);
		}
		if(superUser == null){
			superUser = new Role("SUPER_USER", "Permissao de acesso de super usuario do site");
			roleRepository.save(superUser);
		}

		Usuario usuarioAdmin = usuarioRepository.findByEmail(EMAIL_ADMIN);
		if (usuarioAdmin == null){
			usuarioAdmin = new Usuario("Admin", EMAIL_ADMIN, encoder.encode(SENHA_ADMIN),
					"000.000.000-00",true, new Date());
			usuarioRepository.save(usuarioAdmin);
			usuarioAdmin.setRoles(new HashSet<Role>(Arrays.asList(adminUser)));
			usuarioRepository.save(usuarioAdmin);
			Carteira carteira = new Carteira(usuarioAdmin);
			carteiraService.salvarCarteira(carteira);
		}

		System.out.println("Fim da configuração do banco.\n.\n.\n.");

		System.out.println("Login: "+EMAIL_ADMIN+"\nsenha: "+SENHA_ADMIN);

		System.out.println(".\n.\n.\n.\nAplicação Gestao financeira iniciada -> http://localhost:8080/login");
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
