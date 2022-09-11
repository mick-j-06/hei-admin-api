package school.hei.haapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@Table(name = "\"event\"")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(nullable = false)
    private String name;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String ref;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDate startingHours;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDate endingHours;
    @ManyToOne
    @JoinColumn(name = "user_manager_id", nullable = false)
    private User userManager;
    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    public Instant getStartingHours() {
        ZoneId zoneId = ZoneId.systemDefault();
        return startingHours.atStartOfDay(zoneId).toInstant();
    }

    public Instant getEndingHours() {
        ZoneId zoneId = ZoneId.systemDefault();
        return endingHours.atStartOfDay(zoneId).toInstant();
    }
}
