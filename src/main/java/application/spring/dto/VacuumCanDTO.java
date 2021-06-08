package application.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacuumCanDTO {
    @NotNull
    @Size(min=2, max=15)
    String codeOne;
    @NotNull
    @Min(1)
    @Max(1000)
    Integer quantityPerSheetOne;
    @NotNull
    @Size(min=2, max=15)
    String codeTwo;
    @NotNull
    @Min(1)
    @Max(1000)
    Integer quantityPerSheetTwo;
    @NotNull
    @Min(1)
    @Max(100)
    Double percent;
}
