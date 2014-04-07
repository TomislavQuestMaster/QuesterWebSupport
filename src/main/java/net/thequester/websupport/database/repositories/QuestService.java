package net.thequester.websupport.database.repositories;

import net.thequester.model.Quest;
import net.thequester.websupport.model.QuestDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Tomo.
 */
@Service
public class QuestService{

    @Resource
    private QuestRepository repository;

    @Transactional
    public QuestDetails create(QuestDetails quest) {

        return repository.save(quest);

    }


}
