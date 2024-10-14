package com.torresj.nursing_sas_ope_info.services;

import com.torresj.nursing_sas_ope_info.dtos.MemberDto;

import java.io.IOException;
import java.util.List;

public interface MembersService {
    List<MemberDto> getMembers(String filter) throws IOException;
    MemberDto getMember(int id) throws IOException;
}
