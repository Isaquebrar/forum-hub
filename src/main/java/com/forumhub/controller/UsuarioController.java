package com.forumhub.controller;

import com.forumhub.domain.usuario.Usuario;
import com.forumhub.dto.DadosCadastroUsuario;
import com.forumhub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {
        if (repository.existsByEmail(dados.email())) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado!");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.senha());
        Usuario novoUsuario = new Usuario(dados.nome(), dados.email(), senhaCriptografada);
        repository.save(novoUsuario);

        return ResponseEntity.ok().body("Usuário cadastrado com sucesso!");
    }
}
