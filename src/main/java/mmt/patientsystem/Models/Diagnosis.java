package mmt.patientsystem.Models;

import java.util.Date;

public class Diagnosis {

    private int id;
    private String diagnosis;
    private int diagnosisNameId;
    private String note;
    private Date date;
    private String medications;
    private int patientId;
    private int doctorId;
    private int medLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public int getDiagnosisNameId() {
        return diagnosisNameId;
    }

    public void setDiagnosisNameId(int diagnosisNameId) {
        this.diagnosisNameId = diagnosisNameId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getMedLink() {
        return medLink;
    }

    public void setMedLink(int medLink) {
        this.medLink = medLink;
    }
}
