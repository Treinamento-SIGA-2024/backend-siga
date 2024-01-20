package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.InscricaoEstagio;
import br.ufrj.backendsiga.model.entity.SituacaoInscricao;
import br.ufrj.backendsiga.repository.InscricaoEstagioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InscricaoEstagioService {
    private final InscricaoEstagioRepository inscricaoEstagioRepository;
    private final SituacaoInscricaoService situacaoInscricaoService;

    public InscricaoEstagioService(InscricaoEstagioRepository inscricaoEstagioRepository, SituacaoInscricaoService situacaoInscricaoService) {
        this.inscricaoEstagioRepository = inscricaoEstagioRepository;
        this.situacaoInscricaoService = situacaoInscricaoService;
    }

    public InscricaoEstagio findById(Integer id) {
        return inscricaoEstagioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Inscrição de estágio não encontrada"));
    }

    public List<InscricaoEstagio> listPendentes() {
         SituacaoInscricao pendente = situacaoInscricaoService.findByCodigo("000");
         return inscricaoEstagioRepository.findAllBySituacaoInscricao(pendente);
    }
}
