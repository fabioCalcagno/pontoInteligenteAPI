package com.fabio.projetodofabio.controllers;


import com.fabio.projetodofabio.dtos.CadastroPFDto;
import com.fabio.projetodofabio.entities.Empresa;
import com.fabio.projetodofabio.entities.Funcionario;
import com.fabio.projetodofabio.enums.PerfilEnum;
import com.fabio.projetodofabio.response.Response;
import com.fabio.projetodofabio.services.EmpresaService;
import com.fabio.projetodofabio.services.FuncionarioService;
import com.fabio.projetodofabio.utils.PasswordUtils;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("/api/cadastrar-pf")
@NoArgsConstructor
public class CadastroPFController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPFController.class);

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Response<CadastroPFDto>> cadastrar(@Valid @RequestBody CadastroPFDto cadastroPFDto) throws NoSuchAlgorithmException {
        log.info("Cadastrando PF: {}", cadastroPFDto);
        Response<CadastroPFDto> response = new Response<>();

        validarDadosExistentes(cadastroPFDto, response);
        Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPFDto);

        if (!response.getErrors().isEmpty()) {
            log.error("Erro validando dados de cadastro PF: {}", response.getErrors());
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPFDto.getCnpj());
        empresa.ifPresent(funcionario::setEmpresa);
        this.funcionarioService.persist(funcionario);

        response.setData(this.converterCadastroPFDto(funcionario));
        return ResponseEntity.ok(response);

    }


    /**
     * Verifica se a empresa está cadastrada e se o funcionário não existe na base de dados.
     *
     * @param cadastroPFDto
     * @return
     */
    private void validarDadosExistentes(CadastroPFDto cadastroPFDto, Response<CadastroPFDto> response) {
        if (!this.empresaService.buscarPorCnpj(cadastroPFDto.getCnpj()).isPresent()) {
            response.getErrors().add(new ObjectError("empresa", "Empresa não cadastrada.").getDefaultMessage());
        }
        this.funcionarioService.buscarPorCpf(cadastroPFDto.getCpf())
                .ifPresent(func -> response.getErrors().add(new ObjectError("funcionario", "CPF já existente.").getDefaultMessage()));

        this.funcionarioService.buscarPorEmail(cadastroPFDto.getEmail())
                .ifPresent(func -> response.getErrors().add(new ObjectError("funcionario", "E-mail já existente.").getDefaultMessage()));
    }

    /**
     * Converte os dados do DTO para funcionário.
     *
     * @param cadastroPFDto
     * @return Funcionario
     * @throws NoSuchAlgorithmException
     */
    private Funcionario converterDtoParaFuncionario(CadastroPFDto cadastroPFDto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(cadastroPFDto.getNome());
        funcionario.setEmail(cadastroPFDto.getEmail());
        funcionario.setCpf(cadastroPFDto.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.generateBCrypt(cadastroPFDto.getSenha()));
        cadastroPFDto.getQtdHorasAlmoco()
                .ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
        cadastroPFDto.getQtdHorasTrabalhoDia()
                .ifPresent(qtdHorasTrabDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));
        cadastroPFDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

        return funcionario;
    }

    /**
     * Popula o DTO de cadastro com os dados do funcionário e empresa.
     *
     * @param funcionario
     * @return CadastroPFDto
     */
    private CadastroPFDto converterCadastroPFDto(Funcionario funcionario) {
        CadastroPFDto cadastroPFDto = new CadastroPFDto();
        cadastroPFDto.setId(funcionario.getId());
        cadastroPFDto.setNome(funcionario.getNome());
        cadastroPFDto.setEmail(funcionario.getEmail());
        cadastroPFDto.setCpf(funcionario.getCpf());
        cadastroPFDto.setCnpj(funcionario.getEmpresa().getCnpj());
        funcionario.getQtdHorasAlmocoOpt().ifPresent(qtdHorasAlmoco ->
                cadastroPFDto.setQtdHorasAlmoco(Optional.of(qtdHorasAlmoco.toString())));
        funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(qtdHorasTrabDia ->
                cadastroPFDto.setQtdHorasTrabalhoDia(Optional.of(qtdHorasTrabDia.toString())));
        funcionario.getValorHoraOpt().ifPresent(valorHora ->
                cadastroPFDto.setValorHora(Optional.of(valorHora.toString())));

        return cadastroPFDto;
    }


}
