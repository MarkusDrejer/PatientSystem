package mmt.patientsystem.Models;

import java.util.ArrayList;
import java.util.List;

public class Pre_Med {

    private List<Prescription> prescriptionList;
    private List<Medication> medicationList;

    public Pre_Med() {
        prescriptionList = new ArrayList<>();
        medicationList = new ArrayList<>();
    }

    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(Prescription prescription) {
        this.prescriptionList.add(prescription);
    }

    public List<Medication> getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(Medication medication) {
        this.medicationList.add(medication);
    }
}
