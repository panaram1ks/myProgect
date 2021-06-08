package application.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ResultDTO {
    public WrapperForListVacuumCanDTO wrapperForListPositionDTO;
    int sheets1;
    int sheets2;
    int[] sheetsPerCodeOne;
    int[] sheetsPerCodeTwo;
    int[] blanks1;
}
