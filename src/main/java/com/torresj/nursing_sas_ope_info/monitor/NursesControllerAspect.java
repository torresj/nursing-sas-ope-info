package com.torresj.nursing_sas_ope_info.monitor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class NursesControllerAspect {

    private final MeterRegistry meterRegistry;

    @Before(
            value = "execution(* com.torresj.nursing_sas_ope_info.services.NursesService.getOpeNurses(..))"
    )
    public void updateOpeEndpointCounter(){
        Counter counter = Counter.builder("ope_calls")
                .description("a number of requests to /ope endpoint")
                .register(meterRegistry);
        counter.increment();
    }

    @Before(
            value = "execution(* com.torresj.nursing_sas_ope_info.services.NursesService.getBolsaNurses(..))"
    )
    public void updateBolsaEndpointCounter(){
        Counter counter = Counter.builder("bolsa_calls")
                .description("a number of requests to /bolsa endpoint")
                .register(meterRegistry);
        counter.increment();
    }

    @Before(
            value = "execution(* com.torresj.nursing_sas_ope_info.services.NursesService.getBolsaCriticsNurses(..))"
    )
    public void updateBolsaCriticsEndpointCounter(){
        Counter counter = Counter.builder("critics_calls")
                .description("a number of requests to /critics endpoint")
                .register(meterRegistry);
        counter.increment();
    }

    @Before(
            value = "execution(* com.torresj.nursing_sas_ope_info.services.NursesService.getBolsaDialysisNurses(..))"
    )
    public void updateBolsaDialysisEndpointCounter(){
        Counter counter = Counter.builder("dialysis_calls")
                .description("a number of requests to /dialysis endpoint")
                .register(meterRegistry);
        counter.increment();
    }

    @Before(
            value = "execution(* com.torresj.nursing_sas_ope_info.services.NursesService.getBolsaFamilyNurses(..))"
    )
    public void updateBolsaFamilyEndpointCounter(){
        Counter counter = Counter.builder("family_calls")
                .description("a number of requests to /family endpoint")
                .register(meterRegistry);
        counter.increment();
    }

    @Before(
            value = "execution(* com.torresj.nursing_sas_ope_info.services.NursesService.getBolsaGyneNurses(..))"
    )
    public void updateBolsaGyneEndpointCounter(){
        Counter counter = Counter.builder("gyne_calls")
                .description("a number of requests to /gyne endpoint")
                .register(meterRegistry);
        counter.increment();
    }

    @Before(
            value = "execution(* com.torresj.nursing_sas_ope_info.services.NursesService.getBolsaMentalHealthNurses(..))"
    )
    public void updateBolsaMentalEndpointCounter(){
        Counter counter = Counter.builder("mental_calls")
                .description("a number of requests to /mental endpoint")
                .register(meterRegistry);
        counter.increment();
    }

    @Before(
            value = "execution(* com.torresj.nursing_sas_ope_info.services.NursesService.getBolsaNeonatesNurses(..))"
    )
    public void updateBolsaNeonatesEndpointCounter(){
        Counter counter = Counter.builder("neonates_calls")
                .description("a number of requests to /neonates endpoint")
                .register(meterRegistry);
        counter.increment();
    }

    @Before(
            value = "execution(* com.torresj.nursing_sas_ope_info.services.NursesService.getBolsaNuclearNurses(..))"
    )
    public void updateBolsaNuclearEndpointCounter(){
        Counter counter = Counter.builder("nuclear_calls")
                .description("a number of requests to /nuclear endpoint")
                .register(meterRegistry);
        counter.increment();
    }

    @Before(
            value = "execution(* com.torresj.nursing_sas_ope_info.services.NursesService.getBolsaPediatricianNurses(..))"
    )
    public void updateBolsaPediatricianEndpointCounter(){
        Counter counter = Counter.builder("pediatrician_calls")
                .description("a number of requests to /pediatrician endpoint")
                .register(meterRegistry);
        counter.increment();
    }

    @Before(
            value = "execution(* com.torresj.nursing_sas_ope_info.services.NursesService.getBolsaSpecificMentalHealthNurses(..))"
    )
    public void updateBolsaMentalSpecificEndpointCounter(){
        Counter counter = Counter.builder("mental_specific_calls")
                .description("a number of requests to /mental/specific endpoint")
                .register(meterRegistry);
        counter.increment();
    }

    @Before(
            value = "execution(* com.torresj.nursing_sas_ope_info.services.NursesService.getBolsaSurgeryRoomNurses(..))"
    )
    public void updateBolsaSurgeryRoomEndpointCounter(){
        Counter counter = Counter.builder("surgery_calls")
                .description("a number of requests to /surgery endpoint")
                .register(meterRegistry);
        counter.increment();
    }

    @Before(
            value = "execution(* com.torresj.nursing_sas_ope_info.services.NursesService.getBolsaWorkNurses(..))"
    )
    public void updateBolsaWorkEndpointCounter(){
        Counter counter = Counter.builder("work_calls")
                .description("a number of requests to /work endpoint")
                .register(meterRegistry);
        counter.increment();
    }
}
