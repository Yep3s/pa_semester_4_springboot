package com.LityAppAdmin.Repository;

import com.LityAppAdmin.Model.TourModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITourRepository extends JpaRepository<TourModel, Long> {
}
