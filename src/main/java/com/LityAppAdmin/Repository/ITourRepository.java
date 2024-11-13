package com.LityAppAdmin.Repository;

import com.LityAppAdmin.Model.TourModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITourRepository extends CrudRepository<TourModel, Long> {
}
