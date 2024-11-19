package com.LityAppAdmin.Service;

import com.LityAppAdmin.Model.TourModel;
import com.LityAppAdmin.Repository.ITourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TourService {

    @Autowired
    ITourRepository tourRepository;

    public List<TourModel> obtenerToursRecientes() {
        return tourRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaDeCreacion"));
    }

}
