package org.vaadin.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vaadin.example.backend.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
	@Query("select c from Device c " + 
			"where lower(c.serialNumber) like lower(concat('%', :searchTerm, '%'))"
			+ "or lower(c.deviceName) like lower(concat('%', :searchTerm, '%'))"
			+ "or lower(c.assosId) like lower(concat('%', :searchTerm, '%'))")
	List<Device> search(@Param("searchTerm") String searchTerm);
}
