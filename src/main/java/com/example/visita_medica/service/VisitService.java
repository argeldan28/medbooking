package com.example.visita_medica.service;

import com.example.visita_medica.dto.VisitDto;
import com.example.visita_medica.entity.User;
import com.example.visita_medica.entity.Visit;
import com.example.visita_medica.repository.UserRepository;
import com.example.visita_medica.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final UserRepository userRepository;

    public List<VisitDto> getAllVisits() {
        return visitRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Optional<VisitDto> getVisitById(Long id) {
        return visitRepository.findById(id)
                .map(this::mapToDto);
    }

    public VisitDto createVisit(VisitDto visitDto) {
        Visit visit = new Visit();

        visit.setType(visitDto.getType());
        visit.setDate(visitDto.getDate());
        visit.setTime(visitDto.getTime());

        User patient = userRepository.findById(visitDto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + visitDto.getPatientId()));

        User doctor = userRepository.findById(visitDto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + visitDto.getDoctorId()));

        visit.setPatient(patient);
        visit.setDoctor(doctor);

        Visit savedVisit = visitRepository.save(visit);
        return mapToDto(savedVisit);
    }

    public void deleteVisit(Long id) {
        if (!visitRepository.existsById(id)) {
            throw new IllegalArgumentException("Visit not found with id: " + id);
        }
        visitRepository.deleteById(id);
    }

    private VisitDto mapToDto(Visit visit) {
        return VisitDto.builder()
                .id(visit.getId())
                .type(visit.getType())
                .date(visit.getDate())
                .time(visit.getTime())
                .patientId(visit.getPatient() != null ? visit.getPatient().getId() : null)
                .doctorId(visit.getDoctor() != null ? visit.getDoctor().getId() : null)
                .build();
    }
}
