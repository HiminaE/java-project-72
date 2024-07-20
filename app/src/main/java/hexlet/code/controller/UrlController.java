package hexlet.code.controller;

import hexlet.code.dto.urls.MainPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.repository.UrlChecksRepository;
import hexlet.code.utils.Paths;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Map;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {
    public static void enterUrl(Context ctx) {
        var flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");
        var page = new MainPage((String) flash);
        page.setFlashType(flashType);
        ctx.render("urls/mainPage.jte", model("page", page));
        page.setFlash(null);
    }

    public static void addUrl(Context ctx) throws SQLException {
        var uriParam = ctx.formParam("url");
        try {
            var site = new Url(parseUrl(uriParam));
            if (isExist(site)) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flashType", "error");
                ctx.redirect(Paths.rootPath());
            } else {
                UrlsRepository.save(site);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.sessionAttribute("flashType", "success");
                ctx.redirect(Paths.urlsPath());
            }
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect(Paths.rootPath());
        }

    }

//    public static void addUrl(Context ctx) throws SQLException {
//        var inputUrl = ctx.formParam("url");
//        URI parsedUrl;
//        try {
//            parsedUrl = new URI(inputUrl);
//        } catch (Exception e) {
//            ctx.sessionAttribute("flash", "Некорректный URL");
//            ctx.sessionAttribute("flash-type", "danger");
//            ctx.redirect(Paths.rootPath());
//            return;
//        }
//        String normalizedUrl = String.format("%s://%s%s",
//            parsedUrl.getScheme(),
//            parsedUrl.getHost(),
//            parsedUrl.getPort() == -1 ? "" : ":" + parsedUrl.getPort()).toLowerCase();
//        Url url = UrlsRepository.getByName(normalizedUrl).orElse(null);
//        if (url != null) {
//            ctx.sessionAttribute("flash", "Страница уже существует");
//            ctx.sessionAttribute("flash-type", "info");
//        } else {
//            Url newUrl = new Url(normalizedUrl);
//            UrlsRepository.save(newUrl);
//            ctx.sessionAttribute("flash", "Страница успешно добавлена");
//            ctx.sessionAttribute("flash-type", "success");
//        }
//        ctx.redirect("/urls");
//    }

    public static void showAddedUrls(Context ctx) throws SQLException {
        var flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");
        var urls = UrlsRepository.getUrls();
        Map<Long, UrlCheck> lastChecks = UrlChecksRepository.getLastChecks();
        var page = new UrlsPage(urls, (String) flash, lastChecks);
        page.setFlashType(flashType);
        ctx.render("urls/showAddedUrls.jte", model("page", page));
        page.setFlash(null);
    }

    public static void showInfoAboutUrl(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var site = UrlsRepository.getById(id)
                .orElseThrow(() -> new NotFoundResponse("Site with id: " + " not found"));

        String flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");
        var urlChecks = UrlChecksRepository.getUrlChecksByUrlId(id);
        var page = new UrlPage(site, urlChecks, flash);
        page.setFlashType(flashType);

        ctx.render("urls/showInfoAboutUrl.jte", model("page", page));
        page.setFlash(null);
    }

    private static String parseUrl(String link) throws
            URISyntaxException, MalformedURLException {
        var uri = new URI(link);
        var uriToUrl = uri.toURL();
        return uriToUrl.getProtocol() + "://" + uriToUrl.getAuthority();
    }

    private static boolean isExist(Url url) {
        var name = url.getName();
        return UrlsRepository.getByName(name).isPresent();
    }
}
