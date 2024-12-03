package com.torresj.nursing_sas_ope_info.controllers;

import com.torresj.nursing_sas_ope_info.dtos.SasBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.SasOpeResponseDto;
import com.torresj.nursing_sas_ope_info.services.TcaesService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/tcaes")
@Slf4j
@RequiredArgsConstructor
public class TcaesController {

    private final TcaesService tcaesService;

    @Operation(summary = "Get OPE tcaes by filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = SasOpeResponseDto.class)))
                            }),
            })
    @GetMapping("/ope")
    public ResponseEntity<List<SasOpeResponseDto>> getOpeTcaes(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) throws IOException {
        log.info("Getting OPE tcaes by filter {}", filter);
        var tcaes = tcaesService.getOpeTcaes(filter);
        log.info("Tcaes found: {}", tcaes.size());
        return ResponseEntity.ok(tcaes);
    }

    @Operation(summary = "Get Bolsa tcaes by filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = SasBolsaDto.class)))
                            }),
            })
    @GetMapping("/bolsa")
    public ResponseEntity<Set<SasBolsaDto>> getBolsaTcaes(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) throws IOException {
        log.info("Getting Bolsa tcaes by filter {}", filter);
        var tcaes = tcaesService.getBolsaTcaes(filter);
        log.info("Tcaes found: {}", tcaes.size());
        return ResponseEntity.ok(tcaes);
    }

}

