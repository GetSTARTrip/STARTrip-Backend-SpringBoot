package com.startrip.core.entity.event_trip;

import com.startrip.core.entity.event_trip.dto.UpdateEventTripDto;
import com.startrip.core.entity.user.User;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EventTrip {
    @Id
    @Column(name = "trip_id")
    private UUID tripId;

    @OneToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User userId;

    @Column(name = "user_partner")
    private String userPartner;

    @Column(name = "event_id")
    private UUID eventId;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    private Integer state;

    private String transportation;

    private String title;

    public void update(UpdateEventTripDto dto) {
        this.userPartner = dto.getUserPartner();
        this.eventId = dto.getEventId();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.state = dto.getState();
        this.transportation = dto.getTransportation();
        this.title = dto.getTitle();
    }
}
