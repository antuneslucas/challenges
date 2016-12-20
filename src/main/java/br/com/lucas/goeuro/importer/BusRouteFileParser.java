package br.com.lucas.goeuro.importer;

import static java.util.Objects.isNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class BusRouteFileParser {

    private static final Logger LOG = LoggerFactory.getLogger(BusRouteFileParser.class);

    public Map<Integer, Set<Integer>> parseBusRoutes(String fileName) throws IOException {
        Map<Integer, Set<Integer>> busRoutesByStationId = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            String firstLine = reader.readLine();
            final int busRoutesSize = getBusRoutesSize(firstLine);
            LOG.debug("Will try to read {} Bus Routes from file:{}", busRoutesSize, fileName);

            int linesReadCounter = 0;
            String line;
            while(((line = reader.readLine()) != null) && linesReadCounter < busRoutesSize) {
                addBusRoute(busRoutesByStationId, line);
                linesReadCounter++;
            }

            Assert.isTrue(linesReadCounter == busRoutesSize, "There is no sufficient bus routes in the specified file" + fileName);
            LOG.info("Finished parsing {} bus routes from file: {}", busRoutesSize, fileName);
        }

        return busRoutesByStationId;
    }

    private int getBusRoutesSize(String line) {
        Objects.requireNonNull(line, "File must have at least 2 lines, the first one with the number of bus routes, followed by N bus routes");
        return Integer.parseInt(line.trim());
    }

    private void addBusRoute(Map<Integer, Set<Integer>> busRoutesByStationId, String line) {
        String[] busRouteIdAndStationIds = line.trim().split(" ");
        Integer busRouteId = Integer.valueOf(busRouteIdAndStationIds[0]);
        int length = busRouteIdAndStationIds.length;

        validateBusRouteLineLength(length, line);

        Arrays.stream(busRouteIdAndStationIds, 1, length)
                .map(Integer::valueOf)
                .forEach(stationId -> {
                    Set<Integer> busRouteIds = busRoutesByStationId.get(stationId);
                    if (isNull(busRouteIds))
                        busRoutesByStationId.put(stationId, (busRouteIds = new HashSet<>()));

                    busRouteIds.add(busRouteId);
                });
    }

    private void validateBusRouteLineLength(int busRouteLineLength, String line) {
        Assert.isTrue(busRouteLineLength >= 3, String.format("Error reading bus route line, must have at least 3 numbers but there is only %d. Content of line with error:\"%s\"", busRouteLineLength, line));
    }

}
