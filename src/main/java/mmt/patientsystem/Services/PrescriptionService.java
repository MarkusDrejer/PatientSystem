package mmt.patientsystem.Services;

import mmt.patientsystem.Models.Medication;
import mmt.patientsystem.Models.Prescription;
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
    private PrescriptionRepository prescriptionRepository;

    private ResultSet resultSet;

    public List<Prescription> getPrescriptions(int id) throws SQLException {
        resultSet = prescriptionRepository.getPrescriptions(id);
        List<Prescription> prescriptionList = new ArrayList<>();

        while(resultSet.next()) {
            Prescription prescription = prescriptionFiller();
            prescription.setMedications(resultSet.getString("medications_given"));
            prescriptionList.add(prescription);
        }

        return prescriptionList;
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
