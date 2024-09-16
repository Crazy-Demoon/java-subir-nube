package com.salomon.apirest.apirest.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salomon.apirest.apirest.Entities.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long>{

}
