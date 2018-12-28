package io.github.jakubsuszynski.aggregator.service;

import io.github.jakubsuszynski.aggregator.domain.Tag;
import io.github.jakubsuszynski.aggregator.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TagsServiceImpl implements TagsService {
    @Autowired
    TagsRepository tagsRepository;

    @Override
    public Optional<Tag> getById(long id) {
        return tagsRepository.findById(id);
    }

    @Override
    public List<Tag> getAll() {
        return (List<Tag>) tagsRepository.findAll();
    }

    @Override
    public void saveTag(Tag tag) {
        tagsRepository.save(tag);
    }

    @Override
    public void saveAll(Set<Tag> tags) {

        for(Tag tag : tags){
            if(!isPresent(tag)){
                saveTag(tag);
            }
        }
    }
    private Boolean isPresent(Tag tag){
        Optional<Tag> optionalTag = Optional.ofNullable(tagsRepository.findByTag(tag.getTag()));
        return optionalTag.isPresent();
    }
}
