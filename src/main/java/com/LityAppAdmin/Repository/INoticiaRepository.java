package com.LityAppAdmin.Repository;

import com.LityAppAdmin.Model.NoticiaModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INoticiaRepository extends CrudRepository<NoticiaModel, Long> {
}
