package anhbm.nws.weatherapp.domains.model;


import anhbm.nws.weatherapp.api.weather.WeatherResponse;

public class WeatherDomain {
    private WeatherResponse model;

    public WeatherResponse getModel() {
        return model;
    }

    public void setModel(WeatherResponse model) {
        this.model = model;
    }
}
