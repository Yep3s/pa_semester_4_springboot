package com.LityAppAdmin.Service;

import com.LityAppAdmin.Model.NoticiaModel;
import com.LityAppAdmin.Repository.INoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticiaService {

    @Autowired
    INoticiaRepository noticiaRepository;

    public List<NoticiaModel> obtenerNoticiasRecientes() {
        return noticiaRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaDeCreacion"));
    }

}
