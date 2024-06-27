package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;


@Getter
@Setter
public class UrlsPage extends BasePage {
    private List<Url> urls;
    private Map<Long, UrlCheck> lastChecks;

    public UrlsPage(List<Url> urls, String flash, Map<Long, UrlCheck> lastChecks) {
        super(flash);
        this.urls = urls;
        this.lastChecks = lastChecks;
    }
}
