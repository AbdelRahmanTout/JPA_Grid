package org.vaadin.example.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vaadin.example.backend.entity.Device;
import org.vaadin.example.backend.repository.DeviceRepository;

@Service
public class DeviceService {

	private final DeviceRepository dr;
	
	public DeviceService(DeviceRepository dr) {
		this.dr=dr;
	}

	public void save(Device device) {
		if (device == null) {
			System.err.println("Patient info's is null. Are you sure you have connected your form to the application?");
			return;
		}
		dr.save(device);
	}

	public void deleteById(int id) {
		dr.deleteById(id);
		
	}
	
	public void delete(Device d) {
		dr.delete(d);
	}
	
	public Device getById(Integer id) {
		return dr.getById(id);
	}
	
	public List<Device> createDevicess(List<Device> devices) {
		return (List<Device>) dr.saveAll(devices);
	}
	
	public List<Device> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) { 
            return dr.findAll();
        } else {
            return dr.search(stringFilter);
        }
    }
	
	public Device findById(int id) {
		return dr.findById(id).get();
	}

	public long count() {
		return dr.count();
	}
}
