package httpserver.parser;

import httpserver.entity.Parameter;

import java.util.ArrayList;
import java.util.List;

public class RequestTargetParser {
    public static List<Parameter> parseGetParameters(String text) {
        var parameters = new ArrayList<Parameter>();
        var data = text.split("&");
        for (var dirtText : data) {
            var dirtTextArray = dirtText.split("=", 2);
            if (dirtTextArray.length == 2) parameters.add(new Parameter(dirtTextArray[0], dirtTextArray[1]));
        }
        return parameters;
    }
}
