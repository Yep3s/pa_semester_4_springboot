package com.LityAppAdmin.Repository;

import com.LityAppAdmin.Model.GuiaRapidaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGuiaRapidaRepository extends JpaRepository<GuiaRapidaModel, Long> {
    List<GuiaRapidaModel> findByTipoDeGuia(String tipoDeGuia);
}
