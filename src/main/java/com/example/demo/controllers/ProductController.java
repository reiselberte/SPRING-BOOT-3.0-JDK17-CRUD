package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.ProductRecordDto;
import com.example.demo.model.ProductModel;
import com.example.demo.repository.ProductRepository;

import jakarta.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;// injeção de dependẽncia controller se comunicando com repository  

    //CREATE new product    
   @PostMapping("/products") 
    public ResponseEntity<ProductModel> saveProduct (@RequestBody @Valid ProductRecordDto productRecordDto){//assinatura do metodo retornando uma entidade de retorno do tipo model, recebendo com parametro o dto para verificação de integridade junto com @requestbody  
        var productModel = new ProductModel();// se a validação for um sucesso, será criado um novo produto 
        BeanUtils.copyProperties(productRecordDto, productModel);// após a criação do objeto vamos copiar as informações de dto para o model
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));// por fim chegou o return, antes de retornar ele salvará o produto model no repository e depois retornará status OK
    }

    //BUSCAR all Products
    @GetMapping("/products") //definir uri
    public ResponseEntity<List<ProductModel>> getAllProducts(){//Assinatura sera composta de entidade de resposta + lista de model
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll()); //return entidade de rersposta + status OK e corpo de repository.findAll
    }
    
    
    
}
