package application.spring.entity;


import application.spring.entity.enums.CoverCode;
import lombok.Data;

@Data
public class Cover {

    private CoverCode coverCode;
    private final int quantityPerSheet;

    private int firstOperationCounter;
    private int secondOperationCounter;
    private int thirdOperationCounter;
    private int fourthOperationCounter;
}
