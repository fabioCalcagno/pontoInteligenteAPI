package com.fabio.projetodofabio.controllers;


import com.fabio.projetodofabio.dtos.CadastroPJDto;
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
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins = "*")
@NoArgsConstructor
public class CadastroPJController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private EmpresaService empresaService;


    @PostMapping
    public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto) throws MethodArgumentNotValidException {
        log.info("Cadastrando PJ: {}", cadastroPJDto.toString());

        Response<CadastroPJDto> response = new Response<CadastroPJDto>();
        validarDadosExistentes(cadastroPJDto);
        Empresa empresa = this.converterDtoParaEmpresa(cadastroPJDto);
        Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPJDto);

        this.empresaService.persist(empresa);
        funcionario.setEmpresa(empresa);
        this.funcionarioService.persist(funcionario);

        response.setData(this.converterCadastroPJDto(funcionario));
        return ResponseEntity.ok(response);
    }

    /**
     * Verifica se a empresa ou funcionário já existem na base de dados.
     *
     * @param cadastroPJDto
     */
    private void validarDadosExistentes(CadastroPJDto cadastroPJDto) {
        this.empresaService.buscarPorCnpj(cadastroPJDto.getCnpj())
                .ifPresent(emp -> new ObjectError("empresa", "Empresa já existente."));

        this.funcionarioService.buscarPorCpf(cadastroPJDto.getCpf())
                .ifPresent(func -> new ObjectError("funcionario", "CPF já existente."));

        this.funcionarioService.buscarPorEmail(cadastroPJDto.getEmail())
                .ifPresent(func -> new ObjectError("funcionario", "Email já existente."));
    }

    /**
     * Converte os dados do DTO para empresa.
     *
     * @param cadastroPJDto
     * @return Empresa
     */
    private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJDto) {
        Empresa empresa = new Empresa();
        empresa.setCnpj(cadastroPJDto.getCnpj());
        empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());

        return empresa;
    }

    /**
     * Converte os dados do DTO para funcionário.
     *
     * @param cadastroPJDto
     * @return Funcionario
     * @throws MethodArgumentNotValidException
     */
    private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastroPJDto)
            throws MethodArgumentNotValidException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(cadastroPJDto.getNome());
        funcionario.setEmail(cadastroPJDto.getEmail());
        funcionario.setCpf(cadastroPJDto.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
        funcionario.setSenha(PasswordUtils.generateBCrypt(cadastroPJDto.getSenha()));

        return funcionario;
    }

    /**
     * Popula o DTO de cadastro com os dados do funcionário e empresa.
     *
     * @param funcionario
     * @return CadastroPJDto
     */
    private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
        CadastroPJDto cadastroPJDto = new CadastroPJDto();
        cadastroPJDto.setId(funcionario.getId());
        cadastroPJDto.setNome(funcionario.getNome());
        cadastroPJDto.setEmail(funcionario.getEmail());
        cadastroPJDto.setCpf(funcionario.getCpf());
        cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
        cadastroPJDto.setCnpj(funcionario.getEmpresa().getCnpj());

        return cadastroPJDto;
    }

}

