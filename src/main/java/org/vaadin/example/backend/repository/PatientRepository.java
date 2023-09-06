package org.vaadin.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vaadin.example.backend.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer>{
	 @Query("select c from Patient c " +
		      "where lower(c.firstName) like lower(concat('%', :searchTerm, '%'))"
			 +"or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))"
		      +"or lower(c.email) like lower(concat('%', :searchTerm, '%'))"
		      +"or lower(c.phoneNumber) like lower(concat('%', :searchTerm, '%'))")
	List<Patient> search(@Param("searchTerm") String searchTerm);
}
