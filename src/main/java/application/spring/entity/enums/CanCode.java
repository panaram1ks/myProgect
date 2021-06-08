package application.spring.entity.enums;

public enum CanCode {

    CAN_500("Банка 500"),
    CAN_250("Банка 250"),
    CAN_125("Банка 125"),
    CAN_50("Банка 50"),
    CAN_30("Банка 30"),
    CAN_10("Банка 10");

    public final String label;

    private CanCode(String label){
        this.label = label;
    }
}
