package com.example.demo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


    @Entity
    @Table (name =" TB_PRODUCTS" )
    public class ProductModel implements Serializable {
    /*A serialização significa salvar o estado atual dos objetos em arquivos em formato binário
    para o seu computador, sendo assim esse estado poderá ser recuperado posteriormente recriando 
    o objeto em memória assim como ele estava nomomento da sua serialização.*/
        
    //garante serialização e decerialização do objeto 
    private static final long serialVersionUID =1L;

    /* @Id Garante que meu atributo seja um id e o @GeneratedValue 
    garante que esse id seja gerado automaticamente atraves da estrategia AUTOMÁTICA*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProduct;
    private String nome;
    private BigDecimal value;






    public UUID getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public BigDecimal getValue() {
        return value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }


    }
