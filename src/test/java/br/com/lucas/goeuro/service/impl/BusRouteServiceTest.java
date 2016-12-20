package br.com.lucas.goeuro.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.lucas.goeuro.importer.BusRouteFileParser;

public class BusRouteServiceTest {

    @Mock private BusRouteFileParser busRouteFileParser;
    @InjectMocks private BusRouteServiceImpl busRouteService;

    @Before public void initMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(busRouteFileParser.parseBusRoutes(anyString())).thenReturn(buildBusRouteCache());
        busRouteService.initCache();
    }

    @Test
    public void shouldReturnFalseIfStationsAreEquals() throws IOException {
        assertThat(busRouteService.existsDirectBusRoute(1, 1), is(false));
        assertThat(busRouteService.existsDirectBusRoute(2, 2), is(false));
        assertThat(busRouteService.existsDirectBusRoute(3, 3), is(false));
        assertThat(busRouteService.existsDirectBusRoute(4, 4), is(false));
    }

    @Test
    public void shouldReturnFalseIfOneOrBothStationsDontExist() throws IOException {
        assertThat(busRouteService.existsDirectBusRoute(1, 5), is(false));
        assertThat(busRouteService.existsDirectBusRoute(5, 2), is(false));
        assertThat(busRouteService.existsDirectBusRoute(5, 6), is(false));
    }

    @Test
    public void shouldReturnTrueIfStationsAreFoundAndHaveAtLeastOneConnection() throws IOException {
        //intersection is route 30
        assertThat(busRouteService.existsDirectBusRoute(1, 2), is(true));
        assertThat(busRouteService.existsDirectBusRoute(2, 1), is(true)); //parameters order doesn't matter

        //intersection are routes 40, 50
        assertThat(busRouteService.existsDirectBusRoute(2, 3), is(true));
        assertThat(busRouteService.existsDirectBusRoute(3, 2), is(true));
    }

    @Test
    public void shouldReturnFalseIfStationsAreFoundButAreNotConnected() throws IOException {
        assertThat(busRouteService.existsDirectBusRoute(1, 3), is(false));
        assertThat(busRouteService.existsDirectBusRoute(1, 4), is(false));
        assertThat(busRouteService.existsDirectBusRoute(2, 4), is(false));
        assertThat(busRouteService.existsDirectBusRoute(3, 4), is(false));
    }

    private Map<Integer, Set<Integer>> buildBusRouteCache() {
        Map<Integer, Set<Integer>> dummyBusRouteCache = new HashMap<>();

        //station 1 -> routes 10, 20, 30
        dummyBusRouteCache.put(1, new HashSet<>(Arrays.asList(10, 20, 30)));

        //station 2 -> routes 30, 40, 50
        dummyBusRouteCache.put(2, new HashSet<>(Arrays.asList(30, 40, 50)));

        //station 3 -> routes 40, 50, 60
        dummyBusRouteCache.put(3, new HashSet<>(Arrays.asList(40, 50, 60)));

        //station 4 -> routes 70, 80, 90
        dummyBusRouteCache.put(4, new HashSet<>(Arrays.asList(70, 80, 90)));

        return dummyBusRouteCache;
    }

}
