package net.youtics.backend.crudProductos.services;

import net.youtics.backend.crudProductos.entities.Producto;
import net.youtics.backend.crudProductos.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class ProductoServiceManager implements ProductoService{

    @Autowired
    ProductoRepository productoRepository;
    @Override
    @Transactional
    public Producto save(Producto producto) {
        return this.productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> get() {
        return (List<Producto>) this.productoRepository.findAll();
    }

    /*
    Cuando se establece readOnly = true en @Transactional, indica a Spring y al gestor de la base de datos
    (como Hibernate) que el método solo realizará operaciones de lectura.

    Transactional: Controla el manejo de transacciones, como el inicio, el commit o el rollback.
    la transacción deben completarse correctamente para que los cambios se apliquen a la base de datos.
    Si alguna de las operaciones falla, la transacción debe deshacerse para mantener la integridad de
    los datos.
    */
    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> getById(Long id) {
        return this.productoRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Producto> delete(Long id) {
        Optional<Producto> producto = this.getById(id);
        if(producto.isPresent()){
            this.productoRepository.deleteById(id);
        }
        return producto;
    }

    @Override
    @Transactional
    public Optional <Producto> update(Long id, Producto producto) {
        Optional<Producto> producto1 = this.getById(id);

        if(producto1.isPresent()){
            // El uso de orElseThrow() para lanzar una excepción en caso de que no se
            // encuentre el producto
            Producto prod = producto1.orElseThrow();

            prod.setNombre(producto.getNombre());
            prod.setPrecio(prod.getPrecio());

            return Optional.of(this.productoRepository.save(producto));
        }

        return producto1;
    }
}
