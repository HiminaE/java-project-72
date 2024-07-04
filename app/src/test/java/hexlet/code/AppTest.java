/*
 * This source file was generated by the Gradle 'init' task
 */
package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlChecksRepository;
import hexlet.code.repository.UrlsRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

class AppTest {
    private static Javalin app;
    private static MockWebServer server;

    @BeforeEach
    public final void setApp() throws IOException, SQLException {
        app = App.getApp();
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        server = new MockWebServer();
        var html = Files.readString(Paths.get("src/test/resources/TestHTMLPage.html"));
        var serverResponse = new MockResponse()
                .addHeader("Content-Type", "text/html; charset=utf-8")
                .setResponseCode(200)
                .setBody(html);
        server.enqueue(serverResponse);
        server.start();
    }

    @AfterAll
    public static void shutdownMock() throws IOException {
        server.shutdown();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (srv, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testAddUri() {
        JavalinTest.test(app, (srv, client) -> {
            var requestBody = "url=http://localhost:7070/abc";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("http://localhost:7070");

            var url = UrlsRepository.getByName("http://localhost:7070")
                    .orElse(new Url("")).getName();
            assertThat(url).contains("http://localhost:7070");
        });
    }

    @Test
    public void testAddWrongUrl() {
        JavalinTest.test(app, (srv, client) -> {
            var requestBody = "url=abc";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);

            var urls = UrlsRepository.getByName("abc");
            assertThat(urls).isEmpty();
        });
    }

    @Test
    public void testShowAddedSites() {
        JavalinTest.test(app, (srv, client) -> {
            var url1 = new Url("https://www.example.com");
            var url2 = new Url("http://localhost:7070");
            UrlsRepository.save(url1);
            UrlsRepository.save(url2);

            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);

            var responseBody = response.body().string();
            assertThat(responseBody.contains("https://www.example.com"));
            assertThat(responseBody.contains("http://localhost:7070"));
        });
    }

    @Test
    public void testShowSingleUrl() {
        JavalinTest.test(app, (srv, client) -> {
            var url = new Url("https://www.example.com");
            UrlsRepository.save(url);
            var response = client.get("/urls/" + url.getId());

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://www.example.com");
        });
    }

    @Test
    public void testUrlCheckInnerContent()  throws SQLException {
        var baseUrl = server.url("/").toString();
        var actualUrl = new Url(baseUrl, new Timestamp(new Date().getTime()));
        UrlsRepository.save(actualUrl);
        JavalinTest.test(app, (srv, client) -> {
            var response = client.post("/urls/" + actualUrl.getId() + "/checks");
            assertThat(response.code()).isEqualTo(200);

            var urlCheck = UrlChecksRepository.find(actualUrl.getId()).orElseThrow();
            assertThat(urlCheck.getH1()).contains("H1");
            assertThat(urlCheck.getTitle()).contains("Title");
            assertThat(urlCheck.getDescription()).contains("Content");
        });
    }
}
