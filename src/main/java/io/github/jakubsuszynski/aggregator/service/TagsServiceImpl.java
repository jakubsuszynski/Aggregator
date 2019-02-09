package io.github.jakubsuszynski.aggregator.service;

import io.github.jakubsuszynski.aggregator.domain.Tag;
import io.github.jakubsuszynski.aggregator.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {
    @Autowired
    TagsRepository tagsRepository;


    @Override
    public List<Tag> getAllTags() {
        return tagsRepository.findAll();
    }

    @Override
    public void saveAll(List<Tag> tags) {
        tagsRepository.saveAll(tags);
    }
}
