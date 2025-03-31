package kz.iitu.se241m.bookservice.config;

public class DataSourceInfo {
    private String url;
    private String username;

    public DataSourceInfo(String url, String username) {
        this.url = url;
        this.username = username;
    }

    public void connect() {
        System.out.println("DataSourceInfo: Connecting to " + url + " as " + username);
    }

    @Override
    public String toString() {
        return "DataSourceInfo{" + "url='" + url + '\'' + ", username='" + username + '\'' + '}';
    }
}