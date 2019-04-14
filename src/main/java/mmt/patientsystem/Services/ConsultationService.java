package mmt.patientsystem.Services;

import mmt.patientsystem.Models.Consultation;
import mmt.patientsystem.Models.Patient;
import mmt.patientsystem.Repositories.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultationService {
    @Autowired
    ConsultationRepository consultationRepository;

    private ResultSet resultSet;

    /**
     * puts results from database into list
     * @param id
     **/
    public List<Consultation> getConsultations(int id) throws SQLException {
        resultSet = consultationRepository.getConsultations(id);
        List<Consultation> consultations = new ArrayList<>();

        while (resultSet.next()) {
            consultations.add(consultationFiller());
        }
        return consultations;
    }

    /**
     * returns single consultation
     * @param id
     **/
    public Consultation getSingleConsultation(int id) throws SQLException {
        resultSet = consultationRepository.getSingleConsultation(id);

        Consultation consultation = null;
        while (resultSet.next()) {
            consultation = consultationFiller();
        }
        return consultation;
    }

    /**
     * fills consultations
     **/
    private Consultation consultationFiller() throws SQLException {
        Consultation consultation = new Consultation();
        consultation.setId(resultSet.getInt("id"));
        consultation.setDescription(resultSet.getString("description"));
        consultation.setConclusion(resultSet.getString("conclusion"));
        consultation.setDate(resultSet.getDate("date"));
        consultation.setFromTime(resultSet.getString("from_time"));
        consultation.setToTime(resultSet.getString("to_time"));
        consultation.setPatientId(resultSet.getInt("fk_patient"));
        consultation.setDoctorId(resultSet.getInt("fk_doctor"));

        return consultation;

    }

    /**
     * edits consultation
     * @param consultation
     **/
    public void editConsultation(Consultation consultation) throws SQLException {
        consultationRepository.editConsultation(consultation);
    }

    /**
     * delete consultation
     * @param id
     **/
    public void deleteConsultation(int id) throws SQLException {
        consultationRepository.deleteConsultation(id);
    }

    /**
     * add consultation
     * @param consultation
     **/
    public void addConsultation(Consultation consultation) throws SQLException {
        consultationRepository.addConsultation(consultation);
    }
}
