package com.seuprojeto.trip.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "propostas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeResponsavel;  // Nome do responsável pela proposta

    @Column(nullable = false)
    private String nomeEmpresa;  // Nome da empresa que está fazendo a proposta

    @Column(nullable = false)
    private String telefone;  // Número de telefone para contato

    @Column(nullable = false)
    private String email;  // E-mail de contato

    @Column(nullable = false)
    private String descricaoProposta;  // Descrição da proposta

    @Column(nullable = false)
    private String previaUrl;  // URL da imagem da prévia da proposta
}
