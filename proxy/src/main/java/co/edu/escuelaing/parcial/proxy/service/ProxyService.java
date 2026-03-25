package co.edu.escuelaing.parcial.proxy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class ProxyService {

    private final String activeHost;
    private final String activePort;
    private final String passiveHost;
    private final String passivePort;
    private final int timeoutMs;

    public ProxyService(@Value("${list.active.host}") String activeHost,
                            @Value("${list.active.port}") String activePort,
                            @Value("${list.passive.host}") String passiveHost,
                            @Value("${list.passive.port}") String passivePort,
                            @Value("${list.call.timeout-ms}") int timeoutMs) {
        this.activeHost = activeHost;
        this.activePort = activePort;
        this.passiveHost = passiveHost;
        this.passivePort = passivePort;
        this.timeoutMs = timeoutMs;
    }

    public String flinearSearch(String value) throws URISyntaxException {
        return f("/api/list/linearSearch", value);
    }

    public String forwardbinarySearch(String value) throws URISyntaxException {
        return f("/api/math/binarySearch", value);
    }

    private String f(String path, String value) throws URISyntaxException {
        try {
            return call(activeHost, activePort, path, value);
        } catch (IOException activeException) {
            System.out.println("Instancia 1 fallo, intentando con la 2 " + activeException.getMessage());
            try {
                return call(passiveHost, passivePort, path, value);
            } catch (IOException passiveException) {
                throw new IllegalStateException("fallaron las 2 instancias");
            }
        }
    }

    private String call(String host, String port, String path, String value) throws IOException, URISyntaxException {
        String url = "http://" + host + ":" + port + path + "?value=" + value;
        return execute(url);
    }

    private String execute(String urlT) throws IOException, URISyntaxException {
        URL url = new URI(urlT).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(timeoutMs);
        connection.setReadTimeout(timeoutMs);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = connection.getResponseCode();
        InputStream inputStream = responseCode >= 200 && responseCode < 300
                ? connection.getInputStream()
                : connection.getErrorStream();

        String responseBody = readStream(inputStream);

        if (responseCode >= 200 && responseCode < 300) {
            return responseBody;
        }

        throw new IOException("respondio " + responseCode + ": " + responseBody);
    }

    private String readStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();
        }
    }
}
