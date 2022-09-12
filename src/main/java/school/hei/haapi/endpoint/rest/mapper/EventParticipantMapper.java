package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.CreateEventParticipant;
import school.hei.haapi.model.EventParticipant;
import school.hei.haapi.repository.EventRepository;
import school.hei.haapi.repository.UserRepository;

import static school.hei.haapi.endpoint.rest.model.EventParticipant.StatusEnum.EXPECTED;

@Component
@AllArgsConstructor
public class EventParticipantMapper {
    private EventRepository eventRepository;
    private UserRepository userRepository;

    public school.hei.haapi.endpoint.rest.model.EventParticipant toRest(EventParticipant eventParticipant) {
        var restEventParticipant = new school.hei.haapi.endpoint.rest.model.EventParticipant();
        restEventParticipant.setId(eventParticipant.getId());
        restEventParticipant.setStatus(eventParticipant.getStatus());
        restEventParticipant.setUserParticipantId(eventParticipant.getUserParticipant().getId());
        restEventParticipant.setEventId(eventParticipant.getEvent().getId());
        return restEventParticipant;
    }

    public EventParticipant toDomain(school.hei.haapi.endpoint.rest.model.EventParticipant eventParticipant) {
        return EventParticipant.builder()
                .id(eventParticipant.getId())
                .status(eventParticipant.getStatus())
                .userParticipant(userRepository.getById(eventParticipant.getUserParticipantId()))
                .event(eventRepository.getById(eventParticipant.getEventId()))
                .build();
    }

    public school.hei.haapi.endpoint.rest.model.EventParticipant toRestCreateEventParticipant(String eventId, CreateEventParticipant createEventParticipant) {
        var eventParticipant = new school.hei.haapi.endpoint.rest.model.EventParticipant();
        eventParticipant.setStatus(EXPECTED);
        eventParticipant.setUserParticipantId(createEventParticipant.getUserParticipantId());
        eventParticipant.setEventId(eventId);
        return eventParticipant;
    }
}
