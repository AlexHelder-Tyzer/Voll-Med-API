package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroMedico(
        //NotBlak hace no vaciony no nulos, al igual que NotNUll
        @NotBlank(message = "{email.obligatorio}")
        String nombre,
        @NotBlank(message = "{email.obligatorio}")
        @Email(message = "{email.invalido}")
        String email,
        @NotBlank(message = "{telefono.obligatorio}")
        @Size(min=0, max=15)
        String telefono,
        @NotBlank(message = "{crm.obligatorio}")
        @Pattern(regexp = "\\d{4,6}", message = "{crm.invalido}")
        String documento,
        @NotNull(message = "{especialidad.obligatorio}")
        Especialidad especialidad,
        @NotNull(message = "{direccion.obligatorio}") //Not null porque es un objeto
                @Valid
        DatosDireccion direccion) {
}
