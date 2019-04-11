package mmt.patientsystem.Controllers;

import mmt.patientsystem.Models.Consultation;
import mmt.patientsystem.Models.Patient;
import mmt.patientsystem.Services.ConsultationService;
import mmt.patientsystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
public class ConsultationController {

    @Autowired
    ConsultationService consultationService;

    @Autowired
    private UserService userService;

    private boolean reverseTH = true;

    @GetMapping("/consultations")
    public String list(@RequestParam(value = "order", defaultValue = "1") int order,
                                  @RequestParam(value = "reverse", defaultValue = "false") boolean reverse,
                                  Model model) {
        try {
            reverseTH = !reverseTH;
            model.addAttribute("reverse", reverseTH);
            model.addAttribute("consultations", consultationService.getConsultations(order, reverse));
            return "Consultation/consultations";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @GetMapping("/consultations/add")
    public String consultations(Model model) {
        try {
            model.addAttribute("doctors", userService.getDoctors());
            return "Consultation/add";


        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @PostMapping("/consultations/save")
    public String save(@ModelAttribute Consultation consultation, Model model) {
        try {
            consultationService.addConsultation(consultation);
            return "redirect:/consultations";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

}
