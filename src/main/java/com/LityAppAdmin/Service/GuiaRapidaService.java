package com.LityAppAdmin.Service;


import com.LityAppAdmin.Model.GuiaRapidaModel;
import com.LityAppAdmin.Repository.IGuiaRapidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuiaRapidaService {

    @Autowired
    IGuiaRapidaRepository guiaRapidaRepository;

    public List<GuiaRapidaModel> obtenerGuiasRapidasRecientes() {
        return guiaRapidaRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaDeCreacion"));
    }



}
