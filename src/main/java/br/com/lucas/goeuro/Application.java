package br.com.lucas.goeuro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import br.com.lucas.goeuro.model.BusRouteConstants;

@SpringBootApplication
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    private static final Marker CONSOLE_MARKER = MarkerFactory.getMarker("console");

    public static void main(String args[]) {
        if (args.length == 0 || args[0].trim().length() == 0) {
            LOG.warn(CONSOLE_MARKER, "File name is required! Usage: java -jar goeuro_busroute.jar <filename>");
            LOG.info(CONSOLE_MARKER, "e.g. java -jar goeuro_busroute.jar /path/to/file.txt");
        } else {
            System.getProperties().setProperty(BusRouteConstants.BUS_ROUTE_FILE_NAME.getKey(), args[0]);
            SpringApplication.run(Application.class, args);
        }
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }
}