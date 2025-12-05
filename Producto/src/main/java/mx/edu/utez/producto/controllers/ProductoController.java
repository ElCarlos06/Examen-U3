package mx.edu.utez.producto.controllers;

import mx.edu.utez.producto.dtos.ProductoDto;
import mx.edu.utez.producto.model.Producto;
import mx.edu.utez.producto.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/producto")
@CrossOrigin(origins = "*")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        Map<String, Object> map = productoService.getById(id);
        int code = (Integer) map.get("code");
        return  new ResponseEntity<>(map,code == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProductoDto  productoDto) {
        Map<String, Object> map = productoService.create(productoDto);
        int code = (Integer) map.get("code");
        return  new ResponseEntity<>(map,code == 201 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody ProductoDto  productoDto, @PathVariable Integer id) {
        Map<String, Object> map = productoService.update(productoDto, id);
        int code = (Integer) map.get("code");
        return  new ResponseEntity<>(map,code == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
