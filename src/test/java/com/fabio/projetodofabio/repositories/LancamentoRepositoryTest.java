package com.fabio.projetodofabio.repositories;

import com.fabio.projetodofabio.entities.Empresa;
import com.fabio.projetodofabio.entities.Funcionario;
import com.fabio.projetodofabio.entities.Lancamento;
import com.fabio.projetodofabio.enums.PerfilEnum;
import com.fabio.projetodofabio.enums.TipoEnum;
import com.fabio.projetodofabio.utils.PasswordUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private Long funcionarioId;

    @BeforeEach
    public void setUp() throws Exception {
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());

        Funcionario funcionario = this.funcionarioRepository.save(this.obterDadosFuncionario(empresa));
        this.funcionarioId = funcionario.getId();

        this.lancamentoRepository.save(this.obterDadosLancamentos(funcionario));
        this.lancamentoRepository.save(this.obterDadosLancamentos(funcionario));
    }

    @AfterEach
    public void tearDown() throws Exception {
        this.empresaRepository.deleteAll();
    }


    @Test
    public void testBuscarLancamentosPorFuncionarioId() {
        List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);

        Assertions.assertEquals(2, lancamentos.size());
    }

    @Test
    public void testBuscarLancamentosPorFuncionarioIdPaginado() {
        Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, PageRequest.of(0, 10));

        Assertions.assertEquals(2, lancamentos.getTotalElements());
    }

    private Lancamento obterDadosLancamentos(Funcionario funcionario) {
        Lancamento lancameto = new Lancamento();
        lancameto.setData(new Date());
        lancameto.setTipo(TipoEnum.INICIO_ALMOCO);
        lancameto.setFuncionario(funcionario);
        return lancameto;
    }

    private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Fulano de Tal");
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.generateBCrypt("123456"));
        funcionario.setCpf("24291173474");
        funcionario.setEmail("email@email.com");
        funcionario.setEmpresa(empresa);
        return funcionario;
    }

    private Empresa obterDadosEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa de exemplo");
        empresa.setCnpj("51463645000100");
        return empresa;
    }

}
