package com.Airplane.AirplaneApp.Service;

import com.Airplane.AirplaneApp.DTO.CrewMemberDTO;
import java.util.List;

public interface CrewMemberService {
    CrewMemberDTO createCrewMember(CrewMemberDTO crewMemberDTO);
    List<CrewMemberDTO> getAllCrewMembers();
    CrewMemberDTO getCrewMemberById(Long id);
    CrewMemberDTO updateCrewMember(Long id, CrewMemberDTO crewMemberDTO);
    void deleteCrewMember(Long id);
}