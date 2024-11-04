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
}
