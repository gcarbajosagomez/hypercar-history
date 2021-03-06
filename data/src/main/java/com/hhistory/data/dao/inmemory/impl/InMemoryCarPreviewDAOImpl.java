package com.hhistory.data.dao.inmemory.impl;

import com.hhistory.data.dao.inmemory.InMemoryPictureDAO;
import com.hhistory.data.dao.sql.SqlPictureDAO;
import com.hhistory.data.dao.sql.SqlPictureRepository;
import com.hhistory.data.model.picture.Picture;
import com.hhistory.data.model.util.PictureUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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

    private static final int PREVIEW_LOADING_NUMBER_OF_CHUNKS = 10;

    private PictureUtil          pictureUtil;
    private SqlPictureRepository pictureRepository;
    private SqlPictureDAO        sqlPictureDAO;
    private List<Picture>        previews;

    @Inject
    public InMemoryCarPreviewDAOImpl(PictureUtil pictureUtil,
                                     SqlPictureRepository pictureRepository,
                                     SqlPictureDAO sqlPictureDAO) {
        this.pictureUtil = pictureUtil;
        this.pictureRepository = pictureRepository;
        this.sqlPictureDAO = sqlPictureDAO;
    }

    //@Scheduled(fixedDelayString = "${data.entities.inMemoryLoadDelay}")
    @Override
    public void loadEntitiesFromDB() {
        log.info("Loading Car preview entities in memory");

        Long previewCount = this.pictureRepository.count();

        int chunkSize = (int) (previewCount.doubleValue() / PREVIEW_LOADING_NUMBER_OF_CHUNKS);

        previews = new ArrayList<>();
        IntStream.rangeClosed(0, PREVIEW_LOADING_NUMBER_OF_CHUNKS)
                 .forEach(i -> {
                     int skipPosition = chunkSize * i;
                     this.previews.addAll(this.sqlPictureDAO.getPaginated(skipPosition,
                                                                          chunkSize));
                 });
    }

    @Override
    public void loadEntityFromDB(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeEntity(Long id) {
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
    public List<Long> getAllIds() {
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
                                                  .toList();

        return this.pictureUtil.getPreviewPictureFromCandidates(previewCandidates);
    }
}
