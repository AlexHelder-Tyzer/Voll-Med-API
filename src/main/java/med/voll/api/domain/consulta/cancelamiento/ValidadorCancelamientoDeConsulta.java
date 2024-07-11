package med.voll.api.domain.consulta.cancelamiento;

import med.voll.api.domain.consulta.DatosCancelarConsulta;

public interface ValidadorCancelamientoDeConsulta {
    void validar(DatosCancelarConsulta datos);
}
