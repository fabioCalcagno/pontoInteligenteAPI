package com.fabio.projetodofabio.services;

import com.fabio.projetodofabio.entities.Funcionario;
import com.fabio.projetodofabio.repositories.FuncionarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class FuncionarioServiceTest {

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @BeforeEach
    void setUp() throws Exception {
        BDDMockito.given(this.funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
        BDDMockito.given(this.funcionarioRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
        BDDMockito.given(this.funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
        BDDMockito.given(this.funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
    }

    @Test
    void testPersistirFuncionario() {
        Funcionario funcionario = this.funcionarioService.persist(new Funcionario());

        Assertions.assertNotNull(funcionario);
    }

    @Test
    void testBuscarFuncionarioPorId() {
        Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(1L);

        Assertions.assertTrue(funcionario.isPresent());
    }

    @Test
    void testBuscarFuncionarioPorEmail() {
        Optional<Funcionario> funcionario = this.funcionarioService.buscarPorEmail("email@email.com");

        Assertions.assertTrue(funcionario.isPresent());
    }

    @Test
    void testBuscarFuncionarioPorCpf() {
        Optional<Funcionario> funcionario = this.funcionarioService.buscarPorCpf("24291173474");

        Assertions.assertTrue(funcionario.isPresent());
    }

}
