package application.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class CountingRoom {
    private WrapperForListVacuumCanDTO wrapperForListPositionDTO;
    private int quantity;

    public ResultDTO calc() {
        List<VacuumCanDTO> list = wrapperForListPositionDTO.getVacuumCanDTOList();
        String[] firstCods = new String[list.size()];//наименование первой половины изделия
        int[] firstQuantityPerSheet = new int[list.size()];//количество первых половин изделия на листе
        String[] secondCods = new String[list.size()];//наименование второй половины изделия
        int[] secondQuantityPerSheet = new int[list.size()];//количество вторых половин изделия на листе
        double[] percents = new double[list.size()];
        double[] productsPerSheet = new double[list.size()];
        double[] productsToSheetRatio = new double[list.size()];
        double[] percentageRatio = new double[list.size()];
        int[] sheetsPerSize = new int[list.size()];
        int[] sheetsPerCodeOne = new int[list.size()];
        int[] sheetsPerCodeTwo = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            firstCods[i] = list.get(i).getCodeOne();
            firstQuantityPerSheet[i] = list.get(i).getQuantityPerSheetOne();
            secondCods[i] = list.get(i).getCodeTwo();
            secondQuantityPerSheet[i] = list.get(i).getQuantityPerSheetTwo();
            percents[i] = list.get(i).getPercent();
            productsPerSheet[i] = (firstQuantityPerSheet[i] + secondQuantityPerSheet[i]) / 2;
        }
        for (int i = 0; i < list.size(); i++) {
            productsToSheetRatio[i] = productsPerSheet[0] / productsPerSheet[i];
            percentageRatio[i] = percents[i] / percents[0];
        }
        double d = Arrays.stream(productsToSheetRatio).sum();
        sheetsPerSize[0] = (int) Math.floor(this.quantity / d);
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                sheetsPerSize[i] = (int) Math.floor(sheetsPerSize[0] * productsToSheetRatio[i] * percentageRatio[i]);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            double divider = (double) secondQuantityPerSheet[i] / secondQuantityPerSheet[i]
                    + (double) secondQuantityPerSheet[i] / firstQuantityPerSheet[i];
            sheetsPerCodeOne[i] = (int) Math.floor(sheetsPerSize[i] / divider);
        }
        for (int i = 0; i < list.size(); i++) {
            sheetsPerCodeTwo[i] = sheetsPerSize[i] - sheetsPerCodeOne[i];
        }
        int[] blanks1 = new int[list.size()];//количество первой части изделия по размерам
        int[] blanks2 = new int[list.size()];//кол-во второй части изделия по размерам
        for (int i = 0; i < list.size(); i++) {
            blanks1[i] = sheetsPerCodeTwo[i] * firstQuantityPerSheet[i];
        }
        System.out.println();
        for (int i = 0; i < list.size(); i++) {
            blanks2[i] = sheetsPerCodeOne[i] * secondQuantityPerSheet[i];
        }
        Arrays.stream(blanks1).forEach(x -> System.out.print(x + " "));
        System.out.println();
        int sheets1 = Arrays.stream(sheetsPerCodeOne).sum();
        int sheets2 = Arrays.stream(sheetsPerCodeTwo).sum();

        System.out.println(sheets1);
        System.out.println(sheets2);
        Arrays.asList(sheetsPerSize).forEach(System.out::println);

        /*!!!Material material = new Material();!!!!*/
        ResultDTO resultDTO = new ResultDTO(wrapperForListPositionDTO, sheets1, sheets2, sheetsPerCodeOne, sheetsPerCodeTwo, blanks1);
        System.out.println(resultDTO);
        return resultDTO;
    }
}