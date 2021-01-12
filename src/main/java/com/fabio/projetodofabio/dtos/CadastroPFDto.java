package com.fabio.projetodofabio.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;


@NoArgsConstructor
@ToString
@Getter
@Setter
public class CadastroPFDto {

    private Long id;

    @NotEmpty
    @Length
    private String nome;

    @NotEmpty
    @Length(min = 5, max = 200)
    @Email
    private String email;

    @NotEmpty
    private String senha;

    @NotEmpty
    @CPF
    private String cpf;

    @NotEmpty
    @CNPJ
    private String cnpj;


    private Optional<String> valorHora = Optional.empty();
    private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
    private Optional<String> qtdHorasAlmoco = Optional.empty();


}
