package com.laboratorio.springboot14.util;

import com.laboratorio.springboot14.dto.ProductoDTO;
import com.laboratorio.springboot14.dto.ProductoProjection;
import com.laboratorio.springboot14.dto.ProductoRecord;
import com.laboratorio.springboot14.model.Producto;
import com.laboratorio.springboot14.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class Runner implements CommandLineRunner {
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        String nombre = "cable de red";
        String infix = "ble";

        log.info("Ejecutando Runner...");

        Optional<Producto> producto = productoRepository.findOneByNombre(nombre);
        if (producto.isEmpty()) {
            log.info("No encontré el producto");
        } else {
            log.info("Producto: {}", producto.get().toString());
        }

        producto = productoRepository.findOneByNombreIgnoreCase(nombre);
        if (producto.isEmpty()) {
            log.info("No encontré el producto");
        } else {
            log.info("Producto: {}", producto.get().toString());
        }

        List<Producto> productos = productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        for (Producto p : productos) {
            log.info("Producto: {}", p.toString());
        }

        /* int result = productoRepository.updateCategoriaProductos(2, 1);
        log.info("Se han modificado: {} registros", result);

        result = productoRepository.deleteProductosByCategoria(1);
        log.info("Se han eliminado: {} registros", result); */

        /* long result = productoRepository.deleteByCategoriaId(1);
        log.info("Se han eliminado: {} registros", result); */

        /* int result = productoRepository.updateCategoriaProductosSQL(1, 2);
        log.info("Se han modificado: {} registros", result); */

        /* Integer categoriaId = 1;
        nombre = "caBLe";

        List<Producto> productos1 = productoRepository.findByCategoriaAndNombreSQL(categoriaId, nombre);
        for (Producto p: productos1) {
            log.info("Producto: {}", p.toString());
        }

        int result = productoRepository.deleteProductosByCategoriaSQL(1);
        log.info("Se han eliminado: {} registros", result); */

        // Proyecciones personalizadas
        log.info("********************************************************");
        log.info("Ejemplo de proyección personalizada por constructor: DTO");
        log.info("********************************************************");
        List<ProductoDTO> productosDTO = productoRepository.findListadoProductos();
        for (ProductoDTO p : productosDTO) {
            log.info("Producto DTO: {}", p.toString());
        }

        log.info("***********************************************************");
        log.info("Ejemplo de proyección personalizada por constructor: Record");
        log.info("***********************************************************");
        List<ProductoRecord> productosRecord = productoRepository.findListadoProductosRecord();
        for (ProductoRecord p : productosRecord) {
            log.info("Producto Record: {}", p.toString());
        }

        log.info("*********************************************************");
        log.info("Ejemplo de proyección personalizada usando datos en crudo");
        log.info("*********************************************************");
        List<Object[]> productosObject = productoRepository.findListadoProductosObject();
        for (Object[] p : productosObject) {
            log.info("Producto object: id: {}, nombre: {}, categoría: {}", p[0], p[1], p[2]);
        }

        log.info("************************************************************");
        log.info("Ejemplo de proyección personalizada usando closed projection");
        log.info("************************************************************");
        List<ProductoProjection> productosProjection = productoRepository.findListadoProductosProjection();
        for (ProductoProjection p : productosProjection) {
            log.info("Producto object: id: {}, nombre: {}, categoría: {}", p.getCodigo(), p.getNombre(), p.getCategoria());
        }
    }
}