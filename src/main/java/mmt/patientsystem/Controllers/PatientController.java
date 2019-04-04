package mmt.patientsystem.Controllers;

import mmt.patientsystem.Services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

@Controller
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping("/patients")
    public String patientListPage(Model model) throws SQLException {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }
}
