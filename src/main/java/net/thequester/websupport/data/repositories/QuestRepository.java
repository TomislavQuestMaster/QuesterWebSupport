package net.thequester.websupport.data.repositories;

import net.thequester.model.Quest;
import net.thequester.websupport.model.QuestDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Tomo.
 */
public interface QuestRepository extends JpaRepository<QuestDetails, Long> {

    public QuestDetails findByName(String name);
}
