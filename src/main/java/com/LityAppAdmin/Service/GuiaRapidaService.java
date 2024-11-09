package com.LityAppAdmin.Service;

import com.LityAppAdmin.Model.AdministradorModel;
import com.LityAppAdmin.Model.GuiaRapidaModel;
import com.LityAppAdmin.Model.ImagenModel;
import com.LityAppAdmin.Repository.IGuiaRapidaRepository;
import com.LityAppAdmin.Repository.IImagenRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuiaRapidaService {

    private final IGuiaRapidaRepository guiaRapidaRepository;
    private final IImagenRepository imagenRepository;

    public GuiaRapidaService(IGuiaRapidaRepository guiaRapidaRepository, IImagenRepository imagenRepository) {
        this.guiaRapidaRepository = guiaRapidaRepository;
        this.imagenRepository = imagenRepository;
    }

    public void guardarGuia(String tipoDeGuia, String titulo, String parrafo, List<MultipartFile> archivos, String correoAdmin) throws IOException {
        GuiaRapidaModel guia = new GuiaRapidaModel();
        guia.setTipoDeGuia(tipoDeGuia);
        guia.setTitulo(titulo);
        guia.setParrafo(parrafo);
        guia.setCorreoAdministrador(correoAdmin); // Asigna el correo del administrador

        guiaRapidaRepository.save(guia);

        Path carpetaRaiz = Paths.get("").toAbsolutePath().resolve("imagenes");
        if (!Files.exists(carpetaRaiz)) {
            Files.createDirectories(carpetaRaiz);
        }

        List<ImagenModel> imagenes = archivos.stream().map(archivo -> {
            try {
                String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
                Path rutaCompleta = carpetaRaiz.resolve(nombreArchivo);

                Files.copy(archivo.getInputStream(), rutaCompleta);

                ImagenModel img = new ImagenModel();
                img.setRuta("imagenes/" + nombreArchivo);
                img.setTipoEntidad("GuiaRapida");
                img.setEntidadId(guia.getId());
                return img;
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la imagen", e);
            }
        }).collect(Collectors.toList());

        imagenRepository.saveAll(imagenes);
    }



}
