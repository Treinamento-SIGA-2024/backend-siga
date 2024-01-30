package br.ufrj.backendsiga.model.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SessaoDTO {
    private UUID id;
    private UsuarioDTO usuario;
    private LocalDateTime criacao;
    private Integer expira_segundos;
}
