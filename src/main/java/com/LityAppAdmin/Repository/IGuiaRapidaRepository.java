package com.LityAppAdmin.Repository;

import com.LityAppAdmin.Model.GuiaRapidaModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGuiaRapidaRepository extends CrudRepository<GuiaRapidaModel, Long> {

    List<GuiaRapidaModel> findBytipoDeGuia(String tipoDeGuia);

}
