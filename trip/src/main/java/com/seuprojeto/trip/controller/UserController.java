package com.seuprojeto.trip.controller;  // O pacote onde está o UserController

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// Importando a classe User do pacote correto
import com.seuprojeto.trip.model.User;
import com.seuprojeto.trip.service.UserService;

@RestController  // Define que esta classe é um controlador REST no Spring
@RequestMapping("/api/users")  // Define o caminho base para os endpoints
public class UserController {

    @Autowired  // Injeção de dependência do UserService
    private UserService userService;


    // Endpoint simples de teste
    @GetMapping("/test")
    public String testEndpoint() {
        return "Aplicação está funcionando!";
    }

    // Endpoint para registrar um novo usuário
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        try {
            userService.save(user);  // Salva o usuário utilizando o serviço
            return ResponseEntity.status(HttpStatus.CREATED).body(user);  // Retorna 201 Created com o usuário
        } catch (Exception e) {
            e.printStackTrace();  // Imprime a exceção no log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao registrar usuário: " + e.getMessage());
        }
    }

    // Endpoint para realizar login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User logged = userService.login(user.getEmail(), user.getSenha());  // Verifica credenciais
        if (logged != null) {
            return ResponseEntity.ok(logged);  // Retorna 200 OK com o usuário logado
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("E-mail ou senha inválidos");  // Retorna 401 Unauthorized
    }

    // Endpoint para listar todos os usuários
    @GetMapping
    public List<User> getAll() {
        return userService.findAll();  // Retorna todos os usuários
    }




    // Endpoint de saúde para monitoramento da API (Render, UptimeRobot etc.)
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API Java está rodando!");  // Retorna 200 OK com mensagem de saúde
    }
}
