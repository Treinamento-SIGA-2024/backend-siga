package br.ufrj.backendsiga.service;

import br.ufrj.backendsiga.model.dto.EstagioCreateDTO;
import br.ufrj.backendsiga.model.entity.Estagio;
import br.ufrj.backendsiga.repository.EstagioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstagioService {
    private final EstagioRepository estagioRepository;

    public List<Estagio> listAll() {
        List<Estagio> estagios = estagioRepository.findAll();
        List<Estagio> estagiosComVagas = new ArrayList<>();

        for (Estagio estagio : estagios) {
            if (estagio.getQuantidadeVagas() != 0) {
                estagiosComVagas.add(estagio);
            }
        }
        return estagiosComVagas;
    }

    public Estagio getEstagioById(Integer id) {
        Estagio estagio = estagioRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Estágio não encontrado.")
        );

        return estagio;
    };

    public Estagio createEstagio(EstagioCreateDTO dto) {

        Estagio novoEstagio = new Estagio();

        novoEstagio.setCargo(dto.getCargo());
        novoEstagio.setEmpresa(dto.getEmpresa());
        novoEstagio.setRemuneracao(dto.getRemuneracao());
        novoEstagio.setCargaHorariaSemanal(dto.getCargaHorariaSemanal());
        novoEstagio.setQuantidadeVagas(dto.getQuantidadeVagas());
        novoEstagio.setModalidade(dto.getModalidade());
        novoEstagio.setDescricao(dto.getDescricao());

        return estagioRepository.save(novoEstagio);
    };

}
