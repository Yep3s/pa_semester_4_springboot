package com.LityAppAdmin.Repository;

import com.LityAppAdmin.Model.ImagenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImagenRepository  extends JpaRepository<ImagenModel, Long> {
    List<ImagenModel> findByTipoEntidadAndEntidadId(String tipoEntidad, Long entidadId);
}
