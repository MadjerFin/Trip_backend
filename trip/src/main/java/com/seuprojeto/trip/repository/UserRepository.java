package com.seuprojeto.trip.repository;  // Nome do pacote

import com.seuprojeto.trip.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // Marca esta classe como um repositório no Spring
public interface UserRepository extends JpaRepository<User, Long> {

    // Método para buscar um usuário pelo e-mail
    User findByEmail(String email);
}
