package school.hei.haapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "\"event_participant\"")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private school.hei.haapi.endpoint.rest.model.EventParticipant.StatusEnum status;
    @ManyToOne
    @JoinColumn(name = "user_participant_id", nullable = false)
    private User userParticipant;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
