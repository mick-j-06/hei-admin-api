package school.hei.haapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.haapi.model.EventParticipant;

import java.util.List;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant,String> {
    void deleteAllByEvent_Id(String eventId);

    List<EventParticipant> getAllByStatus(school.hei.haapi.endpoint.rest.model.EventParticipant.StatusEnum status);
}
