package application.spring.controllers.engineer;

import application.spring.dto.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/engineer")
@PreAuthorize("hasRole('ROLE_ENGINEER')")
public class EngineerController {

    @PostMapping("/showCalc")
    public String showCreateForm(@RequestParam Integer num, Model model) {
        WrapperForListVacuumCanDTO wrapperForListVacuumCanDTO = new WrapperForListVacuumCanDTO();
        for (int i = 0; i < num; i++) {
            wrapperForListVacuumCanDTO.addVacuumCanDTOtoList(new VacuumCanDTO());
        }
        model.addAttribute("wrapperForListVacuumCanDTO", wrapperForListVacuumCanDTO);
        return "engineer/calcFormPayment";
    }

    @PostMapping("/showResult")
    public String showCalc(@ModelAttribute WrapperForListVacuumCanDTO wrapper, BindingResult bindingResult, @RequestParam("material") Integer material, Model model) {
//        if(bindingResult.hasErrors()){
//            return "redirect:/showCalc";
//        }
        System.out.println("Material" + "=" + material);

        List<ArrayList> calc = new ArrayList<>();
        calc.add((ArrayList) wrapper.getVacuumCanDTOList());
        CountingRoom count = new CountingRoom(wrapper, material);
        ResultDTO resultDTO = count.calc();
        model.addAttribute("resultDTO", resultDTO);
        return "engineer/showResult";
    }

    @GetMapping("/showMetalForm")
    public String showMetalForm(Model model) {
        MetalDTO metalDTO = new MetalDTO();
        model.addAttribute("metalDTO", metalDTO);
        return "engineer/metalForm";
    }

    @PostMapping("/createMetal")
    public String createMetal(@Valid @ModelAttribute("metalDTO") MetalDTO metalDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "engineer/metalForm";
        }
        System.out.println(metalDTO);
        /*
         * some code!!!
         *
         * */
        model.addAttribute("message", "ok");
        return "engineer/metalForm";
    }

}
