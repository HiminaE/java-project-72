package hexlet.code.utils;

public class Paths {
    public static String rootPath() {
        return "/";
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlsIdPath(Long id) {
        return "/urls/" + id;
    }

    public static String urlsIdPathWithoutId() {
        return "/urls/{id}";
    }

    public static String urlsIdChecksPath(Long id) {
        return "/urls/" + id + "/checks";
    }

    public static String urlsIdChecksPathWithoutId() {
        return "/urls/{id}/checks";
    }
}