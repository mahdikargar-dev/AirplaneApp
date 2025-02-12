package com.Airplane.AirplaneApp.Service.impl;

import com.Airplane.AirplaneApp.DTO.CrewMemberDTO;
import com.Airplane.AirplaneApp.Entity.CrewMember;
import com.Airplane.AirplaneApp.Mapper.CrewMemberMapper;
import com.Airplane.AirplaneApp.Repository.CrewMemberRepository;
import com.Airplane.AirplaneApp.Service.CrewMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrewMemberServiceImpl implements CrewMemberService {

    @Autowired
    private CrewMemberRepository crewMemberRepository;

    @Autowired
    private CrewMemberMapper crewMemberMapper;

    @Override
    public CrewMemberDTO createCrewMember(CrewMemberDTO crewMemberDTO) {
        // Validate input
        validateCrewMemberDTO(crewMemberDTO);

        // Check if employee ID already exists
        if (crewMemberRepository.existsByEmployeeId(crewMemberDTO.getEmployeeId())) {
            throw new IllegalArgumentException("Crew member with employee ID " + crewMemberDTO.getEmployeeId() + " already exists");
        }

        // Convert DTO to Entity and save
        CrewMember crewMember = crewMemberMapper.toEntity(crewMemberDTO);
        CrewMember savedCrewMember = crewMemberRepository.save(crewMember);

        // Convert saved Entity back to DTO
        return crewMemberMapper.toDTO(savedCrewMember);
    }

    @Override
    public List<CrewMemberDTO> getAllCrewMembers() {
        return crewMemberRepository.findAll().stream()
                .map(crewMemberMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CrewMemberDTO getCrewMemberById(Long id) {
        CrewMember crewMember = crewMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crew member not found with id: " + id));
        return crewMemberMapper.toDTO(crewMember);
    }

    @Override
    public CrewMemberDTO updateCrewMember(Long id, CrewMemberDTO crewMemberDTO) {
        // Validate input
        validateCrewMemberDTO(crewMemberDTO);

        // Find existing CrewMember
        CrewMember existingCrewMember = crewMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crew member not found with id: " + id));

        // Update existing CrewMember with new values
        existingCrewMember.setFirstName(crewMemberDTO.getFirstName());
        existingCrewMember.setLastName(crewMemberDTO.getLastName());
        existingCrewMember.setEmployeeId(crewMemberDTO.getEmployeeId());
        existingCrewMember.setDateOfBirth(crewMemberDTO.getDateOfBirth());
        existingCrewMember.setNationality(crewMemberDTO.getNationality());
        existingCrewMember.setPassportNumber(crewMemberDTO.getPassportNumber());
        existingCrewMember.setPassportExpiryDate(crewMemberDTO.getPassportExpiryDate());
        existingCrewMember.setStatus(crewMemberDTO.getStatus());
        existingCrewMember.setMedicalCertificateNumber(crewMemberDTO.getMedicalCertificateNumber());
        existingCrewMember.setMedicalCertificateExpiry(crewMemberDTO.getMedicalCertificateExpiry());

        // Update CrewType
        existingCrewMember.setCrewType(crewMemberMapper.mapCrewType(crewMemberDTO.getCrewTypeId()));

        // Save and return updated CrewMember as DTO
        CrewMember updatedCrewMember = crewMemberRepository.save(existingCrewMember);
        return crewMemberMapper.toDTO(updatedCrewMember);
    }

    @Override
    public void deleteCrewMember(Long id) {
        // Check if CrewMember exists
        CrewMember existingCrewMember = crewMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crew member not found with id: " + id));

        // Delete CrewMember
        crewMemberRepository.delete(existingCrewMember);
    }

    // Validation method
    private void validateCrewMemberDTO(CrewMemberDTO crewMemberDTO) {
        if (crewMemberDTO == null) {
            throw new IllegalArgumentException("Crew member cannot be null");
        }

        // Validate required fields
        validateRequiredField(crewMemberDTO.getFirstName(), "First Name");
        validateRequiredField(crewMemberDTO.getLastName(), "Last Name");
        validateRequiredField(crewMemberDTO.getEmployeeId(), "Employee ID");
        validateRequiredField(crewMemberDTO.getNationality(), "Nationality");
        validateRequiredField(crewMemberDTO.getPassportNumber(), "Passport Number");
        validateRequiredField(crewMemberDTO.getStatus(), "Status");

        // Validate dates
        validateDate(crewMemberDTO.getDateOfBirth(), "Date of Birth");
        validateDate(crewMemberDTO.getPassportExpiryDate(), "Passport Expiry Date");

        // Validate passport expiry is in the future
        if (crewMemberDTO.getPassportExpiryDate() != null &&
                crewMemberDTO.getPassportExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Passport expiry date must be in the future");
        }

        // Validate medical certificate expiry if provided
        if (crewMemberDTO.getMedicalCertificateExpiry() != null &&
                crewMemberDTO.getMedicalCertificateExpiry().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Medical certificate expiry date must be in the future");
        }

        // Validate crew type
        if (crewMemberDTO.getCrewTypeId() == null) {
            throw new IllegalArgumentException("Crew Type ID is required");
        }
    }

    private void validateRequiredField(String field, String fieldName) {
        if (field == null || field.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
    }

    private void validateDate(LocalDate date, String fieldName) {
        if (date == null) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
    }
}