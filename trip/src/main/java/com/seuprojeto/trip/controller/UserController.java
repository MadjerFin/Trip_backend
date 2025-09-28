package com.seuprojeto.trip.controller;

import com.seuprojeto.trip.model.User;
import com.seuprojeto.trip.service.UserService;
import jakarta.validation.Valid; // ✅ trocado de javax -> jakarta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// (Opcional) libere CORS só para front-end conhecido
@CrossOrigin(origins = {"http://localhost:3000", "https://seu-frontend.exemplo"}, allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Healthcheck
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API Java está rodando!");
    }

    // Ping simples
    @GetMapping("/test")
    public String testEndpoint() {
        return "Aplicação está funcionando!";
    }

    // Registrar usuário
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        try {
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao registrar usuário: " + e.getMessage());
        }
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        var logged = userService.login(user.getEmail(), user.getSenha());
        if (logged != null) {
            return ResponseEntity.ok(logged);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("E-mail ou senha inválidos");
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}
