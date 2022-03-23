package httpserver.entity;

import java.util.List;

public record Headers(List<Header> list) {
    @Override
    public String toString() {
        var builder = new StringBuilder();
        for (var i = 0; i < list.size(); i++) {
            builder.append(list.get(i));
            if (list.size() - 1 != i) builder.append("\r\n");
        }
        return builder.toString();
    }

    public String getHeaderByKey(String key) {
        for (Header header : list) {
            if (header.key().equals(key)) return header.value();
        }
        return null;
    }
}
