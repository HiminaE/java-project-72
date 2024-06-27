package hexlet.code.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BasePage {
    private String flash;
    private String flashType;

    public BasePage(String flash) {
        this.flash = flash;
    }
}
