package application.spring.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WrapperForListVacuumCanDTO {

    private List<VacuumCanDTO> vacuumCanDTOList;

    public void addVacuumCanDTOtoList(VacuumCanDTO vacuumCanDTO){
        if (vacuumCanDTOList == null){
            vacuumCanDTOList = new ArrayList<VacuumCanDTO>();
        }
        this.vacuumCanDTOList.add(vacuumCanDTO);
    }
}
