package org.apiweather.database.converter;

import org.apiweather.database.repository.WeatherDataEntity;
import org.apiweather.dto.TemperatureResponse;
import org.apiweather.dto.WeatherDataResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WeatherDataEntityToWeatherDataConverterTest {

    @InjectMocks
    private WeatherDataEntityToWeatherDataConverter converter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertWeatherDataEntityToWeatherDataResponse() {
        // Configuracion y preparacion de entorno
        WeatherDataEntity entity = mock(WeatherDataEntity.class);
        when(entity.getCity()).thenReturn("Rosario");
        when(entity.getProvince()).thenReturn("Santa Fe");
        when(entity.getLocalObservationDateTime()).thenReturn("2023-06-27T12:34:56-03:00");
        when(entity.getWeatherText()).thenReturn("Sunny");
        when(entity.isHasPrecipitation()).thenReturn(false);
        when(entity.getPrecipitationType()).thenReturn("Rain");
        when(entity.isDayTime()).thenReturn(true);
        when(entity.getTempMetricValue()).thenReturn(25.0);
        when(entity.getTempMetricUnit()).thenReturn("C");
        when(entity.getTempImperialValue()).thenReturn(77.0);
        when(entity.getTempImperialUnit()).thenReturn("F");

        // Invocacion del metodo
        WeatherDataResponse response = converter.convert(entity);

        // Validaciones
        assert response != null;
        assertEquals("Rosario", response.getCiudad());
        assertEquals("Santa Fe", response.getProvincia());
        assertEquals("2023-06-27", response.getFecha());
        assertEquals("12:34:56", response.getHora());
        assertEquals("-03:00", response.getHusoHorario());
        assertEquals("Sunny", response.getEstadoTiempo());
        assertFalse(response.isHayPrecipitacion());
        assertEquals("Rain", response.getTipoPrecipitacion());
        assertTrue(response.isEsHorarioDiurno());
        assertEquals(new TemperatureResponse(25.0, "C"), response.getTemperaturaCelsius());
        assertEquals(new TemperatureResponse(77.0, "F"), response.getTemperaturaFarenheit());
    }
}
