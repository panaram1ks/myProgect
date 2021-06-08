package application.spring.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class MetalDTO {
    @NotNull
    @Size(min = 5, max = 10)
    private String name;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalTime;
    @NotNull
    @Min(1)
    @Max(Integer.MAX_VALUE)
    private Integer amount;


}
