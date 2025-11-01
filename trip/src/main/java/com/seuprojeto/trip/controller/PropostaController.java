package com.seuprojeto.trip.controller;

import com.seuprojeto.trip.exception.ApiException;
import com.seuprojeto.trip.model.Proposta;
import com.seuprojeto.trip.service.PropostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Permite requisições do frontend (localhost e Vercel)
@CrossOrigin(origins = {"http://localhost:3000", "https://trip-red.vercel.app"}, allowCredentials = "true")
@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    @Autowired
    private PropostaService propostaService;

    // Endpoint para enviar uma nova proposta
    @PostMapping
    public ResponseEntity<Object> enviarProposta(@RequestBody Proposta proposta) {
        try {
            // Tenta salvar a proposta
            Proposta propostaSalva = propostaService.saveProposta(proposta);
            return ResponseEntity.status(HttpStatus.CREATED).body(propostaSalva);  // Retorna a proposta salva com status 201
        } catch (ApiException e) {
            // Captura a exceção ApiException e retorna mensagem detalhada para o cliente
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();  // Imprime a exceção no log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno, tente novamente mais tarde.");
        }
    }

    // Manipula a exceção globalmente para a aplicação
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> handleCampoObrigatorioException(ApiException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
