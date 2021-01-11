package com.fabio.projetodofabio.services;

import com.fabio.projetodofabio.entities.Empresa;
import com.fabio.projetodofabio.repositories.EmpresaRepository;
import org.junit.jupiter.api.*;
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
public class EmpresaServiceTest {

    @MockBean
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaService empresaService;

    private static final String CNPJ = "51463645000100";

    @BeforeEach
    public void setUp() throws Exception{
        BDDMockito.given(this.empresaRepository.findByCnpj(Mockito.anyString()))
                .willReturn(new Empresa());
        BDDMockito.given(this.empresaRepository.save(Mockito.any(Empresa.class)))
                .willReturn(new Empresa());
    }

    @Test
    public void testShouldReturnEmpresaSerchingByCnpj(){
        Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(CNPJ);
        Assertions.assertTrue(empresa.isPresent());
    }

    @Test
    public void testShouldPersistEmpresaOnDataBase(){
        Empresa empresa = this.empresaService.persist(new Empresa());
        Assertions.assertNotNull(empresa);
    }
}
