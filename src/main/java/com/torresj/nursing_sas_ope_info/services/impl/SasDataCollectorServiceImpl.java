package com.torresj.nursing_sas_ope_info.services.impl;

import com.torresj.nursing_sas_ope_info.dtos.bolsa.ExclusionReasonDto;
import com.torresj.nursing_sas_ope_info.dtos.bolsa.ExclusionReasons;
import com.torresj.nursing_sas_ope_info.dtos.bolsa.NurseBolsaDto;
import com.torresj.nursing_sas_ope_info.dtos.bolsa.ScaleDto;
import com.torresj.nursing_sas_ope_info.dtos.bolsa.Status;
import com.torresj.nursing_sas_ope_info.dtos.ope.NurseOpeDto;
import com.torresj.nursing_sas_ope_info.dtos.ope.ScoreDto;
import com.torresj.nursing_sas_ope_info.services.SasDataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

@Service
@Slf4j
@AllArgsConstructor
public class SasDataCollectorServiceImpl implements SasDataService {

    private static final String SAS_OPE_URL_DEFINITIVE = "https://ws027.sspa.juntadeandalucia.es/profesionales/oep_listados/listado_sup.asp?idproceso=71106&idprocesoant=&situacion=S&pageno=%d&orden=ORD_TOTAL&t=L&convocatoria=5000&estado=listado&provisional=0";
    private static final String SAS_OPE_URL_PROVISIONAL = "https://ws027.sspa.juntadeandalucia.es/profesionales/oep_listados/listado_sup.asp?idproceso=71106&idprocesoant=&situacion=S&pageno=%d&orden=ORD_TOTAL&t=L&convocatoria=5000&estado=listado&provisional=1";
    private static final String SAS_BOLSA_URL_PROVISIONAL = "https://ws027.sspa.juntadeandalucia.es/profesionales/personaltemporal/b_listado_inscripcion.asp?c_area_esp=000&id_catesp=1006&estado=listado&provisional=1&situacion=A&pageno=%d&orden=ORDNOM&t=L&convocatoria=2023";

    private static final Map<String, String> HEADERS =
            Map.ofEntries(
                    new AbstractMap.SimpleEntry<>(
                            "Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"),
                    new AbstractMap.SimpleEntry<>("Accept-language", "es-ES,es;q=0.9"),
                    new AbstractMap.SimpleEntry<>("Cache-control", "max-age=0"),
                    new AbstractMap.SimpleEntry<>("Connection", "max-age=0"),
                    new AbstractMap.SimpleEntry<>(
                            "Sec-ch-ua",
                            "Google Chrome\";v=\"129\", \"Not=A?Brand\";v=\"8\", \"Chromium\";v=\"129\""),
                    new AbstractMap.SimpleEntry<>("ec-ch-ua-mobile", "?0"),
                    new AbstractMap.SimpleEntry<>("sec-ch-ua-platform", "\"Linux\""),
                    new AbstractMap.SimpleEntry<>("Sec-Fetch-Dest", "document"),
                    new AbstractMap.SimpleEntry<>("Sec-Fetch-Mode", "navigate"),
                    new AbstractMap.SimpleEntry<>("Sec-Fetch-Site", "same-origin"),
                    new AbstractMap.SimpleEntry<>("sec-fetch-user", "?1"),
                    new AbstractMap.SimpleEntry<>("upgrade-insecure-requests", "1"),
                    new AbstractMap.SimpleEntry<>(
                            "user-agent",
                            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36"));

    @Override
    @Cacheable("finalOpeNurses")
    public Map<Integer, NurseOpeDto> getOpeDefinitiveListNurses() throws IOException {
        log.info("Calling SAS page to get definitive OPE nurses data");
        Map<Integer, NurseOpeDto> nurses = new HashMap<>();
        for(int i=1;i<=16;i++){
            addOpeNursePage(nurses,i, SAS_OPE_URL_DEFINITIVE);
        }
        return nurses;
    }

    @Override
    @Cacheable("provisionalOpeNurses")
    public Map<Integer, NurseOpeDto> getOpeProvisionalListNurses() throws IOException {
        log.info("Calling SAS page to get provisional OPE nurses data");
        Map<Integer, NurseOpeDto> nurses = new HashMap<>();
        for(int i=1;i<=16;i++){
            addOpeNursePage(nurses,i, SAS_OPE_URL_PROVISIONAL);
        }
        return nurses;
    }

    @Override
    @Cacheable("provisionalBolsaNurses")
    public Set<NurseBolsaDto> getBolsaProvisionalListNurses() throws IOException {
        log.info("Calling SAS page to get provisional Bolsa nurses data");
        Set<NurseBolsaDto> nurses = new HashSet<>();

        IntStream.range(1, 151)
                .parallel()
                .forEach(i -> addBolsaNursePage(nurses,i, SAS_BOLSA_URL_PROVISIONAL));

        return nurses;
    }

    private void addOpeNursePage(Map<Integer, NurseOpeDto> nurses, int page, String url) throws IOException {
        var webPage = Jsoup.connect(String.format(url,page)).headers(HEADERS).get();
        var lines = webPage.select("tr");

        for(int i=1; i<lines.size(); i++){
            int position = i + ((page-1)*100);
            nurses.put(position, lineToOpeNurse(lines.get(i),position));
        }
    }

    private NurseOpeDto lineToOpeNurse(Element element, int position){
        var fields = element.select("td");
        String dni = fields.getFirst().text();
        String name = fields.get(1).text().split(",")[1];
        String surname = fields.get(1).text().split(",")[0];
        String shift = fields.get(2).text();
        float total = Float.parseFloat(fields.get(3).text().replace(",","."));
        float op = Float.parseFloat(fields.get(4).text().replace(",","."));
        float con = Float.parseFloat(fields.get(5).text().replace(",","."));
        return new NurseOpeDto(dni,name,surname,shift,new ScoreDto(total,op,con,position));
    }

    private void addBolsaNursePage(Set<NurseBolsaDto> nurses, int page, String url) {
        try {
            var webPage = Jsoup.connect(String.format(url, page)).headers(HEADERS).get();
            var lines = webPage.select("tr");

            for (int i = 2; i < lines.size() - 25; i++) {
                nurses.add(lineToBolsaNurse(lines.get(i)));
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private NurseBolsaDto lineToBolsaNurse(Element element){
        var fields = element.select("td");
        var dni = fields.getFirst().text();
        var name = fields.get(1).text().split(",")[1];
        var surname = fields.get(1).text().split(",")[0];
        var shift = fields.get(2).text();
        var treaty = fields.get(7).text();
        var status = Status.valueOf(fields.get(8).text());
        var exclusionCode = fields.get(9).text();
        var experience = fields.get(3).text();
        var formation = fields.get(4).text();
        var others = fields.get(5).text();
        var total = fields.get(6).text();
        return new NurseBolsaDto(
                dni,
                name,
                surname,
                shift,
                treaty,
                status,
                ExclusionReasons.getExclusionReason(exclusionCode),
                new ScaleDto(experience,formation,others,total)
        );
    }
}
