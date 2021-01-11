package com.fabio.projetodofabio.entities;


import com.fabio.projetodofabio.enums.TipoEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Lancamento implements Serializable {
    private static final long serialVersionUID = 6524560251526772839L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @NotNull
    private Date dataCriacao;
    private Date dataAtualizacao;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoEnum tipo;

    @ManyToOne(fetch = FetchType.EAGER)
    private Funcionario funcionario;

    private String descricao;
    private String localizacao;

    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = new Date();
    }

    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }

}
