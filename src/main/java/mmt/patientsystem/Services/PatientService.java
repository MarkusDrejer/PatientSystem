package mmt.patientsystem.Services;

import mmt.patientsystem.Models.Patient;
import mmt.patientsystem.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    private ResultSet resultSet;

    /**
     * puts results from database into list
     * @param order
     * @param reverse
     **/
    public List<Patient> getAllPatients(int order, boolean reverse) throws SQLException {
        resultSet = patientRepository.getAllPatients(order, reverse);
        List<Patient> patients = new ArrayList<>();

        while (resultSet.next()) {
            patients.add(patientFiller());
        }
        return patients;
    }

    /**
     * returns single patient id
     * @param id
     **/
    public Patient getSinglePatientID(int id) throws SQLException{
        resultSet = patientRepository.getSinglePatientID(id);

        Patient patient = null;
        while (resultSet.next()) {
            patient = patientFiller();
        }
        return patient;
    }

    /**
     * returns single patient from search
     * @param patient
     **/
    public int getSinglePatientSearch(Patient patient) throws SQLException {
        resultSet = patientRepository.getSinglePatientSearch(patient);
        int id;

        if (resultSet.next()) {
            id = resultSet.getInt("id");
        } else {
            throw new InputMismatchException();
        }
        return id;
    }

    /**
     * fills patient
     **/
    private Patient patientFiller() throws SQLException{
        Patient patient = new Patient();
        patient.setId(resultSet.getInt("id"));
        patient.setName(resultSet.getString("name"));
        patient.setAge(resultSet.getInt("age"));
        patient.setBirthDate(resultSet.getDate("birthdate"));
        patient.setCPR(resultSet.getInt("cpr"));
        patient.setHeight(resultSet.getInt("height"));
        patient.setWeight(resultSet.getDouble("weight"));
        patient.setGender(resultSet.getString("gender"));
        patient.setPersonalDescription(resultSet.getString("description"));

        return patient;
    }

    /**
     * edits patient
     * @param patient
     **/
    public void editPatient(Patient patient) throws SQLException {
        patientRepository.editPatient(patient);
    }

    /**
     * delete patient
     * @param id
     **/
    public void deletePatient(int id) throws SQLException {
        patientRepository.deletePatient(id);
    }

    /**
     * add patient
     * @param patient
     **/
    public void addPatient(Patient patient) throws SQLException {
        patientRepository.addPatient(patient);
    }
}
