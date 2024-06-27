package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UrlPage extends BasePage {
    private Url url;
    private List<UrlCheck> urlChecks;

    public UrlPage(Url url, List<UrlCheck> urlChecks, String flash) {
        super(flash);
        this.url = url;
        this.urlChecks = urlChecks;
    }
}
