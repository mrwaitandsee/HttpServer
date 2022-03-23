package httpserver.entity;

public record Header(String key, String value) {
    @Override
    public String toString() {
        return key + ": " + value;
    }
}
