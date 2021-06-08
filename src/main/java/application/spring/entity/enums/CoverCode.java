package application.spring.entity.enums;

public enum CoverCode {

    COVER_500("Крышка 500"),
    COVER_250("Крышка 250"),
    COVER_125("Крышка 125"),
    COVER_50("Крышка 50"),
    COVER_30("Крышка 30"),
    COVER_10("Крышка 10");

    public final String label;

    private CoverCode(String label){
        this.label = label;
    }
}
