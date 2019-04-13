package mmt.patientsystem.Controllers;

import mmt.patientsystem.Models.Diagnosis;
import mmt.patientsystem.Services.DiagnosisService;
import mmt.patientsystem.Services.MedicationService;
import mmt.patientsystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;
    @Autowired
    private MedicationService medicationService;
    @Autowired
    private UserService userService;

    @GetMapping("/diagnosis/{id}")
    public String SingleDiagnosis(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("diagnosis", diagnosisService.getSingleDiagnosis(id));
            model.addAttribute("medications", medicationService.getMedications(id, 2));
            return "DiagnosisPages/diagnosisPage";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @GetMapping("/diagnosis/editdiagnosis/{id}")
    public String editDiagnosis(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("diagnosis", diagnosisService.getSingleDiagnosis(id));
            model.addAttribute("d_names", diagnosisService.getDiagnosisNames());
            model.addAttribute("doctors", userService.getDoctors());
            return "DiagnosisPages/editdiagnosis";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @PostMapping("/{id}/diagnosis/editdiagnosis")
    public String editDiagnosis(@PathVariable(value = "id") int id,
                                   @ModelAttribute Diagnosis diagnosis, Model model) {
        try {
            diagnosisService.editDiagnosis(diagnosis);
            return "redirect:/patient/" + id;

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @PostMapping("/{p_id}/diagnosis/deletediagnosis/{id}")
    public String deletePrescription(@PathVariable(value = "p_id") int p_id,
                                     @PathVariable(value = "id") int id, Model model) {
        try {
            diagnosisService.deleteDiagnosis(id);
            return "redirect:/patient/" + p_id;

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @GetMapping("/patient/{id}/adddiagnosis")
    public String prescriptions(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("patient_id", id);
            model.addAttribute("doctors", userService.getDoctors());
            model.addAttribute("d_names", diagnosisService.getDiagnosisNames());
            model.addAttribute("medications", medicationService.getMedications(-1, -1));
            return "DiagnosisPages/adddiagnosis";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @PostMapping("/diagnosis/save")
    public String save(@ModelAttribute Diagnosis diagnosis, Model model){
        try {
            diagnosisService.addDiagnosis(diagnosis);
            return "redirect:/patient/" + diagnosis.getPatientId();

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @GetMapping("/{id}/adddiagnosisname")
    public String addDiagnosisName(@PathVariable(value = "id") int id, Model model) {
        model.addAttribute("patientId", id);
        return "DiagnosisPages/adddiagnosisname";
    }

    @PostMapping("/{id}/adddiagnosisname")
    public String addDiagnosisName(@PathVariable(value = "id") int id,
                                    @RequestParam String name, Model model) {
        try {
            diagnosisService.addDiagnosisName(name);
            return "redirect:/patient/" + id;

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }
}
