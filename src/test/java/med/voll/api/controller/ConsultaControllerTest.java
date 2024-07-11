package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;
    @Autowired
    private JacksonTester<DatosDetalleConsulta> detalleConsultaJacksonTester;

    @MockBean
    private AgendaDeConsultaService agendaDeConsultaService;

    @Test
    @DisplayName("Deberia retornar estado HTTP 400 cuando los datos ingresados sean invalidos")
    @WithMockUser
    void agendarEscenario1() throws Exception {
        // Given
        String invalidRequestBody = "{}";  // Aquí se simula un body inválido

        // When
        var response = mvc.perform(post("/consultas")
                        .contentType("application/json")
                        .content(invalidRequestBody))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deberia retornar estado HTTP 200 cuando los datos ingresados son validos")
    @WithMockUser
    void agendarEscenario2() throws Exception {
        // Given
        var fecha = LocalDateTime.now().plusHours(1);
        var especialdiad = Especialidad.CARDIOLOGIA;
        var datos = new DatosDetalleConsulta(null, 2L, 5L, fecha);

        // When
        when(agendaDeConsultaService.agendar(any())).thenReturn(datos);

        var response = mvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(agendarConsultaJacksonTester.write(new DatosAgendarConsulta(2L,5L,fecha,especialdiad)).getJson()))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = detalleConsultaJacksonTester.write(new DatosDetalleConsulta(null, 2L, 5L, fecha)).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }


}