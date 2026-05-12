package edu.universidad.productos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.universidad.productos.model.Producto;
import edu.universidad.productos.repository.ProductoRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        productoService = new ProductoService(productoRepository);
    }

    @Test
    void buscarRetornaProductoCuandoExiste() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Teclado");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.buscar(1L);

        assertEquals("Teclado", resultado.getNombre());
    }

    @Test
    void buscarLanzaExcepcionCuandoNoExiste() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        NoSuchElementException excepcion = assertThrows(
                NoSuchElementException.class,
                () -> productoService.buscar(99L)
        );

        assertEquals("Producto no encontrado: 99", excepcion.getMessage());
    }

    @Test
    void procesarProductoValidoNormalizaNombreYGuarda() {
        Producto guardado = new Producto();
        guardado.setId(1L);
        guardado.setNombre("Monitor");
        guardado.setPrecio(750_000.0);
        guardado.setStock(8);

        when(productoRepository.save(any(Producto.class))).thenReturn(guardado);

        Producto resultado = productoService.procesarProducto("  Monitor  ", 750_000.0, 8);

        assertEquals("Monitor", resultado.getNombre());
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void procesarProductoRechazaNombreVacio() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> productoService.procesarProducto("   ", 10_000.0, 2)
        );

        assertEquals("El nombre no puede estar vacio", excepcion.getMessage());
    }

    @Test
    void procesarProductoRechazaPrecioNoValido() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> productoService.procesarProducto("Mouse", 0.0, 2)
        );

        assertEquals("El precio debe ser mayor a cero", excepcion.getMessage());
    }

    @Test
    void procesarProductoRechazaPrecioSuperiorAlMaximo() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> productoService.procesarProducto("Servidor", 1_200_000.0, 1)
        );

        assertEquals("El precio excede el maximo permitido", excepcion.getMessage());
    }

    @Test
    void procesarProductoRechazaStockNegativo() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> productoService.procesarProducto("Mouse", 10_000.0, -1)
        );

        assertEquals("El stock no puede ser negativo", excepcion.getMessage());
    }
}
