package com.torresj.nursing_sas_ope_info.controllers;

import com.torresj.nursing_sas_ope_info.dtos.bolsa.NurseBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.ope.NurseOpeResponseDto;
import com.torresj.nursing_sas_ope_info.services.NursesService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/nurses")
@Slf4j
@RequiredArgsConstructor
public class NursesController {

    private final NursesService nursesService;

    @Operation(summary = "Get OPE nurses by filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = NurseOpeResponseDto.class)))
                            }),
            })
    @GetMapping("/ope")
    public ResponseEntity<List<NurseOpeResponseDto>> getOpeNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) throws IOException {
        log.info("Getting OPE nurses by filter {}", filter);
        var nurses = nursesService.getOpeNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }

    @Operation(summary = "Get OPE nurse by id")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = NurseOpeResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            })
    @GetMapping("/ope/{id}")
    public ResponseEntity<NurseOpeResponseDto> getOpeNurse(@Parameter(description = "OPE Nurse id") @PathVariable int id) throws IOException {
        log.info("Getting nurse {}", id);
        var member = nursesService.getOpeNurse(id);
        log.info("Nurse {} found: {}", id, member);
        return ResponseEntity.ok(member);
    }

    @Operation(summary = "Get Bolsa nurses by filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = NurseBolsaDto.class)))
                            }),
            })
    @GetMapping("/bolsa")
    public ResponseEntity<Set<NurseBolsaDto>> getBolsaNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) throws IOException {
        log.info("Getting Bolsa nurses by filter {}", filter);
        var nurses = nursesService.getBolsaNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }
}
