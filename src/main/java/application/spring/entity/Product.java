package application.spring.entity;

import application.spring.entity.enums.ProductCode;
import lombok.Data;


@Data
public class Product {

    private ProductCode productCode;

    private Can can;

    private Cover cover;
}
