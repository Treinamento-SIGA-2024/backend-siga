package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.dto.InscricaoEstagioPendentesDTO;
import br.ufrj.backendsiga.model.entity.InscricaoEstagio;
import br.ufrj.backendsiga.model.entity.SituacaoInscricao;
import br.ufrj.backendsiga.model.mapping.InscricaoEstagioMapper;
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

    public List<InscricaoEstagioPendentesDTO> listPendentes() {
         SituacaoInscricao pendente = situacaoInscricaoService.findByCodigo("000");
         List<InscricaoEstagio> inscricoesPendentes = inscricaoEstagioRepository.findAllBySituacaoInscricao(pendente);
         return inscricoesPendentes.stream().map(est -> InscricaoEstagioMapper.INSTANCE.toPendentesDTO(est)).toList();
    }

    public InscricaoEstagio approvePedido(Integer id) {
        InscricaoEstagio inscricaoEstagio = findById(id);
        if(inscricaoEstagio.getSituacaoInscricao().getCodigo().equals("001")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Inscrição de estágio já aprovada");
        } else if(inscricaoEstagio.getSituacaoInscricao().getCodigo().equals("002")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Inscrição de estágio já rejeitada");
        }
        SituacaoInscricao aprovado = situacaoInscricaoService.findByCodigo("001");
        inscricaoEstagio.setSituacaoInscricao(aprovado);
        return inscricaoEstagioRepository.save(inscricaoEstagio);
    }
    public InscricaoEstagio rejectPedido(Integer id) {
        InscricaoEstagio inscricaoEstagio = findById(id);
        if(inscricaoEstagio.getSituacaoInscricao().getCodigo().equals("001")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Inscrição de estágio já aprovada");
        } else if(inscricaoEstagio.getSituacaoInscricao().getCodigo().equals("002")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Inscrição de estágio já rejeitada");
        }
        SituacaoInscricao rejeitado = situacaoInscricaoService.findByCodigo("002");
        inscricaoEstagio.setSituacaoInscricao(rejeitado);
        return inscricaoEstagioRepository.save(inscricaoEstagio);
    }
}
