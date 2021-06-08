package application.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacuumCan {
//    @NotNull
//    @Size(min=2, max=15)
    String codeOne;
//    @NotNull
//    @Min(1)
//    @Max(1000)
    Integer quantityPerSheetOne;
//    @NotNull
//    @Size(min=2, max=15)
    String codeTwo;
//    @NotNull
//    @Min(1)
//    @Max(1000)
    Integer quantityPerSheetTwo;
//    @NotNull
//    @Min(1)
//    @Max(100)
    Double percent;
}
