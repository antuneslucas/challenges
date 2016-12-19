package br.com.lucas.goeuro.model;

public enum BusRouteConstants {

    BUS_ROUTE_FILE_NAME("busroute.filename");

    private final String key;

    private BusRouteConstants(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
