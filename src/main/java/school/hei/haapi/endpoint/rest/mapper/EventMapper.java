package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.model.Event;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class EventMapper {
    private PlaceMapper placeMapper;

    public school.hei.haapi.endpoint.rest.model.Event toRest(Event event) {
        var restEvent = new school.hei.haapi.endpoint.rest.model.Event();
        restEvent.setId(event.getId());
        restEvent.setName(event.getName());
        restEvent.setRef(event.getRef());
        restEvent.setStartingHours(event.getStartingHours());
        restEvent.setEndingHours(event.getEndingHours());
        restEvent.setPlace(placeMapper.toRest(event.getPlace()));
        return restEvent;
    }

    public Event toDomain(school.hei.haapi.endpoint.rest.model.Event restEvent) {
        return Event.builder()
                .id(restEvent.getId())
                .name(restEvent.getName())
                .ref(restEvent.getRef())
                .startingHours(LocalDate.from(restEvent.getStartingHours()))
                .endingHours(LocalDate.from(restEvent.getEndingHours()))
                .place(placeMapper.toDomain(restEvent.getPlace()))
                .build();
    }
}
