package com.example.supernurse.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.supernurse.models.Invoice;
import com.example.supernurse.models.Patient;

public class PatientViewModel extends ViewModel {

    private MutableLiveData<Patient> mPatient = new MutableLiveData<>();

    public LiveData<Patient> getPatient() {
        return mPatient;
    }

    public void setPatient(Patient patient) {
        mPatient.setValue(patient);
    }

    private void loadInvoices() {

    }

    public void addInvoice(Invoice inv) {
        mPatient.getValue().addInvoice(inv);
        mPatient.postValue(mPatient.getValue());
    }

}