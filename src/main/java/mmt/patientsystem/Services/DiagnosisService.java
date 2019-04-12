package mmt.patientsystem.Services;

import mmt.patientsystem.Models.Diagnosis;
import mmt.patientsystem.Models.Prescription;
import mmt.patientsystem.Repositories.DiagnosisRepository;
import mmt.patientsystem.Repositories.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    private ResultSet resultSet;

    public List<Diagnosis> getDiagnosis(int id) throws SQLException {
        resultSet = diagnosisRepository.getDiagnosis(id);
        List<Diagnosis> diagnosiss = new ArrayList<>();

        while (resultSet.next()) {
            Diagnosis diagnosis = diagnosisFiller();
            diagnosis.setMedications(resultSet.getString("medications_given"));
            diagnosiss.add(diagnosis);
        }
        return diagnosiss;
    }

    public Diagnosis getSingleDiagnosis(int id) throws SQLException {
        resultSet = diagnosisRepository.getSingleDiagnosis(id);

        Diagnosis diagnosis = null;
        while (resultSet.next()) {
            diagnosis = diagnosisFiller();
        }
        return diagnosis;
    }

    private Diagnosis diagnosisFiller() throws SQLException {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(resultSet.getInt("id"));
        diagnosis.setDiagnosis(resultSet.getString("d_name"));
        diagnosis.setNote(resultSet.getString("note"));
        diagnosis.setDate(resultSet.getDate("date"));
        diagnosis.setPatientId(resultSet.getInt("fk_patient"));
        diagnosis.setDoctorId(resultSet.getInt("fk_doctor"));

        return diagnosis;
    }

    public void deleteDiagnosis(int id) throws SQLException {
        diagnosisRepository.deleteDiagnosis(id);
    }
}
