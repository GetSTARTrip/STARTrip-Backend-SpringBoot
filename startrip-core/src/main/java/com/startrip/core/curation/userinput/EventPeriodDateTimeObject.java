package com.startrip.core.curation.userinput;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventPeriodDateTimeObject {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
