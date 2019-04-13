package mmt.patientsystem.Controllers;

import mmt.patientsystem.Models.Medication;
import mmt.patientsystem.Services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

@Controller
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @GetMapping("/{p_id}/addmedicine/{id}/{type}")
    public String addMedicine(@PathVariable(value = "p_id") int p_id,
                              @PathVariable(value = "id") int id,
                              @PathVariable(value = "type") int type, Model model) {
        try {
            model.addAttribute("patientId", p_id);
            model.addAttribute("couplingId", id);
            model.addAttribute("type", type);
            model.addAttribute("medications", medicationService.getMedications(-1, -1));
            return "addmedicine";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @PostMapping("/{p_id}/addmedicine/{type}")
    public String addMedicine(@PathVariable(value = "p_id") int p_id,
                              @PathVariable(value = "type") int type,
                              @ModelAttribute Medication medication, Model model) {
        try {
            medicationService.addMedicine(medication, type);
            return "redirect:/patient/" + p_id;

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }
}
