package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import school.hei.haapi.endpoint.rest.model.CreateEventParticipant;
import school.hei.haapi.model.EventParticipant;
import school.hei.haapi.model.exception.BadRequestException;
import school.hei.haapi.repository.EventParticipantRepository;
import school.hei.haapi.endpoint.rest.model.EventParticipant.StatusEnum;
import school.hei.haapi.repository.EventRepository;
import school.hei.haapi.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static school.hei.haapi.endpoint.rest.model.EventParticipant.StatusEnum.*;

@Service
@AllArgsConstructor
public class EventParticipantService {
    private EventParticipantRepository eventParticipantRepository;
    private EventRepository eventRepository;
    private UserRepository userRepository;

    public List<EventParticipant> getAll(Integer page, Integer pageSize, String status) {
        if (page != null && pageSize != null) {
            if (status != null) {
                return eventParticipantRepository
                        .getAllByStatus(this.statusEnum(status), PageRequest.of(page, pageSize));
            }
            return eventParticipantRepository.findAll(PageRequest.of(page, pageSize)).toList();
        }
        if (status != null) {
            return eventParticipantRepository.getAllByStatus(this.statusEnum(status));
        }
        return eventParticipantRepository.findAll();
    }

    public List<EventParticipant> getAllByEventId(Integer page, Integer pageSize, String eventId, String status) {
        if (page != null && pageSize != null) {
            if (status != null) {
                return eventParticipantRepository
                        .getAllByEvent_IdAndStatus(eventId, this.statusEnum(status), PageRequest.of(page, pageSize));
            }
            return eventParticipantRepository
                    .getAllByEvent_Id(eventId, PageRequest.of(page, pageSize));
        }
        if (status != null) {
            return eventParticipantRepository
                    .getAllByEvent_IdAndStatus(eventId, this.statusEnum(status));
        }
        return eventParticipantRepository
                .getAllByEvent_Id(eventId);
    }

    public EventParticipant getById(String id) {
        return eventParticipantRepository.getById(id);
    }

    @Transactional
    public List<EventParticipant> createAll(String eventId, List<CreateEventParticipant> createEventParticipants) {
        eventRepository.getById(eventId);
        return eventParticipantRepository.saveAll(
                this.buildByCreateEventParticipants(eventId, createEventParticipants)
        );
    }

    public List<EventParticipant> updateAll(String eventId, List<EventParticipant> eventParticipantList) {
        for (EventParticipant eventParticipant : eventParticipantList) {
            eventParticipantRepository.getByIdAndEvent_Id(eventParticipant.getId(), eventId);
        }
        return eventParticipantRepository.saveAll(eventParticipantList);
    }

    public StatusEnum statusEnum(String status) {
        switch (status) {
            case "EXPECTED":
                return EXPECTED;
            case "HERE":
                return HERE;
            case "MISSING":
                return MISSING;
            default:
                throw new BadRequestException("status :" + status + " not found");
        }
    }

    public EventParticipant buildByCreateEventParticipant(String eventId, CreateEventParticipant createEventParticipant) {
        EventParticipant eventParticipant = new EventParticipant();
        eventParticipant.setId(createEventParticipant.getId());
        eventParticipant.setUserParticipant(userRepository.getById(Objects.requireNonNull(createEventParticipant.getUserParticipantId())));
        eventParticipant.setEvent(eventRepository.getById(eventId));
        return eventParticipant;
    }

    public List<EventParticipant> buildByCreateEventParticipants(String eventId, List<CreateEventParticipant> createEventParticipants) {
        List<EventParticipant> eventParticipantList = new ArrayList<>();
        for (CreateEventParticipant createEventParticipant : createEventParticipants) {
            eventParticipantList
                    .add(this.buildByCreateEventParticipant(eventId, createEventParticipant));
        }
        return eventParticipantList;
    }
}
