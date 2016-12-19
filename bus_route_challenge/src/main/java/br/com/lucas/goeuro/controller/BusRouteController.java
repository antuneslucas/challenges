package br.com.lucas.goeuro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucas.goeuro.model.BusRouteDirectConnection;
import br.com.lucas.goeuro.service.BusRouteService;

@RestController
public class BusRouteController {

    @Autowired private BusRouteService busRouteService;

    @RequestMapping(value = "/api/direct", method = RequestMethod.GET)
    public BusRouteDirectConnection isDirect(@RequestParam("dep_sid") Integer departureStationId, @RequestParam("arr_sid") Integer arrivalStationId) {
        return new BusRouteDirectConnection(departureStationId, arrivalStationId, busRouteService.existsDirectBusRoute(departureStationId, arrivalStationId));
    }

}
