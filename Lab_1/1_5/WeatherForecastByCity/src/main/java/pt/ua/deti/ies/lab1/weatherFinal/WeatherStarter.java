package pt.ua.deti.ies.lab1.weatherFinal;

import pt.ua.deti.ies.lab1.weatherFinal.ApiClientLogic;
import pt.ua.deti.ies.lab1.weatherFinal.IpmaCityForecast;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherStarter {

    private static final int CITY_ID_AVEIRO = 1010500;

    // private static final Logger logger = Logger.getLogger(WeatherStarter.class.getName());
    private static Logger logger = LogManager.getLogger(WeatherStarter.class.getName());

    public static void  main(String[] args ) {

        ApiClientLogic logic = new ApiClientLogic();
        
        // Request forecast
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                logger.info(args[i]);
                // Check if input is a valid city code/name
                if (!logic.sendRequest(args[i])) {
                    logger.warn("Invalid input (Not city)!");
                    continue;
                }
                getResponse(logic);
            }
        } else {
            // Default city (Aveiro)
            logger.info("Currently using default code (Aveiro)");
            logic.sendRequest(CITY_ID_AVEIRO);
            getResponse(logic);
        }

        System.exit(0);
    }

    public static void getResponse(ApiClientLogic logic) {
        // Get response - forecast
        IpmaCityForecast forecast = logic.getForecast();
        if (forecast != null) {
            logger.info("Max temp for today: " + forecast.getData().
                    listIterator().next().getTMax());
        } else
            logger.info( "No results!");
    }
}