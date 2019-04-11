package mmt.patientsystem.Controllers;

import mmt.patientsystem.Models.Patient;
import mmt.patientsystem.Services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.InputMismatchException;

@Controller
public class PatientController {

    @Autowired
    PatientService patientService;

    private boolean reverseTH = true;

    @GetMapping("/patients")
    public String patientListPage(@RequestParam(value = "order", defaultValue = "1") int order,
                                  @RequestParam(value = "reverse", defaultValue = "false") boolean reverse,
                                  Model model) {
        try {
            reverseTH = !reverseTH;
            model.addAttribute("reverse", reverseTH);
            model.addAttribute("patients", patientService.getAllPatients(order, reverse));
            return "PatientPages/patients";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @GetMapping("/patient/{id}")
    public String singlePatientPage(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("patient", patientService.getSinglePatientID(id));
            return "PatientPages/patientPage";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @PostMapping("/patient/search")
    public String singlePatientSearch(@ModelAttribute Patient patient, Model model) {
        try {
            model.addAttribute("patient", patientService.getSinglePatientSearch(patient));
            return "PatientPages/patientPage";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        } catch (InputMismatchException e) {
            model.addAttribute("invalid", true);
            return "fragments/sidenav";
        }
    }

    @GetMapping("/patients/editpatient/{id}")
    public String editPatient(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("patient", patientService.getSinglePatientID(id));
            return "PatientPages/editpatient";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @PostMapping("/patients/editpatient")
    public String editPatient(@ModelAttribute Patient patient, Model model) {
        try {
            patientService.editPatient(patient);
            return "redirect:/patients";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @PostMapping("/patients/deletepatient/{id}")
    public String deletePatient(@PathVariable(value = "id") int id, Model model) {
        try {
            patientService.deletePatient(id);
            return "redirect:/patients";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    @GetMapping("/patients/addpatient")
    public String addPatient() {
        return "PatientPages/addpatient";
    }

    @PostMapping("/patients/addpatient")
    public String addPatient(@ModelAttribute Patient patient, Model model) {
        try {
            patientService.addPatient(patient);
            return "redirect:/patients";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }
}
