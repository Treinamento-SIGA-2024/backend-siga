package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.InscricaoEstagio;
import br.ufrj.backendsiga.model.entity.SituacaoInscricao;
import br.ufrj.backendsiga.repository.InscricaoEstagioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscricaoEstagioService {
    private final InscricaoEstagioRepository inscricaoEstagioRepository;
    private final SituacaoInscricaoService situacaoInscricaoService;

    public InscricaoEstagioService(InscricaoEstagioRepository inscricaoEstagioRepository, SituacaoInscricaoService situacaoInscricaoService) {
        this.inscricaoEstagioRepository = inscricaoEstagioRepository;
        this.situacaoInscricaoService = situacaoInscricaoService;
    }

    public List<InscricaoEstagio> listPendentes() {
         SituacaoInscricao pendente = situacaoInscricaoService.findByCodigo("000");
         return inscricaoEstagioRepository.findAllBySituacaoInscricao(pendente);
    }
}
