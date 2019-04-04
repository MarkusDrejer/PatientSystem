package mmt.patientsystem.Controllers;

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

    @GetMapping("/patients")
    public String patientListPage(@RequestParam(value = "order", defaultValue = "1") int order,
                                  Model model) throws SQLException {
        model.addAttribute("patients", patientService.getAllPatients(order));
        return "patients";
    }
}
