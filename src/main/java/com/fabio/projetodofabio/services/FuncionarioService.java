package com.fabio.projetodofabio.services;

import com.fabio.projetodofabio.entities.Funcionario;

import java.util.Optional;

public interface FuncionarioService {

    /**
     *
     * @param funcionario
     * @return funcionario
     */
    Funcionario persist(Funcionario funcionario);

    /**
     *
     * @param cpf
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorCpf(String cpf);

    /**
     *
     * @param email
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorEmail(String email);

    /**
     *
     * @param id
     * @return Optional<Funcionario>
     */
    Optional<Funcionario> buscarPorId(Long id);


}
