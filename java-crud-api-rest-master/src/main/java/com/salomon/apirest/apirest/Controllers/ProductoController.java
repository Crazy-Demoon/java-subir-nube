package com.salomon.apirest.apirest.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomon.apirest.apirest.Entities.Producto;
import com.salomon.apirest.apirest.Repositories.ProductoRepositorio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    // Con esta etiqueta spring automaticamente sabe que repositorio vamos a usar y
    // automaticamante va y hace una instancia a ese repositorio
    @Autowired
    private ProductoRepositorio productoRepositorio;

    @GetMapping
    // Get al servidor, select * from a la base de datos
    public List<Producto> getAllProductos() {
        // de la intancia de productosRepositorio trae todos los productos, aca estarian
        // las cosas de la base de datos
        return productoRepositorio.findAll();
    }

    @GetMapping("/{id}")
    // Obtener producto por id
    public Producto getProductById(@PathVariable Long id) {
        /*
         * Hacemos un retorno de el repositorio donde se encuentra el producto al cual
         * buscamos por ID
         * En caso de que no se encuentre lanzamos una exepcion la cual nos informa que
         * no se encontro el producto con n ID
         */
        return productoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID " + id));
    }

    // post al servidor, insert into a la base de datos
    @PostMapping
    public Producto createProducto(@RequestBody Producto producto) {

        return productoRepositorio.save(producto);
    }

    // put al servidor, ser como update a la base de datos
    // Recibe como parametro el ID del producto y tambien serian los detalles del
    // producto o informacion

    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto detailsProducto) {
        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID " + id));

        producto.setNombre(detailsProducto.getNombre());
        producto.setPrecio(detailsProducto.getPrecio());

        return productoRepositorio.save(producto);
    }

    /*
     * Lo que esta dento de las etiquetas de delete, upodate o create es lo que se va a recibir o enviar 
     * por medio de las rutas, en este caso en todas estamos obteniendo el ID para poder hacer las busquedas
     * en nuesra base de datos
     */
    @DeleteMapping("/{id}")
    public String deleteProducto(@PathVariable Long id){
        Producto producto = productoRepositorio.findById(id)
        .orElseThrow(()-> new RuntimeException("No se encontro ningun producto con el ID " + id));
        productoRepositorio.delete(producto);

        return "El producto con el ID " + id + " ha sido eliminado exitosamente";
    }

}
