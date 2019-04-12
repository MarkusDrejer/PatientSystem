package mmt.patientsystem.Services;

import mmt.patientsystem.Models.Medication;
import mmt.patientsystem.Repositories.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    private ResultSet resultSet;

    public List<Medication> getMedications(int id, int type) throws SQLException {
        resultSet = medicationRepository.getMedications(id, type);
        List<Medication> medicationList = new ArrayList<>();

        while(resultSet.next()) {
            Medication medication = new Medication();
            medication.setId(resultSet.getInt("m_id"));
            medication.setName(resultSet.getString("name"));
            medication.setDescription(resultSet.getString("description"));
            medication.setSideEffects(resultSet.getString("Sideeffects"));
            medicationList.add(medication);
        }
        return medicationList;
    }
}
