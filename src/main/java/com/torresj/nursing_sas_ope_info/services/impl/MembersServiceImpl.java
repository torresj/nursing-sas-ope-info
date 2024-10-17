package com.torresj.nursing_sas_ope_info.services.impl;

import com.torresj.nursing_sas_ope_info.dtos.MemberDto;
import com.torresj.nursing_sas_ope_info.dtos.MemberResponseDto;
import com.torresj.nursing_sas_ope_info.services.MembersService;
import com.torresj.nursing_sas_ope_info.services.SasDataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class MembersServiceImpl implements MembersService {

    private final SasDataService sasDataService;

    @Override
    public List<MemberResponseDto> getMembers(String filter) throws IOException {
        var filteredMembers = sasDataService.getDefinitiveListMembers()
                .values()
                .stream()
                .filter(member -> member.surname().toLowerCase().contains(filter.toLowerCase()))
                .map(memberDto -> membersToResponseDto(findMemberInProvisionalList(memberDto), memberDto))
                .limit(100)
                .toList();

        return new ArrayList<>(filteredMembers);
    }

    @Override
    public MemberResponseDto getMember(int id) throws IOException {
        var finalMember = sasDataService.getDefinitiveListMembers().get(id);
        return membersToResponseDto(
                findMemberInProvisionalList(finalMember),
                finalMember
        );
    }

    private MemberDto findMemberInProvisionalList(MemberDto member) {
        try {
            return sasDataService.getProvisionalListMembers()
                    .values()
                    .stream()
                    .filter(memberDto ->
                            memberDto.surname().equalsIgnoreCase(member.surname()) &&
                                    memberDto.dni().equals(member.dni()))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private MemberResponseDto membersToResponseDto(MemberDto memberProvisional, MemberDto finalMember) {
        return new MemberResponseDto(
                finalMember.dni(),
                finalMember.name(),
                finalMember.surname(),
                finalMember.shift(),
                memberProvisional != null? memberProvisional.score() : null,
                finalMember.score()
        );
    }
}
