package com.seuprojeto.trip.service;

import com.seuprojeto.trip.model.User;
import com.seuprojeto.trip.repository.UserRepository;
import com.seuprojeto.trip.exception.ApiException;  // Importando a exceção personalizada
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Método para salvar um usuário
    @Transactional
    public void save(User user) {
        // Verifica se o e-mail já está registrado
        if (userRepository.findByEmail(user.getEmail()) != null) {
            // Lança uma exceção personalizada se o e-mail já existe
            throw new ApiException("Usuário com este e-mail já existe.");
        }
        // Se não existir, salva o usuário no banco de dados
        userRepository.save(user);
    }

    // Método para listar todos os usuários
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Método de login
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getSenha().equals(password)) {
            return user;
        }
        return null;
    }
}
