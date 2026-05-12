package edu.universidad.productos.service;

import edu.universidad.productos.model.Producto;
import edu.universidad.productos.repository.ProductoRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private static final double PRECIO_MAXIMO_PERMITIDO = 999_999.0;

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto buscar(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado: " + id));
    }

    public Producto procesarProducto(String nombre, Double precio, Integer stock) {
        validarDatos(nombre, precio, stock);

        Producto producto = new Producto();
        producto.setNombre(nombre.strip());
        producto.setPrecio(precio);
        producto.setStock(stock);

        return productoRepository.save(producto);
    }

    private void validarDatos(String nombre, Double precio, Integer stock) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if (precio == null || precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        if (precio > PRECIO_MAXIMO_PERMITIDO) {
            throw new IllegalArgumentException("El precio excede el maximo permitido");
        }
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
}
