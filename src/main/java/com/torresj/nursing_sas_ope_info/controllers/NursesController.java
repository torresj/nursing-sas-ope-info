package com.torresj.nursing_sas_ope_info.controllers;

import com.torresj.nursing_sas_ope_info.dtos.SasBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.NurseAreaBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.SasOpeResponseDto;
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
                                            array = @ArraySchema(schema = @Schema(implementation = SasOpeResponseDto.class)))
                            }),
            })
    @GetMapping("/ope")
    public ResponseEntity<List<SasOpeResponseDto>> getOpeNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) throws IOException {
        log.info("Getting OPE nurses by filter {}", filter);
        var nurses = nursesService.getOpeNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
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
                                            array = @ArraySchema(schema = @Schema(implementation = SasBolsaDto.class)))
                            }),
            })
    @GetMapping("/bolsa")
    public ResponseEntity<Set<SasBolsaDto>> getBolsaNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) throws IOException {
        log.info("Getting Bolsa nurses by filter {}", filter);
        var nurses = nursesService.getBolsaNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }

    @Operation(summary = "Get Bolsa ciritcs nurses by filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = NurseAreaBolsaDto.class)))
                            }),
            })
    @GetMapping("/bolsa/critics")
    public ResponseEntity<Set<NurseAreaBolsaDto>> getBolsaCriticsNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) throws IOException {
        log.info("Getting Bolsa critics nurses by filter {}", filter);
        var nurses = nursesService.getBolsaCriticsNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }

    @Operation(summary = "Get Bolsa dialysis nurses by filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = NurseAreaBolsaDto.class)))
                            }),
            })
    @GetMapping("/bolsa/dialysis")
    public ResponseEntity<Set<NurseAreaBolsaDto>> getBolsaDialysisNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) {
        log.info("Getting Bolsa dialysis nurses by filter {}", filter);
        var nurses = nursesService.getBolsaDialysisNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }

    @Operation(summary = "Get Bolsa family nurses by filter")
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
    @GetMapping("/bolsa/family")
    public ResponseEntity<Set<SasBolsaDto>> getBolsaFamilyNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) {
        log.info("Getting Bolsa family nurses by filter {}", filter);
        var nurses = nursesService.getBolsaFamilyNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }

    @Operation(summary = "Get Bolsa gynecology nurses by filter")
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
    @GetMapping("/bolsa/gyne")
    public ResponseEntity<Set<SasBolsaDto>> getBolsaGyneNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) {
        log.info("Getting Bolsa gyne nurses by filter {}", filter);
        var nurses = nursesService.getBolsaGyneNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }

    @Operation(summary = "Get Bolsa mental health nurses by filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = NurseAreaBolsaDto.class)))
                            }),
            })
    @GetMapping("/bolsa/mental")
    public ResponseEntity<Set<NurseAreaBolsaDto>> getBolsaMentalNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) {
        log.info("Getting Bolsa mental health nurses by filter {}", filter);
        var nurses = nursesService.getBolsaMentalHealthNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }

    @Operation(summary = "Get Bolsa neonates nurses by filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = NurseAreaBolsaDto.class)))
                            }),
            })
    @GetMapping("/bolsa/neonates")
    public ResponseEntity<Set<NurseAreaBolsaDto>> getBolsaNeonatesNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) {
        log.info("Getting Bolsa neonates nurses by filter {}", filter);
        var nurses = nursesService.getBolsaNeonatesNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }

    @Operation(summary = "Get Bolsa nuclear medicine nurses by filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = NurseAreaBolsaDto.class)))
                            }),
            })
    @GetMapping("/bolsa/nuclear")
    public ResponseEntity<Set<NurseAreaBolsaDto>> getBolsaNuclearNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) {
        log.info("Getting Bolsa nuclear nurses by filter {}", filter);
        var nurses = nursesService.getBolsaNuclearNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }

    @Operation(summary = "Get Bolsa pediatrician nurses by filter")
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
    @GetMapping("/bolsa/pediatrician")
    public ResponseEntity<Set<SasBolsaDto>> getBolsaPediatricianNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) {
        log.info("Getting Bolsa pediatrician nurses by filter {}", filter);
        var nurses = nursesService.getBolsaPediatricianNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }

    @Operation(summary = "Get Bolsa specific mental health nurses by filter")
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
    @GetMapping("/bolsa/mental/specific")
    public ResponseEntity<Set<SasBolsaDto>> getBolsaSpecificMentalNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) {
        log.info("Getting Bolsa specific mental health nurses by filter {}", filter);
        var nurses = nursesService.getBolsaSpecificMentalHealthNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }

    @Operation(summary = "Get Bolsa surgery room nurses by filter")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = NurseAreaBolsaDto.class)))
                            }),
            })
    @GetMapping("/bolsa/surgery")
    public ResponseEntity<Set<NurseAreaBolsaDto>> getBolsaSurgeryNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) {
        log.info("Getting Bolsa surgery room nurses by filter {}", filter);
        var nurses = nursesService.getBolsaSurgeryRoomNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }

    @Operation(summary = "Get Bolsa work nurses by filter")
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
    @GetMapping("/bolsa/work")
    public ResponseEntity<Set<SasBolsaDto>> getBolsaWorkNurses(
            @Parameter(description = "Filter by surname") @RequestParam String filter
    ) {
        log.info("Getting Bolsa work nurses by filter {}", filter);
        var nurses = nursesService.getBolsaWorkNurses(filter);
        log.info("Nurses found: {}", nurses.size());
        return ResponseEntity.ok(nurses);
    }

}

