package br.com.lucas.goeuro.importer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BusRouteFileParserTest {

    /*  Bus route example file, located on /test/resources folder, with the following content:
        3
        0 0 1 2 3 4
        1 3 1 6 5
        2 0 6 4
    */
    private final String fileName = this.getClass().getResource("/bus_route_example.txt").getPath();

    @Spy private BusRouteFileParser parser;

    @Test
    public void testParsedResultContainsSevenEntries() throws IOException {
        Map<Integer, Set<Integer>> parseBusRoutes = parser.parseBusRoutes(fileName);

        assertThat(parseBusRoutes, is(notNullValue()));
        assertThat(parseBusRoutes.entrySet().size(), is(7));

        Set<Integer> busRoutesByStationIdNumberFour = parseBusRoutes.get(4);

        assertThat(busRoutesByStationIdNumberFour.contains(0), is(true));
        assertThat(busRoutesByStationIdNumberFour.contains(1), is(false));
        assertThat(busRoutesByStationIdNumberFour.contains(2), is(true));

    }

    // stationId 4 occurs only on bus routes 0 and 2
    @Test
    public void testParsedResultContainsExpectedValues() throws IOException {
        Map<Integer, Set<Integer>> parseBusRoutes = parser.parseBusRoutes(fileName);

        Set<Integer> busRoutesByStationIdNumberFour = parseBusRoutes.get(4);

        assertThat(busRoutesByStationIdNumberFour.contains(0), is(true));
        assertThat(busRoutesByStationIdNumberFour.contains(1), is(false));
        assertThat(busRoutesByStationIdNumberFour.contains(2), is(true));

    }

}
