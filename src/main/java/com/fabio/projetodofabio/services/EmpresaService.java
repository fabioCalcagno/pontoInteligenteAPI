package com.fabio.projetodofabio.services;

import com.fabio.projetodofabio.entities.Empresa;

import java.util.Optional;

public interface EmpresaService {

    /**
     * Retorna uma empresa dado um CNPJ
     * @Param cnpj
     * @return Optional<Empresa>
     */
    Optional<Empresa> buscarPorCnpj(String cnpj);

    /**
     * Cadastra uma nova empresa no banco de dados
     *
     * @Param empresa
     * @return Empresa
     */
    Empresa persist(Empresa empresa);

}
