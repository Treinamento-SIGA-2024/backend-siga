package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.SituacaoInscricao;
import br.ufrj.backendsiga.repository.SituacaoInscricaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SituacaoInscricaoService {
    private final SituacaoInscricaoRepository situacaoInscricaoRepository;

    public SituacaoInscricao findByCodigo(String codigo) {
        return situacaoInscricaoRepository.findByCodigo(codigo).orElseThrow();
    }
}
