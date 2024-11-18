package com.LityAppAdmin.Service;

import com.LityAppAdmin.Model.ExperienciaModel;
import com.LityAppAdmin.Repository.IExperienciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExperienciaService {

    @Autowired
    IExperienciaRepository experienciaRepository;

    public List<ExperienciaModel> obtenerExperienciasRecientes() {
        return experienciaRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaDeCreacion"));
    }

}