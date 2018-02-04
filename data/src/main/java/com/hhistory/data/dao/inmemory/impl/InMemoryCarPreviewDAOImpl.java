package com.hhistory.data.dao.inmemory.impl;

import com.hhistory.data.dao.inmemory.InMemoryPictureDAO;
import com.hhistory.data.dao.sql.SqlPictureRepository;
import com.hhistory.data.model.picture.Picture;
import com.hhistory.data.model.util.PictureUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hhistory.data.dao.inmemory.impl.InMemoryCarPreviewDAOImpl.IN_MEMORY_CAR_PREVIEW_DAO;

/**
 * Created by Gonzalo Carbajosa on 4/02/18.
 */
@Component(value = IN_MEMORY_CAR_PREVIEW_DAO)
@EnableScheduling
@NoArgsConstructor
@Slf4j
public class InMemoryCarPreviewDAOImpl implements InMemoryPictureDAO {

    public static final String IN_MEMORY_CAR_PREVIEW_DAO = "inMemoryCarPreviewDAO";

    private PictureUtil          pictureUtil;
    private SqlPictureRepository pictureRepository;
    private List<Picture>        previews;

    @Inject
    public InMemoryCarPreviewDAOImpl(PictureUtil pictureUtil,
                                     SqlPictureRepository pictureRepository) {
        this.pictureUtil = pictureUtil;
        this.pictureRepository = pictureRepository;
    }

    @Scheduled(initialDelayString = "${data.pictures.inMemoryLoadDelay}", fixedDelay = LOAD_ENTITIES_DELAY)
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading Car preview entities in memory");
        previews = pictureRepository.getAllPreviews();
    }

    @Override
    public void loadEntityFromDB(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeEntity(Long id){
        throw new UnsupportedOperationException();
}

    @Override
    public Picture getById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Picture> getEntities() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Long> getIdsByCarId(Long carId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Picture> getByCarId(Long carId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List getAllIds() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Long> getAllPreviewIds(Long manufacturerId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Picture> getCarPreview(Long carId) {
        List<Picture> previewCandidates = previews.stream()
                                                  .filter(preview -> preview.getCar().getId().equals(carId))
                                                  .collect(Collectors.toList());

        return this.pictureUtil.getPreviewPictureFromCandidates(previewCandidates);
    }
}
