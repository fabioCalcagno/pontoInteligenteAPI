package com.fabio.projetodofabio.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class JwtAuthenticationDto {

    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String senha;


}
