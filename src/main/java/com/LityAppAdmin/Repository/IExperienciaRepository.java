package com.LityAppAdmin.Repository;

import com.LityAppAdmin.Model.ExperienciaModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExperienciaRepository extends CrudRepository<ExperienciaModel, Long> {
}
