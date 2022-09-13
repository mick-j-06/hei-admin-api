package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.EventParticipant;
import school.hei.haapi.model.exception.BadRequestException;
import school.hei.haapi.repository.EventParticipantRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class EventParticipantService {
    private EventParticipantRepository eventParticipantRepository;

    public List<EventParticipant> getAll(Integer page, Integer pageSize, school.hei.haapi.endpoint.rest.model.EventParticipant.StatusEnum status) {
        if (page != null && pageSize != null) {
            if (status != null) {
                return eventParticipantRepository
                        .getAllByStatus(status, PageRequest.of(page, pageSize));
            }
            return eventParticipantRepository.findAll(PageRequest.of(page, pageSize)).toList();
        }
        if (status != null) {
            return eventParticipantRepository.getAllByStatus(status);
        }
        return eventParticipantRepository.findAll();
    }

    public List<EventParticipant> getAllByEventId(Integer page, Integer pageSize, String eventId, school.hei.haapi.endpoint.rest.model.EventParticipant.StatusEnum status) {
        if (page != null && pageSize != null) {
            if (status != null) {
                return eventParticipantRepository
                        .getAllByEvent_IdAndStatus(eventId, status, PageRequest.of(page, pageSize));
            }
            return eventParticipantRepository
                    .getAllByEvent_Id(eventId, PageRequest.of(page, pageSize));
        }
        if (status != null) {
            return eventParticipantRepository
                    .getAllByEvent_IdAndStatus(eventId, status);
        }
        return eventParticipantRepository
                .getAllByEvent_Id(eventId);
    }

    public EventParticipant getByEventIdAndId(String eventId, String id) {
        return eventParticipantRepository.getByIdAndEvent_Id(id, eventId);
    }

    public EventParticipant getById(String id) {
        return eventParticipantRepository.getById(id);
    }

    @Transactional
    public List<EventParticipant> createAll(List<EventParticipant> eventParticipantList) {
        for (EventParticipant eventParticipant : eventParticipantList) {
            if (eventParticipantRepository.getByUserParticipant_IdAndEvent_Id(
                    eventParticipant.getUserParticipant().getId(),
                    eventParticipant.getEvent().getId()
            ) != null) {
                throw new BadRequestException(
                        "Event participant id:" + eventParticipant.getUserParticipant().getId() +
                                " in Event:" + eventParticipant.getEvent().getId() +
                                "existe deja");
            }
        }
        return eventParticipantRepository.saveAll(eventParticipantList);
    }

    @Transactional
    public List<EventParticipant> updateAll(String eventId, List<EventParticipant> eventParticipantList) {
        for (EventParticipant eventParticipant : eventParticipantList) {
            eventParticipantRepository.getByIdAndEvent_Id(eventParticipant.getId(), eventId);
        }
        return eventParticipantRepository.saveAll(eventParticipantList);
    }
}
