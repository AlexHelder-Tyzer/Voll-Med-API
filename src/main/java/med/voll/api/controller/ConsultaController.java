package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosCancelarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

// Controller y ResponseBody se puede reempalzar por RestController
@Controller
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name="bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

    @GetMapping
    public ResponseEntity<Page<DatosDetalleConsulta>> listar(@PageableDefault(size = 10, sort = {"fecha"}) Pageable paginacion){
        var response = service.consultar(paginacion);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    @Operation(
            summary="Registra una consulta en la base de datos,",
            description = "",
            tags = {"consulta", "post"}
    )
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos){
        var response = service.agendar(datos);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Transactional
    @Operation(
            summary="Cancela una consulta en la base de datos,",
            description = "Requiere motivo",
            tags = {"consulta", "delete"}
    )
    private ResponseEntity cancelar(@RequestBody @Valid DatosCancelarConsulta datos){
        service.cancelar(datos);
        return ResponseEntity.noContent().build();
    }
}
