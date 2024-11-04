package com.LityAppAdmin.Repository;

import com.LityAppAdmin.Model.AdministradorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IAdministradorRepository extends JpaRepository<AdministradorModel, Long> {

    @Query("SELECT a FROM AdministradorModel a WHERE a.correo = :correo AND a.password = :password")
    Optional<AdministradorModel> findByCorreoAndPassword(@Param("correo") String correo, @Param("password") String password);
}

