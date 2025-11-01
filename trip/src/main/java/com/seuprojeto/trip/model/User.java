package com.seuprojeto.trip.model;

import jakarta.persistence.*;  // Pacote de persistência utilizado no Spring, mantendo a compatibilidade com Jakarta EE
import jakarta.validation.constraints.*;  // Validações
import lombok.*;  // Importando o Lombok

@Entity  // Define que esta classe é uma entidade JPA (persistência de dados no banco)
@Table(name = "users")  // Define o nome da tabela no banco de dados
@Data  // Lombok gera automaticamente os getters, setters, toString, equals, hashCode e o construtor
@NoArgsConstructor  // Lombok gera o construtor sem parâmetros (necessário para a JPA)
@AllArgsConstructor  // Lombok gera o construtor com todos os parâmetros
public class User {

    @Id  // Define a chave primária da entidade
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Define a estratégia para geração do ID (autoincremento no banco)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false)  // Define que o campo não pode ser nulo
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Column(unique = true, nullable = false)  // Define que o campo 'email' deve ser único e não pode ser nulo
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @Column(nullable = false)  // Define que o campo 'senha' não pode ser nulo
    private String senha;

}
