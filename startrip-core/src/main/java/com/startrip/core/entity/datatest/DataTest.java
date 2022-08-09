package com.startrip.core.entity.datatest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DataTest {
    @Id
    @Column(name = "test_id")
    private UUID testId;

    @Column(name = "place_name")
    private String placeName; // json 파일의 title 부분을 넣기
}
