package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.NoticiaModel;
import com.LityAppAdmin.Service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/lity")
public class UNoticiasController {

    @Autowired
    private NoticiaService noticiaService;

    @GetMapping("/noticiasUser")
    public String mostrarNoticias(Model model) {

        List<NoticiaModel> noticias = noticiaService.obtenerNoticiasRecientes();
        model.addAttribute("noticias", noticias);
        return "NoticiasUsuario";
    }





}
