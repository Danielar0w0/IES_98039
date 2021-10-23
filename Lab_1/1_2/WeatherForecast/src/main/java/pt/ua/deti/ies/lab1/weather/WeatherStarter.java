package pt.ua.deti.ies.lab1.weather;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// import weather.ipma_client.IpmaCityForecast;
// import weather.ipma_client.IpmaService;

// import java.util.logging.Logger;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherStarter {

    private static final int CITY_ID_AVEIRO = 1010500;
    /*
    loggers provide a better alternative to System.out.println
    https://rules.sonarsource.com/java/tag/bad-practice/RSPEC-106
     */

    // private static final Logger logger = Logger.getLogger(WeatherStarter.class.getName());
    private static Logger logger = LogManager.getLogger(WeatherStarter.class.getName());

    public static void  main(String[] args ) {

        /*
        get a retrofit instance, loaded with the GSon lib to convert JSON into objects
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IpmaService service = retrofit.create(IpmaService.class);

        Call<IpmaCityForecast> callSync = service.getForecastForACity(CITY_ID_AVEIRO);
        if (args.length > 0) {
            try {
                int code = Integer.parseInt(args[0]);
                callSync = service.getForecastForACity(code);
            } catch (NumberFormatException ex) {
                System.err.println("Invalid city code!");
                logger.warn("Invalid city code!");
                System.exit(-1);
            }
        }

        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                logger.info( "max temp for today: " + forecast.getData().
                        listIterator().next().getTMax());
            } else {
                logger.info( "No results!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.exit(0);
    }
}