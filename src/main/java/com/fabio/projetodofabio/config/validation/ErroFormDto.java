package com.fabio.projetodofabio.config.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErroFormDto {

    private String campo;
    private String erro;
}
