package com.fabio.projetodofabio.repositories;

import com.fabio.projetodofabio.entities.Empresa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;



@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmpresaRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String CNPJ = "51463645000100";

    @BeforeEach
    public void setUp() throws Exception{
        Empresa empresa = new Empresa();
        empresa.setCnpj(CNPJ);
        empresa.setRazaoSocial("Empresa de exemplo");
        empresaRepository.save(empresa);
    }

    @Test
    public void testBuscarPorCnpj(){
        Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);

        Assertions.assertEquals(CNPJ, empresa.getCnpj());
    }
}
