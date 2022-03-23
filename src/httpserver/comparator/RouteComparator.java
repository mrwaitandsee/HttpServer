package httpserver.comparator;

import httpserver.router.Route;

import java.util.Comparator;

public class RouteComparator implements Comparator<Route> {
    @Override
    public int compare(Route a, Route b) {
        return a.getUrl().split("/").length - b.getUrl().split("/").length;
    }
}
