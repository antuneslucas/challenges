package br.com.lucas.goeuro.service.impl;

import static java.util.Objects.isNull;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.goeuro.importer.BusRouteFileParser;
import br.com.lucas.goeuro.model.BusRouteConstants;
import br.com.lucas.goeuro.service.BusRouteService;

@Service
public class BusRouteServiceImpl implements BusRouteService {

    private static final Logger LOG = LoggerFactory.getLogger(BusRouteServiceImpl.class);

    @Autowired private BusRouteFileParser busRouteFileParser;

    //in-Memory Cache that maps stationId to routeIds that contains this station
    private Map<Integer, Set<Integer>> busRouteCache;

    //package private for testing purposes
    @PostConstruct
    void initCache() {
        String fileName = System.getProperty(BusRouteConstants.BUS_ROUTE_FILE_NAME.getKey());

        try {
            busRouteCache = busRouteFileParser.parseBusRoutes(fileName);
            LOG.info("Bus Routes Cache warmed successfully");
        } catch(Exception e) {
            LOG.error("Something went wrong reading file: {}", fileName);
            LOG.error("Shutting system down", e);
            System.exit(1);
        }
    }

    @Override
    public boolean existsDirectBusRoute(Integer departureStationId, Integer arrivalStationId) {
        if(isNull(departureStationId) || isNull(arrivalStationId) || departureStationId.equals(arrivalStationId))
            return false;

        Set<Integer> departureStationBusRoutes = busRouteCache.get(departureStationId);
        if(isNull(departureStationBusRoutes) || departureStationBusRoutes.isEmpty())
            return false;

        Set<Integer> arrivalStationBusRoutes = busRouteCache.get(arrivalStationId);
        if(isNull(arrivalStationBusRoutes) || arrivalStationBusRoutes.isEmpty())
            return false;

        return existsDirectBusRoute(departureStationBusRoutes, arrivalStationBusRoutes);
    }

    private boolean existsDirectBusRoute(Set<Integer> departureStationBusRoutes, Set<Integer> arrivalStationBusRoutes) {
        if(departureStationBusRoutes.size() > arrivalStationBusRoutes.size()) //try to iterate over the smallest set
            return !Collections.disjoint(departureStationBusRoutes, arrivalStationBusRoutes);

        return !Collections.disjoint(arrivalStationBusRoutes, departureStationBusRoutes);
    }

}
