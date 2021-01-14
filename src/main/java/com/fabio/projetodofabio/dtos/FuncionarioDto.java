package com.fabio.projetodofabio.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FuncionarioDto {

    private Long id;

    @NotEmpty
    @Length(min = 3, max = 200)
    private String nome;

    @NotEmpty
    @Length(min = 5, max = 200)
    @Email
    private String email;


    private Optional<String> senha = Optional.empty();
    private Optional<String> valorHora = Optional.empty();
    private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
    private Optional<String> qtdHorasAlmoco = Optional.empty();
}
