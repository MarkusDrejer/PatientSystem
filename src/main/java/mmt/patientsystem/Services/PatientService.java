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

    public List<Patient> getAllPatients(int order, boolean reverse) throws SQLException {
        resultSet = patientRepository.getAllPatients(order, reverse);
        List<Patient> patients = new ArrayList<>();

        while (resultSet.next()) {
            patients.add(fillPatientInfo());
        }
        return patients;
    }

    public Patient getSinglePatientID(int id) throws SQLException{
        resultSet = patientRepository.getSinglePatientID(id);
        Patient patient = null;

        while (resultSet.next()) {
            patient = fillPatientInfo();
        }

        return patient;
    }

    public Patient getSinglePatientSearch(Patient patient) throws SQLException {
        resultSet = patientRepository.getSinglePatientSearch(patient);

        if (resultSet.next()) {
            patient = fillPatientInfo();
        } else {
            throw new InputMismatchException();
        }

        return patient;
    }

    private Patient fillPatientInfo() throws SQLException{
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

    public void editPatient(Patient patient) throws SQLException {
        patientRepository.editPatient(patient);
    }

    public void deletePatient(int id) throws SQLException {
        patientRepository.deletePatient(id);
    }

    public void addPatient(Patient patient) throws SQLException {
        patientRepository.addPatient(patient);
    }
}
