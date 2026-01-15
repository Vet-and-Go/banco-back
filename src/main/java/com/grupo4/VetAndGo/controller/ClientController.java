package com.grupo4.VetAndGo.controller;

import com.grupo4.VetAndGo.domain.exception.ValidationException;
import com.grupo4.VetAndGo.domain.model.Client;
import com.grupo4.VetAndGo.domain.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id) {
        return clientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        System.out.println(
                "LOGIN REQUEST RECEIVED: username='" + request.username() + "', password='" + request.password() + "'");
        String token = clientService.login(request.username(), request.password());
        System.out.println("LOGIN SUCCESS: token=" + token);
        return ResponseEntity.ok(new LoginResponse(token, request.username()));
    }

    @PostMapping("/auth/session")
    public ResponseEntity<Client> validateSession(@RequestBody String token) {
        return clientService.validateSession(token)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ValidationException("Token inválido o expirado."));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(@RequestBody LoginRequest request) {
        clientService.logout(request.username());
        return ResponseEntity.noContent().build();
    }

    public record LoginRequest(String username, String password) {
    }

    public record LoginResponse(String token, String username) {
    }

}
