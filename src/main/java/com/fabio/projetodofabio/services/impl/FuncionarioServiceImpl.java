package com.fabio.projetodofabio.services.impl;

import com.fabio.projetodofabio.entities.Funcionario;
import com.fabio.projetodofabio.repositories.FuncionarioRepository;
import com.fabio.projetodofabio.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Funcionario persist(Funcionario funcionario) {
        log.info("Persistindo Funcionario: {}", funcionario);
        return this.funcionarioRepository.save(funcionario);
    }

    @Override
    public Optional<Funcionario> buscarPorCpf(String cpf) {
        log.info("Buscando Funcionario por Cpf: {}", cpf);
        return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
    }

    @Override
    public Optional<Funcionario> buscarPorEmail(String email) {
        log.info("Buscando Funcionario por E-mail: {}", email);
        return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
    }

    @Override
    public Optional<Funcionario> buscarPorId(Long id) {
        log.info("Buscando Funcionario por Id: {}", id);
        return this.funcionarioRepository.findById(id);
    }
}
