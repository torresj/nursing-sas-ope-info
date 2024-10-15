package com.torresj.nursing_sas_ope_info.controllers;

import com.torresj.nursing_sas_ope_info.dtos.MemberDto;
import com.torresj.nursing_sas_ope_info.dtos.MemberResponseDto;
import com.torresj.nursing_sas_ope_info.services.MembersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/members")
@Slf4j
@RequiredArgsConstructor
public class MembersController {

    private final MembersService membersService;

    @Operation(summary = "Get members by filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = MemberResponseDto.class)))
                            }),
            })
    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getMembers(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) throws IOException {
        log.info("Getting members by filter {}", filter);
        var members = membersService.getMembers(filter);
        log.info("Members found: " + members.size());
        return ResponseEntity.ok(members);
    }

    @Operation(summary = "Get member by id")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = MemberResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            })
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getChannel(@Parameter(description = "Member id") @PathVariable int id) throws IOException {
        log.info("Getting member {}", id);
        var member = membersService.getMember(id);
        log.info("Member {} found: {}",id, member);
        return ResponseEntity.ok(member);
    }
}
