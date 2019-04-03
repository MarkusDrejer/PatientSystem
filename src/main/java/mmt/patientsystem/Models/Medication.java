package mmt.patientsystem.Models;

public class Medication {

    private int id;
    private String name;
    private String description;


    public Medication() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean checkConflict() {
        return true;
    }
}