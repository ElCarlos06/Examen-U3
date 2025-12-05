package mx.edu.utez.producto.service;

import mx.edu.utez.producto.dtos.ProductoDto;
import mx.edu.utez.producto.model.Producto;
import mx.edu.utez.producto.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        List<Producto> productos = productoRepository.findAll();
        map.put("productos", productos);
        return map;
    }

    public Map<String, Object> getById(Integer id) {
        Map<String, Object> map = new HashMap<>();
        Optional<Producto> producto = productoRepository.findById(id);
        if(producto.isPresent()) {
            map.put("mensaje", "Producto encontrado");
            map.put("producto", producto.get());
            map.put("code", 200);
        }else {
            map.put("mensaje", "Producto no encontrado");
            map.put("code", 404);
        }

        return map;
    }

    public Map<String, Object> create(ProductoDto productoDto) {
        Map<String, Object> map = new HashMap<>();
        Optional<Producto> productosku = productoRepository.findBySku(productoDto.getSku());
        if(productosku.isPresent()) {
            map.put("mensaje", "El sku ya esta registrado");
            map.put("code", 400);
            return map;
        }
        Producto producto = new Producto();

        if (productoDto.getPrecio() <= 0){
            map.put("mensaje", "El precio tiene que ser mayor que 0");
            map.put("code", 400);
            return map;
        }

        if (productoDto.getStock() <0){
            map.put("mensaje", "El stock no puede ser menor que 0");
            map.put("code", 400);
            return map;
        }

        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        producto.setSku(productoDto.getSku());
        producto.setStock(productoDto.getStock());
        productoRepository.save(producto);
        map.put("mensaje", "Producto agregado correctamente");
        map.put("code", 201);

        return map;
    }

    public Map<String, Object> update(ProductoDto productoDto, Integer id) {
        Map<String, Object> map = new HashMap<>();
        Optional<Producto> producto = productoRepository.findById(id);
        if(producto.isPresent()) {
            Producto existente = producto.get();
            existente.setNombre(productoDto.getNombre());
            existente.setPrecio(productoDto.getPrecio());
            existente.setStock(productoDto.getStock());

            if (productoDto.getPrecio() <= 0){
                map.put("mensaje", "El precio tiene que ser mayor que 0");
                map.put("code", 400);
                return map;
            }

            if (productoDto.getStock() <0){
                map.put("mensaje", "El stock no puede ser menor que 0");
                map.put("code", 400);
                return map;
            }
            productoRepository.save(existente);
            map.put("mensaje", "Producto actualizado correctamente");
            map.put("code", 200);
        }else  {
            map.put("mensaje", "Producto no encontrado");
            map.put("code", 404);
        }
        return map;

    }
}
