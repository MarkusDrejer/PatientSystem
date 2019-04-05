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

    private boolean reverseTH = true;

    @GetMapping("/patients")
    public String patientListPage(@RequestParam(value = "order", defaultValue = "1") int order,
                                  @RequestParam(value = "reverse", defaultValue = "false") boolean reverse,
                                  Model model) throws SQLException {
        reverseTH = !reverseTH;
        model.addAttribute("reverse", reverseTH);
        model.addAttribute("patients", patientService.getAllPatients(order, reverse));
        return "patients";
    }
}
