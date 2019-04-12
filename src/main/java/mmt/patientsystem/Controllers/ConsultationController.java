package mmt.patientsystem.Controllers;

import mmt.patientsystem.Models.Consultation;
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

    @GetMapping("/patient/{id}/addconsultation")
    public String consultations(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("patient_id", id);
            model.addAttribute("doctors", userService.getDoctors());
            return "ConsultationPages/addconsultation";


        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @PostMapping("/consultations/save")
    public String save(@ModelAttribute Consultation consultation, Model model) {
        try {
            consultationService.addConsultation(consultation);
            return "redirect:/patient/" + consultation.getPatientId();

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

}
