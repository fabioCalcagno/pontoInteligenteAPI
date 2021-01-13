package com.fabio.projetodofabio.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmpresaDto {

    private Long id;
    private String razaoSocial;
    private String cnpj;


}
