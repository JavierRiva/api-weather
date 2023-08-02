# api-weather
API para obtener condiciones climáticas de una ciudad de Argentina

# Datos de contacto
Javier Riva
jrivazabala@gmail.com

# Instrucciones
- Clonar el proyecto desde el repositorio 'https://github.com/JavierRiva/api-weather.git'.
- Ejecutarlo desde 'ApiWeatherApplication'.
- Desde alguna herramienta de prueba de APIs (Postman) configurar el siguiente endpoint:
    GET http://localhost:8080/weather/current-conditions
    A su vez, configurar los params 'ciudad' y 'provincia'(obligatorios)
- Si no se encuentra la ciudad indicada o se encuentra la ciudad pero no se corresponde con la provincia, la respuesta HTTP será un 400(Bad Request) con el mensaje de error correspondiente.

# Ejemplo
- Request: GET http://localhost:8080/weather/current-conditions?ciudad=Necochea&provincia=Buenos Aires
- Response: 
    Status: 200 OK
    Body:
        {
            "ciudad": "Necochea",
            "provincia": "Buenos Aires",
            "fecha": "2023-08-02",
            "hora": "08:55:00",
            "husoHorario": "-03:00",
            "estadoTiempo": "Mostly sunny",
            "hayPrecipitacion": false,
            "esHorarioDiurno": true,
            "temperaturaCelsius": {
                "valorTemperatura": 16.7,
                "unidadTemperatura": "C"
            },
            "temperaturaFarenheit": {
                "valorTemperatura": 62.0,
                "unidadTemperatura": "F"
            }
        }


