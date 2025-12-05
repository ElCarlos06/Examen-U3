package mx.edu.utez.producto.repository;


import mx.edu.utez.producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    Optional<Producto> findBySku(String sku);

}
