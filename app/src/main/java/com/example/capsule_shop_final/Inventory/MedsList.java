package com.example.capsule_shop_final.Inventory;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MedsList {
    private List<Medicine> medicines;
    private String name;

    public MedsList() {
        String uid = FirebaseAuth.getInstance().getUid();
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
