package br.ufrj.backendsiga.service;
import br.ufrj.backendsiga.model.entity.Cargo;
import br.ufrj.backendsiga.model.entity.IniciacaoCientifica;
import br.ufrj.backendsiga.model.entity.Usuario;
import br.ufrj.backendsiga.repository.IniciacaoCientificaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IniciacaoCientificaService {
    private final IniciacaoCientificaRepository iniciacaoCientificaRepository;
    private final UsuarioService usuarioService;
    private final CargoService cargoService;

    @Transactional
    public List<IniciacaoCientifica> listIniciacaoCientificaByProfessorMatricula(String matricula) {
        Usuario professor = usuarioService.getUsuarioByMatricula(matricula);
        Cargo cargoProfessor = cargoService.getCargoByNome(Cargo.PROFESSOR);
        usuarioService.assertUsuarioCargo(professor, cargoProfessor);

        return iniciacaoCientificaRepository.findAllByProfessoresIsContaining(professor);
    }
}
