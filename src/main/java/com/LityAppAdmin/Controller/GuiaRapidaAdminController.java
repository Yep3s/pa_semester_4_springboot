package com.LityAppAdmin.Controller;

import com.LityAppAdmin.Model.GuiaRapidaModel;
import com.LityAppAdmin.Repository.IGuiaRapidaRepository;
import com.LityAppAdmin.Service.GuiaRapidaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/admin")
public class GuiaRapidaAdminController {

    private final GuiaRapidaService guiaRapidaService;

    public GuiaRapidaAdminController(GuiaRapidaService guiaRapidaService) {
        this.guiaRapidaService = guiaRapidaService;
    }

    @Autowired
    private IGuiaRapidaRepository guiaRapidaRepository;

    private String obtenerCorreoAdministrador(HttpSession session) {
        String correo = (String) session.getAttribute("adminCorreo");
        if (correo != null) {
            return correo;
        } else {
            throw new IllegalStateException("No hay un administrador en sesión.");
        }
    }

    // Muestra la página para crear guía rápida
    @GetMapping("/crear-guia-rapida")
    public String mostrarCrearGuiaRapida(HttpSession session) {
        if (session.getAttribute("adminCorreo") != null) {
            return "CrearGuiaRapida"; // Nombre del archivo HTML
        } else {
            return "redirect:/api/admin/index"; // Redirige al login si no está autenticado
        }
    }

    @PostMapping("/create-guia")
    public String crearGuia(@RequestParam("tipoDeGuia") String tipoDeGuia,
                            @RequestParam("titulo") String titulo,
                            @RequestParam("parrafo") String parrafo,
                            @RequestParam("imagen") List<MultipartFile> archivos,
                            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            String correo = obtenerCorreoAdministrador(session);
            guiaRapidaService.guardarGuia(tipoDeGuia, titulo, parrafo, archivos, correo);

            // Agregar un mensaje de éxito para mostrarlo en el frontend
            redirectAttributes.addFlashAttribute("successMessage", "¡Guía creada exitosamente!");

            // Redirigir a la misma página para mostrar el mensaje
            return "redirect:/api/admin/crear-guia-rapida";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al crear la guía: " + e.getMessage());
            return "redirect:/api/admin/crear-guia-rapida";
        }
    }

    // Muestra la página para editar guías rápidas
// Muestra la página para editar guías rápidas
    @GetMapping("/editar-guias-rapidas")
    public String mostrarEditarGuiasRapidas(HttpSession session,Model model) {
        if (session.getAttribute("adminCorreo") != null) {
// Obtener la lista de guías de la base de datos
            List<GuiaRapidaModel> guias = guiaRapidaService.obtenerTodasLasGuias(); // Asegúrate de tener este método en el servicio
            model.addAttribute("guias", guias); // Pasar las guías al modelo
            return "EditarGuiasRapidas"; // Nombre del archivo HTML
        } else {
            return "redirect:/api/admin/"; // Redirige al login si no está autenticado
        }
    }

    @DeleteMapping("/eliminar-guia/{id}")
    public String eliminarGuia(@PathVariable("id") Long id) {
        guiaRapidaService.eliminarGuia(id);
        return "redirect:/api/admin/editar-guias-rapidas";
    }


}
