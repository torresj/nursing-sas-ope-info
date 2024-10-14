package com.torresj.nursing_sas_ope_info.services;

import com.torresj.nursing_sas_ope_info.dtos.MemberDto;

import java.io.IOException;
import java.util.Map;

public interface SasDataService {
    Map<Integer, MemberDto> getDefinitiveListMembers() throws IOException;
    Map<Integer, MemberDto> getProvisionalListMembers();
}
