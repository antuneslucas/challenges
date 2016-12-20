package br.com.lucas.goeuro.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"depSid", "arrSid", "directBusRoute"})
public class BusRouteDirectConnection {

    private final Integer depSid;
    private final Integer arrSid;
    private final boolean directBusRoute;

    public BusRouteDirectConnection(Integer depSid, Integer arrSid, boolean directBusRoute) {
        this.depSid = depSid;
        this.arrSid = arrSid;
        this.directBusRoute = directBusRoute;
    }

    public Integer getDepSid() {
        return depSid;
    }

    public Integer getArrSid() {
        return arrSid;
    }

    public boolean isDirectBusRoute() {
        return directBusRoute;
    }

}
