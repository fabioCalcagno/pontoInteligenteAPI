package com.fabio.projetodofabio.controllers;

import com.fabio.projetodofabio.dtos.FuncionarioDto;
import com.fabio.projetodofabio.entities.Funcionario;
import com.fabio.projetodofabio.response.Response;
import com.fabio.projetodofabio.services.FuncionarioService;
import com.fabio.projetodofabio.utils.PasswordUtils;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Optional;


@NoArgsConstructor
@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

    @Autowired
    private FuncionarioService funcionarioService;


    /**
     * Atualiza os dados de um funcionário.
     *
     * @param id
     * @param funcionarioDto
     * @return ResponseEntity<Response < FuncionarioDto>>
     * @throws NoSuchAlgorithmException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id,
                                                              @Valid @RequestBody FuncionarioDto funcionarioDto) throws NoSuchAlgorithmException, NoSuchElementException {

        log.info("Atualizando funcionário: {}", funcionarioDto.toString());
        Response<FuncionarioDto> response = new Response<FuncionarioDto>();
        Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(id);

        this.atualizarDadosFuncionario(funcionario.get(), funcionarioDto, response);
        this.validateEqualEmail(funcionario.get(), funcionarioDto, response);

        if (!response.getErrors().isEmpty()) {
            return ResponseEntity.badRequest().body(response);
        }

        this.funcionarioService.persist(funcionario.get());
        response.setData(this.converterFuncionarioDto(funcionario.get()));

        return ResponseEntity.ok(response);
    }

    /**
     * Atualiza os dados do funcionário com base nos dados encontrados no DTO.
     *
     * @param funcionario
     * @param funcionarioDto
     * @throws NoSuchAlgorithmException
     */
    private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto, Response<FuncionarioDto> response)
            throws NoSuchAlgorithmException {
        funcionario.setNome(funcionarioDto.getNome());


        funcionario.setQtdHorasAlmoco(null);
        funcionarioDto.getQtdHorasAlmoco()
                .ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));

        funcionario.setQtdHorasTrabalhoDia(null);
        funcionarioDto.getQtdHorasTrabalhoDia()
                .ifPresent(qtdHorasTrabDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));

        funcionario.setValorHora(null);
        funcionarioDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

        if (funcionarioDto.getSenha().isPresent()) {
            funcionario.setSenha(PasswordUtils.generateBCrypt(funcionarioDto.getSenha().get()));
        }
    }

    /**
     * Retorna um DTO com os dados de um funcionário.
     *
     * @param funcionario
     * @return FuncionarioDto
     */
    private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
        FuncionarioDto funcionarioDto = new FuncionarioDto();
        funcionarioDto.setId(funcionario.getId());
        funcionarioDto.setEmail(funcionario.getEmail());
        funcionarioDto.setNome(funcionario.getNome());
        funcionario.getQtdHorasAlmocoOpt().ifPresent(
                qtdHorasAlmoco -> funcionarioDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
        funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(
                qtdHorasTrabDia -> funcionarioDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabDia))));
        funcionario.getValorHoraOpt()
                .ifPresent(valorHora -> funcionarioDto.setValorHora(Optional.of(valorHora.toString())));

        return funcionarioDto;
    }

    /**
     * Valida se o email inserido é igual ao anterior
     *
     * @param funcionario
     * @param funcionarioDto
     * @param response
     */
    private void validateEqualEmail(Funcionario funcionario, FuncionarioDto funcionarioDto, Response response) {
        if (!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
            this.funcionarioService.buscarPorEmail(funcionarioDto.getEmail())
                    .ifPresent(func -> response.getErrors()
                            .add(String.valueOf(new ObjectError("email", "Email já existente.").getDefaultMessage())));
            funcionario.setEmail(funcionarioDto.getEmail());
        }
    }

}
