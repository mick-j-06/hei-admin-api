package school.hei.haapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.haapi.model.EventParticipant;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant,String> {
}
