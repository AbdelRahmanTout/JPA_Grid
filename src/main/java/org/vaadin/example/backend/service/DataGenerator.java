package org.vaadin.example.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;
import org.vaadin.example.backend.entity.Device;
import org.vaadin.example.backend.entity.Patient;
import org.vaadin.example.backend.repository.DeviceRepository;
import org.vaadin.example.backend.repository.PatientRepository;

import com.vaadin.flow.spring.annotation.SpringComponent;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PatientRepository patientRepository,DeviceRepository deviceRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (patientRepository.count() != 0L && deviceRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");
            logger.info("... generating 20 Contact entities...");
            
            ExampleDataGenerator<Device> deviceGenerator = new ExampleDataGenerator<>(Device.class,LocalDateTime.now());
			deviceGenerator.setData(Device::setSerialNumber, DataType.ADDRESS);
			deviceGenerator.setData(Device::setDeviceName, DataType.FULL_NAME);
			deviceGenerator.setData(Device::setAssosId, DataType.IBAN);
			deviceGenerator.setData(Device::setDate, DataType.DATE_OF_BIRTH);
			List<Device> lst = deviceGenerator.create(20, seed/2).stream().collect(Collectors.toList());
			
            
            ExampleDataGenerator<Patient> patientGenerator = new ExampleDataGenerator<>(Patient.class, LocalDateTime.now());
            patientGenerator.setData(Patient::setFirstName, DataType.FIRST_NAME);
            patientGenerator.setData(Patient::setLastName, DataType.LAST_NAME);
            patientGenerator.setData(Patient::setPhoneNumber, DataType.NUMBER_UP_TO_10000);
            patientGenerator.setData(Patient::setEmail, DataType.EMAIL);
            patientGenerator.setData(Patient::setDate, DataType.DATE_OF_BIRTH);
            
            List <Patient>ls = patientGenerator.create(20, seed/2).stream().collect(Collectors.toList());
            deviceRepository.saveAll(lst);
            patientRepository.saveAll(ls);
            
            logger.info("Generated demo data");
        };
    }
}