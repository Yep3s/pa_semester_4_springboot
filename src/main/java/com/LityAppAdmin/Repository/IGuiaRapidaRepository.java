package com.LityAppAdmin.Repository;

import com.LityAppAdmin.Model.GuiaRapidaModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGuiaRapidaRepository extends CrudRepository<GuiaRapidaModel, Long> {


}
