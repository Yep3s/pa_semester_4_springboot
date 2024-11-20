package com.LityAppAdmin.Repository;

import com.LityAppAdmin.Model.NoticiaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INoticiaRepository extends JpaRepository<NoticiaModel, Long> {
}
