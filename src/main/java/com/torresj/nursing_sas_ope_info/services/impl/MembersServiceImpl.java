package com.torresj.nursing_sas_ope_info.services.impl;

import com.torresj.nursing_sas_ope_info.dtos.MemberDto;
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
    public List<MemberDto> getMembers(String filter) throws IOException {
        var filteredMembers = sasDataService.getDefinitiveListMembers()
                .values()
                .stream()
                .filter(member -> member.surname().toLowerCase().contains(filter.toLowerCase())).toList();
        return new ArrayList<>(filteredMembers);
    }

    @Override
    public MemberDto getMember(int id) throws IOException {
        return sasDataService.getDefinitiveListMembers().get(id);
    }
}
