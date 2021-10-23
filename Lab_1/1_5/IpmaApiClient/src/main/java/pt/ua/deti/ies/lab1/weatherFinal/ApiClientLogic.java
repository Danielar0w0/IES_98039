package pt.ua.deti.ies.lab1.weatherFinal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class ApiClientLogic {

    private IpmaService service;
    private Call<IpmaCityForecast> callSync;

    public ApiClientLogic() {
        /*
        get a retrofit instance, loaded with the GSon lib to convert JSON into objects
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(IpmaService.class);
    }

    public boolean sendRequest(int code) {
        callSync = service.getForecastForACity(code);
        return true;
    }

    public boolean sendRequest(Integer code) {
        callSync = service.getForecastForACity(code);
        return true;
    }

    public boolean sendRequest(String input) {
        try {
            // If the input is the code of a city
            int code = Integer.parseInt(input);
            callSync = service.getForecastForACity(code);
            return true;
        } catch (NumberFormatException ex) {
            // If the input is the name of a city
            if (getCode(input) != null)
                return true;
            // Else
            return false;
        }
    }

    public IpmaCityForecast getForecast() {
        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();
            return forecast;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Integer getCode(String name) {
        try {
            Call<IpmaCity> otherCallSync = service.getForecastForACityByName();
            Response<IpmaCity> apiResponse = otherCallSync.execute();
            
            // Attempt to find city
            List<City> cities = apiResponse.body().getData();
            for (City c: cities) {
                // If city is found
                if (c.getLocal().equalsIgnoreCase(name)) {
                    int code = c.getGlobalIdLocal();
                    this.callSync = service.getForecastForACity(code);
                    return code;
                }
            }

            // City not found
            return null;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
