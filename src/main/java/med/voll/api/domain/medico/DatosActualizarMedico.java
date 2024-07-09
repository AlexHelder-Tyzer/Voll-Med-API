package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosActualizarMedico(
        @NotNull
        Long id, // para actualizar necesitamos el id
        String nombre,
        String documento,
        DatosDireccion direccion
) {
}
