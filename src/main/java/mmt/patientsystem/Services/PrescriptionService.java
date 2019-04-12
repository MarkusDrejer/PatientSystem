package mmt.patientsystem.Services;

import mmt.patientsystem.Models.Medication;
import mmt.patientsystem.Models.Pre_Med;
import mmt.patientsystem.Models.Prescription;
import mmt.patientsystem.Repositories.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    private ResultSet resultSet;

    public Pre_Med getPrescriptions(int id) throws SQLException {
        resultSet = prescriptionRepository.getPrescriptions(id);
        Pre_Med preMed = new Pre_Med();

        while (resultSet.next()) {
            preMed.setPrescriptionList(prescriptionFiller());

            Medication medication = new Medication();
            medication.setId(resultSet.getInt("m_id"));
            medication.setName(resultSet.getString("name"));
            medication.setDescription(resultSet.getString("description"));
            medication.setSideEffects(resultSet.getString("Sideeffects"));
            preMed.setMedicationList(medication);
        }
        return preMed;
    }

    public Prescription getSinglePrescription(int id) throws SQLException {
        resultSet = prescriptionRepository.getSinglePrescription(id);

        Prescription prescription = null;
        while (resultSet.next()) {
            prescription = prescriptionFiller();
        }
        return prescription;
    }

    private Prescription prescriptionFiller() throws SQLException {
        Prescription prescription = new Prescription();
        prescription.setId(resultSet.getInt("id"));
        prescription.setPrescription(resultSet.getString("prescription"));
        prescription.setNote(resultSet.getString("note"));
        prescription.setDate(resultSet.getDate("date"));
        prescription.setPatientId(resultSet.getInt("fk_patient"));
        prescription.setDoctorId(resultSet.getInt("fk_doctor"));

        return prescription;
    }

    public void deletePrescription(int id) throws SQLException {
        prescriptionRepository.deletePrescription(id);
    }
}
