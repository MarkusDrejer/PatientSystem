package mmt.patientsystem.Controllers;

import mmt.patientsystem.Models.Prescription;
import mmt.patientsystem.Models.Patient;
import mmt.patientsystem.Services.PrescriptionService;
import mmt.patientsystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
public class PrescriptionController {

    @Autowired
    PrescriptionService prescriptionService;

    @Autowired
    private UserService userService;

    @GetMapping("/prescription/{id}")
    public String singlePrescription(@PathVariable(value = "id") int id, Model model) throws SQLException {

            model.addAttribute("prescription", prescriptionService.getSinglePrescription(id));
            return "PrescriptionPages/prescriptionPage";

    }

    @PostMapping("/{p_id}/prescription/deleteprescription/{id}")
    public String deletePrescription(@PathVariable(value = "p_id") int p_id,
                                     @PathVariable(value = "id") int id, Model model) {
        try {
            prescriptionService.deletePrescription(id);
            return "redirect:/patient/" + p_id;

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    /*@GetMapping("/prescription/addprescription")
    public String prescriptions(Model model) {
        try {
            model.addAttribute("doctors", userService.getDoctors());
            return "Prescription/addprescription";


        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @PostMapping("/Prescription/save")
    public String save(@ModelAttribute Prescription prescription, Model model) {
        try {
            prescriptionService.addPrescription(prescription);
            return "redirect:/prescriptions";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }
*/
}
