package mmt.patientsystem.External;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class   CheckPrescription {

    private Random rand;

    public CheckPrescription() {
        rand = new Random();
    }

    public boolean check() {
        if(rand.nextInt(10) == 1) {
            return false;
        }
        return true;
    }
}
