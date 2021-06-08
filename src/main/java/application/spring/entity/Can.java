package application.spring.entity;

import application.spring.entity.enums.CanCode;
import lombok.Data;

@Data
public class Can {

    private CanCode canCode;
    private final int quantityPerSheet;

    private int firstOperationCounter;
    private int secondOperationCounter;
    private int thirdOperationCounter;

}
