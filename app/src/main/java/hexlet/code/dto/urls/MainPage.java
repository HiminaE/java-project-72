package hexlet.code.dto.urls;

import hexlet.code.dto.BasePage;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class MainPage extends BasePage {
    public MainPage(String flash) {
        super(flash);
    }
}
