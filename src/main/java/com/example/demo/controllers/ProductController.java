package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    
    //CRIAR new product    
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

    
     //BUSCAR one products
    @GetMapping ("/products/{id}")//busca por id temos que colocar o tipo na uri, nesse caso será uma busca por {id}, lembrar de deixar entre chaves
    public ResponseEntity<Object> getOneProduct(@PathVariable (value = "id") UUID id){//assinatura vai retornar um Object pq existirá tratamento de resposta, paramentro @pathvariable para consegui pegar o id que está dentro da uri, id e tipo
       Optional<ProductModel> product0 = productRepository.findById(id); //ir no banco de dados fazer uma busca através de um metodo JPA findById, passamos o id pra ele e ele vai na base fazer o select
                                                                         // Por fim o product0 criado para receber esse objeto caso ele exista
        if(product0.isEmpty()){ // depois por ultimo fazemos um tratamento utlizando do HttpStatus: se produto for vazio isEMpty = notfound senao OK
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto inexistente.");
       }
       return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }
    
    //UPDATE product
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct (@PathVariable (value = "id") UUID id, @RequestBody  @Valid ProductRecordDto productRecordDto ){
        Optional<ProductModel>  product0 = productRepository.findById(id);
        if(product0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto inexistente");
        }
        ProductModel productModel =product0.get();
        
        BeanUtils.copyProperties(productRecordDto, productModel);

        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    //DELETAR
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct (@PathVariable (value="id")UUID id){
        Optional<ProductModel> product0 = productRepository.findById(id);
        if(product0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(product0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");

    }
    
    

}
