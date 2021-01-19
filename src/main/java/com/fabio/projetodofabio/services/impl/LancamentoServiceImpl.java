package com.fabio.projetodofabio.services.impl;

import com.fabio.projetodofabio.entities.Lancamento;
import com.fabio.projetodofabio.repositories.LancamentoRepository;
import com.fabio.projetodofabio.services.LancamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Override
    public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
        log.info("Buscando um lançamento pelo id do funcionario {}", funcionarioId);
        return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
    }

    @Override
    @Cacheable("lancamentoPorId")
    public Optional<Lancamento> buscarPorId(Long id) {
        log.info("Buscando um lançamento pelo id {}", id);
        return this.lancamentoRepository.findById(id);
    }

    @Override
    @CachePut("lancamentoPorId")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Lancamento persist(Lancamento lancamento) {
        log.info("Persistindo um Lançamento na base de dados  {}", lancamento);
        return this.lancamentoRepository.save(lancamento);
    }

    @Override
    public void remover(Long id) {
        log.info("Deletando um lançamento da base de dados, pelo id {}", id);
        this.lancamentoRepository.deleteById(id);
    }
}
