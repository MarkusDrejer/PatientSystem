package mmt.patientsystem.Services;

import mmt.patientsystem.Models.Diagnosis;
import mmt.patientsystem.Repositories.DiagnosisRepository;
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

    public List<Diagnosis> getDiagnosisNames() throws SQLException {
        resultSet = diagnosisRepository.getDiagnosisNames();
        List<Diagnosis> d_names = new ArrayList<>();

        while(resultSet.next()) {
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setId(resultSet.getInt("id"));
            diagnosis.setDiagnosis(resultSet.getString("d_name"));
            d_names.add(diagnosis);
        }
        return d_names;
    }

    private Diagnosis diagnosisFiller() throws SQLException {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(resultSet.getInt("id"));
        diagnosis.setDiagnosis(resultSet.getString("d_name"));
        diagnosis.setDiagnosisNameId(resultSet.getInt("fk_diagnose_name"));
        diagnosis.setNote(resultSet.getString("note"));
        diagnosis.setDate(resultSet.getDate("date"));
        diagnosis.setPatientId(resultSet.getInt("fk_patient"));
        diagnosis.setDoctorId(resultSet.getInt("fk_doctor"));

        return diagnosis;
    }

    public void editDiagnosis(Diagnosis diagnosis) throws SQLException {
        diagnosisRepository.editDiagnosis(diagnosis);
    }

    public void deleteDiagnosis(int id) throws SQLException {
        diagnosisRepository.deleteDiagnosis(id);
    }

    public void addDiagnosis(Diagnosis diagnosis) throws SQLException {
        resultSet = diagnosisRepository.addDiagnosis(diagnosis);
        resultSet.next();
        int key = resultSet.getInt(1);
        int medId = diagnosis.getMedLink();
        diagnosisRepository.addDiagnosisJunction(key, medId);
    }
}
