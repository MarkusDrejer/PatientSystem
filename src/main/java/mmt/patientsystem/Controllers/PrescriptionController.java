package mmt.patientsystem.Controllers;

import mmt.patientsystem.Models.Prescription;
import mmt.patientsystem.Services.MedicationService;
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
    private MedicationService medicationService;
    @Autowired
    private UserService userService;

    /**
     * displays prescription page
     * @param id
     **/
    @GetMapping("/prescription/{id}")
    public String singlePrescription(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("prescription", prescriptionService.getSinglePrescription(id));
            model.addAttribute("doctors", userService.getDoctors());
            model.addAttribute("medications", medicationService.getMedications(id, 1));
            return "PrescriptionPages/prescriptionPage";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    /**
     * displays edit prescription form
     * @param id
     **/
    @GetMapping("/prescription/editprescription/{id}")
    public String editPrescription(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("prescription", prescriptionService.getSinglePrescription(id));
            model.addAttribute("doctors", userService.getDoctors());
            return "PrescriptionPages/editprescription";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    /**
     * saves edit prescription form
     * @param id
     **/
    @PostMapping("/{id}/prescription/editprescription")
    public String editConsultation(@PathVariable(value = "id") int id,
                                   @ModelAttribute Prescription prescription, Model model) {
        try {
            prescriptionService.editPrescription(prescription);
            return "redirect:/patient/" + id;

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    /**
     * deletes chosen prescription
     * @param id
     * @param p_id
     **/
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

    /**
     * displays a patients prescription page
     * @param id
     **/
    @GetMapping("/patient/{id}/addprescription")
    public String addPrescription(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("patient_id", id);
            model.addAttribute("doctors", userService.getDoctors());
            model.addAttribute("medications", medicationService.getMedications(-1, -1));
            return "PrescriptionPages/addprescription";


        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    /**
     * saves changes in prescription page
     * @param prescription
     **/
    @PostMapping("/prescription/save")
    public String save(@ModelAttribute Prescription prescription, Model model) {
        try {
            prescriptionService.addPrescription(prescription);
            return "redirect:/patient/" + prescription.getPatientId();

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }
}