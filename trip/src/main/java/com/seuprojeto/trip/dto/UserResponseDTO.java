package com.seuprojeto.trip.dto;

import com.seuprojeto.trip.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para retornar dados do usuário sem expor a senha
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String nome;
    private String email;

    // Construtor que converte User para UserResponseDTO
    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.nome = user.getNome();
        this.email = user.getEmail();
    }

    // Método estático para facilitar a conversão
    public static UserResponseDTO fromUser(User user) {
        return new UserResponseDTO(user);
    }
}
