package com.startrip.core.util.datatest;

import com.startrip.core.entity.datatest.DataTest;
import com.startrip.core.entity.datatest.DataTestRepository;
import com.startrip.core.util.datatest.dto.DataTestDto;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AreaBased {
    private final DataTestRepository dataTestRepository;
    public String get() throws FileNotFoundException {

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/com/startrip/codebase/store/지역기반 관광정보조회.json"));

            DataTestDto dto = new DataTestDto();
            String str;
            while ((str = fileReader.readLine()) != null) {
                if (str.contains("title")) {
                    String s = str.trim().substring(9).split("\"")[1];
                    System.out.println(s);
                    dataTestRepository.save(new DataTest(UUID.randomUUID(), s));
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "ok";
    }
}
