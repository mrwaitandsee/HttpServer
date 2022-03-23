package httpserver.entity;

public record Parameter(String key, String value) {
    @Override
    public String toString() {
        return key + ";" + value;
    }
}
