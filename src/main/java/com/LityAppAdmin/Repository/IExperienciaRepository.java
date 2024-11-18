package com.LityAppAdmin.Repository;

import com.LityAppAdmin.Model.ExperienciaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExperienciaRepository extends JpaRepository<ExperienciaModel, Long> {
}
