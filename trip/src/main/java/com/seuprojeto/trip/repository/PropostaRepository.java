package com.seuprojeto.trip.repository;

import com.seuprojeto.trip.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    // Você pode adicionar consultas personalizadas aqui, se necessário
}
