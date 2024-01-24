package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.entity.SituacaoCriacaoIC;
import br.ufrj.backendsiga.repository.SituacaoCriacaoICRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SituacaoCriacaoICService {
    private final SituacaoCriacaoICRepository situacaoCriacaoICRepository;

    public SituacaoCriacaoIC getSituacaoCriacaoICByCodigo(String codigo) {
        Optional<SituacaoCriacaoIC> situacaoCriacaoIC = situacaoCriacaoICRepository.findByCodigo(codigo);
        if (situacaoCriacaoIC.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Código de situação de criação \"" +
                    codigo +
                    "\" de iniciação científica não encontrado");
        }
        return situacaoCriacaoIC.get();
    }
}
