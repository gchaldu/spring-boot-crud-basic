package net.youtics.backend.crudProductos.controllers;

import net.youtics.backend.crudProductos.entities.Producto;
import net.youtics.backend.crudProductos.services.ProductoServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    ProductoServiceManager productoServiceManager;

    @GetMapping("/")
    public List<Producto> listarProductos(){
        return this.productoServiceManager.get();
    }

    @GetMapping("/{id}")
    public Producto optenerProductoId( @PathVariable Long id){
        return this.productoServiceManager.getById(id).get();
    }

    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Producto> encontrado = productoServiceManager.getById(id);

        if (encontrado.isPresent()) {
            return ResponseEntity.ok(encontrado.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<Producto> save(@RequestBody Producto producto){
        Producto newProducto = productoServiceManager.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProducto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> deleteProducto(@PathVariable Long id){
        Optional<Producto> optionalProducto = this.productoServiceManager.delete(id);

        if(optionalProducto.isPresent()){
            return ResponseEntity.ok(optionalProducto.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto,
                                                   @PathVariable Long id)
    {
        Optional<Producto> productoOptional = this.productoServiceManager.getById(id);

        if(productoOptional.isPresent()){
            return ResponseEntity.status(201).body(productoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

}
