package com.torresj.nursing_sas_ope_info.services.impl;

import com.torresj.nursing_sas_ope_info.dtos.MemberDto;
import com.torresj.nursing_sas_ope_info.dtos.ScoreDto;
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
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class SasDataCollectorServiceImpl implements SasDataService {

    private static final String SAS_URL_DEFINITIVE = "https://ws027.sspa.juntadeandalucia.es/profesionales/oep_listados/listado_sup.asp?idproceso=71106&idprocesoant=&situacion=S&pageno=%d&orden=ORD_TOTAL&t=L&convocatoria=5000&estado=listado&provisional=0";
    private static final String SAS_URL_PROVISIONAL = "https://ws027.sspa.juntadeandalucia.es/profesionales/oep_listados/listado_sup.asp?idproceso=71106&idprocesoant=&situacion=S&pageno=%d&orden=ORD_TOTAL&t=L&convocatoria=5000&estado=listado&provisional=1";

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
    @Cacheable("finalMembers")
    public Map<Integer, MemberDto> getDefinitiveListMembers() throws IOException {
        log.info("Calling SAS page to get definitive members data");
        Map<Integer, MemberDto> members = new HashMap<>();
        for(int i=1;i<=16;i++){
            addMemberPage(members,i,SAS_URL_DEFINITIVE);
        }
        return members;
    }

    @Override
    @Cacheable("provisionalMembers")
    public Map<Integer, MemberDto> getProvisionalListMembers() throws IOException {
        log.info("Calling SAS page to get provisional members data");
        Map<Integer, MemberDto> members = new HashMap<>();
        for(int i=1;i<=16;i++){
            addMemberPage(members,i,SAS_URL_PROVISIONAL);
        }
        return members;
    }

    private void addMemberPage(Map<Integer, MemberDto> members, int page, String url) throws IOException {
        var webPage = Jsoup.connect(String.format(url,page)).headers(HEADERS).get();
        var lines = webPage.select("tr");

        for(int i=1; i<lines.size(); i++){
            int position = i + ((page-1)*100);
            members.put(position,lineToMember(lines.get(i),position));
        }
    }

    private MemberDto lineToMember(Element element, int position){
        var fields = element.select("td");
        String dni = fields.getFirst().text();
        String name = fields.get(1).text().split(",")[1];
        String surname = fields.get(1).text().split(",")[0];
        String shift = fields.get(2).text();
        float total = Float.parseFloat(fields.get(3).text().replace(",","."));
        float op = Float.parseFloat(fields.get(4).text().replace(",","."));
        float con = Float.parseFloat(fields.get(5).text().replace(",","."));
        return new MemberDto(dni,name,surname,shift,new ScoreDto(total,op,con,position));
    }
}
