package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.SituacaoInscricao;
import br.ufrj.backendsiga.repository.SituacaoInscricaoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SituacaoInscricaoService {
    private final SituacaoInscricaoRepository situacaoInscricaoRepository;

    public SituacaoInscricaoService(SituacaoInscricaoRepository situacaoInscricaoRepository) {
        this.situacaoInscricaoRepository = situacaoInscricaoRepository;
    }
    public SituacaoInscricao findByCodigo(String codigo) {
        return situacaoInscricaoRepository.findByCodigo(codigo).orElseThrow();
    }
}
