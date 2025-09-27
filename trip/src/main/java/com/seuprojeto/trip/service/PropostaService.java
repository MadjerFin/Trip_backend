package com.seuprojeto.trip.service;

import com.seuprojeto.trip.model.Proposta;
import com.seuprojeto.trip.repository.PropostaRepository;
import com.seuprojeto.trip.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotNull;

@Service
public class PropostaService {

    @Autowired
    private PropostaRepository propostaRepository;

    public Proposta saveProposta(Proposta proposta) {
        // Verifique se algum campo obrigatório está nulo e lance a exceção personalizada
        if (proposta.getNomeResponsavel() == null || proposta.getNomeResponsavel().isEmpty()) {
            throw new ApiException("O nome do responsável é obrigatório", "MISSING_NOME_RESPONSAVEL");
        }
        if (proposta.getNomeEmpresa() == null || proposta.getNomeEmpresa().isEmpty()) {
            throw new ApiException("O nome da empresa é obrigatório", "MISSING_NOME_EMPRESA");
        }
        if (proposta.getTelefone() == null || proposta.getTelefone().isEmpty()) {
            throw new ApiException("O telefone é obrigatório", "MISSING_TELEFONE");
        }
        if (proposta.getEmail() == null || proposta.getEmail().isEmpty()) {
            throw new ApiException("O e-mail é obrigatório", "MISSING_EMAIL");
        }
        if (proposta.getDescricaoProposta() == null || proposta.getDescricaoProposta().isEmpty()) {
            throw new ApiException("A descrição da proposta é obrigatória", "MISSING_DESCRICAO");
        }
        if (proposta.getPreviaUrl() == null || proposta.getPreviaUrl().isEmpty()) {
            throw new ApiException("A URL da prévia é obrigatória", "MISSING_PREVIA_URL");
        }

        // Se tudo estiver ok, salva a proposta
        return propostaRepository.save(proposta);
    }
}
