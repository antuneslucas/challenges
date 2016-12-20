package br.com.lucas.goeuro.service;

public interface BusRouteService {

    boolean existsDirectBusRoute(Integer departureStationId, Integer arrivalStationId);
    
}
