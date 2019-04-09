package mmt.patientsystem.Controllers;

import mmt.patientsystem.Models.Diagnosis;
import mmt.patientsystem.Services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

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
            return "errorPage";
        }
    }

    @GetMapping("/patient/{id}")
    public String singlePatientPage(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("patient", patientService.getSinglePatient(id));
            return "PatientPages/patientPage";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "errorPage";
        }
    }
}
