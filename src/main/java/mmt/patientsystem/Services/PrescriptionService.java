package mmt.patientsystem.Services;

import mmt.patientsystem.Models.Prescription;
import mmt.patientsystem.Models.Patient;
import mmt.patientsystem.Repositories.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionService {
    @Autowired
    PrescriptionRepository prescriptionRepository;

    private ResultSet resultSet;

    public List<Prescription> getPrescriptions() throws SQLException {
        resultSet = prescriptionRepository.getPrescriptions();
        List<Prescription> prescriptions = new ArrayList<>();

        while (resultSet.next()) {
            prescriptions.add(prescriptionFiller());
        }
        return prescriptions;
    }

    private Prescription prescriptionFiller() throws SQLException {
        Prescription prescription = new Prescription();
        prescription.setId(resultSet.getInt("id"));
        prescription.setNote(resultSet.getString("note"));
        prescription.setMedicationName(resultSet.getString("prescription"));
        prescription.setDate(resultSet.getDate("date"));
        prescription.setPatientName(resultSet.getString("fk_patient"));
        prescription.setDoctorName(resultSet.getString("fk_doctor"));


        return prescription;

    }

    public void addPrescription(Prescription prescription) throws SQLException {
        prescriptionRepository.addPrescription(prescription);
    }
}
