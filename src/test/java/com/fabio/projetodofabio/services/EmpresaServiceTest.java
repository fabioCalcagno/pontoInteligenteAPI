package com.fabio.projetodofabio.services;

import com.fabio.projetodofabio.entities.Empresa;
import com.fabio.projetodofabio.repositories.EmpresaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
class EmpresaServiceTest {

    @MockBean
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaService empresaService;

    private static final String CNPJ = "51463645000100";

    @BeforeEach
    void setUp() throws Exception {
        BDDMockito.given(this.empresaRepository.findByCnpj(Mockito.anyString()))
                .willReturn(new Empresa());
        BDDMockito.given(this.empresaRepository.save(Mockito.any(Empresa.class)))
                .willReturn(new Empresa());
    }

    @Test
    void testShouldReturnEmpresaSerchingByCnpj() {
        Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(CNPJ);
        Assertions.assertTrue(empresa.isPresent());
    }

    @Test
    void testShouldPersistEmpresaOnDataBase() {
        Empresa empresa = this.empresaService.persist(new Empresa());
        Assertions.assertNotNull(empresa);
    }
}
