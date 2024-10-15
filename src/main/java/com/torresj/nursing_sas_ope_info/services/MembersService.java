package com.torresj.nursing_sas_ope_info.services;

import com.torresj.nursing_sas_ope_info.dtos.MemberDto;
import com.torresj.nursing_sas_ope_info.dtos.MemberResponseDto;

import java.io.IOException;
import java.util.List;

public interface MembersService {
    List<MemberResponseDto> getMembers(String filter) throws IOException;
    MemberResponseDto getMember(int id) throws IOException;
}
