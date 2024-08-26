package org.adaschool.Weather;

import org.adaschool.Weather.controller.WeatherReportController; // Agrega esta línea
import org.adaschool.Weather.service.WeatherReportService;
import org.adaschool.Weather.data.WeatherReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class WeatherReportControllerTest {

    @InjectMocks
    private WeatherReportController weatherReportController;

    @Mock
    private WeatherReportService weatherReportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherReport() {
        // Configurar el mock del servicio
        WeatherReport mockReport = new WeatherReport();
        mockReport.setTemperature(20.0);
        mockReport.setHumidity(50.0);

        when(weatherReportService.getWeatherReport(anyDouble(), anyDouble())).thenReturn(mockReport);

        // Ejecutar el método del controlador
        ResponseEntity<WeatherReport> response = weatherReportController.getWeatherReport(4.60971, -74.08175);

        // Verificar el resultado
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(20.0, response.getBody().getTemperature());
        assertEquals(50.0, response.getBody().getHumidity());

        // Verificar que el servicio fue llamado correctamente
        verify(weatherReportService).getWeatherReport(4.60971, -74.08175);
    }

    @Test
    public void testGetWeatherReport_ServiceThrowsException() {
        // Configurar el mock para lanzar una excepción
        when(weatherReportService.getWeatherReport(anyDouble(), anyDouble())).thenThrow(new RuntimeException("Error retrieving weather data"));

        // Ejecutar el método del controlador y verificar la excepción
        ResponseEntity<WeatherReport> response = weatherReportController.getWeatherReport(4.60971, -74.08175);

        // Verificar el resultado
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Verificar que el servicio fue llamado correctamente
        verify(weatherReportService).getWeatherReport(4.60971, -74.08175);
    }
}
