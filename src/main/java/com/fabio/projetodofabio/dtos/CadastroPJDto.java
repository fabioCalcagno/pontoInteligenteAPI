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

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CadastroPJDto {

    private Long id;

    @NotEmpty
    @Length(min = 3, max = 200)
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
    @Length(min = 5, max = 200)
    private String razaoSocial;

    @NotEmpty
    @CNPJ
    private String cnpj;
}
