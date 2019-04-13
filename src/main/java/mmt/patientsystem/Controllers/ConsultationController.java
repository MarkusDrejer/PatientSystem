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

    @GetMapping("/consultation/{id}")
    public String singleConsultation(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("consultation", consultationService.getSingleConsultation(id));
            return "ConsultationPages/consultationPage";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @GetMapping("/consultation/editconsultation/{id}")
    public String editConsultation(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("consultation", consultationService.getSingleConsultation(id));
            model.addAttribute("doctors", userService.getDoctors());
            return "ConsultationPages/editconsultation";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @PostMapping("/{id}/consultation/editconsultation")
    public String editConsultation(@PathVariable(value = "id") int id,
                                   @ModelAttribute Consultation consultation, Model model) {
        try {
            consultationService.editConsultation(consultation);
            return "redirect:/patient/" + id;

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @PostMapping("/{p_id}/consultation/deleteconsultation/{id}")
    public String deleteConsultation(@PathVariable(value = "id") int id,
                                     @PathVariable(value = "p_id") int p_id,
                                     Model model) throws SQLException {
        try {
            consultationService.deleteConsultation(id);
            return "redirect:/patient/" + p_id;

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @GetMapping("/patient/{id}/addconsultation")
    public String addConsultation(@PathVariable(value = "id") int id, Model model) {
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
    public String addConsultation(@ModelAttribute Consultation consultation, Model model) {
        try {
            consultationService.addConsultation(consultation);
            return "redirect:/patient/" + consultation.getPatientId();

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

}
