package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.endpoint.rest.mapper.EventParticipantMapper;
import school.hei.haapi.endpoint.rest.model.CreateEventParticipant;
import school.hei.haapi.endpoint.rest.model.EventParticipant;
import school.hei.haapi.service.EventParticipantService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class EventParticipantController {
    private EventParticipantService eventParticipantService;
    private EventParticipantMapper eventParticipantMapper;

    @GetMapping(value = "/events/{event_id}/event_participants/{event_participant_id}")
    public EventParticipant getEventEventParticipantById(
            @PathVariable String event_id,
            @PathVariable String event_participant_id
    ) {
        return eventParticipantMapper.toRest(eventParticipantService.getByEventIdAndId(event_id, event_participant_id));
    }

    @PostMapping(value = "/events/{event_id}/event_participants")
    public List<EventParticipant> createEventEventParticipants(
            @PathVariable String event_id,
            @RequestBody List<CreateEventParticipant> createEventParticipantList
    ) {
        List<EventParticipant> restEventParticipantList = createEventParticipantList.stream().map(
                        (createEventParticipant) -> eventParticipantMapper.toRestCreateEventParticipant(event_id, createEventParticipant))
                .collect(Collectors.toUnmodifiableList());
        return eventParticipantService
                .createAll(
                        restEventParticipantList.stream().map(eventParticipantMapper::toDomain)
                                .collect(Collectors.toUnmodifiableList())
                ).stream().map(eventParticipantMapper::toRest)
                .collect(Collectors.toUnmodifiableList());
    }
}
