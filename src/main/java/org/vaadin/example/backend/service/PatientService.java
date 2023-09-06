package org.vaadin.example.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.vaadin.example.backend.entity.Patient;
import org.vaadin.example.backend.repository.PatientRepository;


@Service
public class PatientService {

	private final PatientRepository pr;
	
	private PatientService(PatientRepository pr) {
		this.pr=pr;
	}

	public void save(Patient patient) {
		if (patient == null) {
			System.err.println("Patient info's is null. Are you sure you have connected your form to the application?");
			return;
		}
		pr.save(patient);
	}

	public void deleteById(int id) {
		pr.deleteById(id);
		
	}
	
	public void delete(Patient p) {
		pr.delete(p);
	}
	
	public Patient getById(Integer id) {
		return pr.getById(id);
	}
	
	public List<Patient> createPatients(List<Patient> patients) {
		return (List<Patient>) pr.saveAll(patients);
	}
	
	public List<Patient> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) { 
            return pr.findAll();
        } else {
            return pr.search(stringFilter);
        }
    }
	
	public Patient findById(int id) {
		return pr.findById(id).get();
	}

	public long count() {
		return pr.count();
	}
	
//	public List<Patient> findAll() {
//	List<Patient> ls = new ArrayList<>();
//	Iterable <Patient> it = pr.findAll();
//	for(Patient p : it) {
//		ls.add(p);
//	}
//	return ls;
//}
}