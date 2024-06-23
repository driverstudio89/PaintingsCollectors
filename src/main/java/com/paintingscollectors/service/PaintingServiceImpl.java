package com.paintingscollectors.service;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.AddPaintingDto;
import com.paintingscollectors.model.dto.PaintingInfoDto;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.PaintingRepository;
import com.paintingscollectors.repository.StyleRepository;
import com.paintingscollectors.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PaintingServiceImpl {

    private final StyleRepository styleRepository;
    private final UserSession userSession;
    private final UserRepository userRepository;
    private final PaintingRepository paintingRepository;

    public PaintingServiceImpl(StyleRepository styleRepository, UserSession userSession, UserRepository userRepository, PaintingRepository paintingRepository) {
        this.styleRepository = styleRepository;
        this.userSession = userSession;
        this.userRepository = userRepository;
        this.paintingRepository = paintingRepository;
    }

    public boolean addNewPainting(AddPaintingDto addPaintingDto) {
        if (!userSession.isLoggedIn()) {
            return false;
        }

        Painting painting = new Painting();
        Style style = styleRepository.findByName(addPaintingDto.getStyleName());
        User user = userRepository.findById(userSession.getId()).orElse(null);

        painting.setName(addPaintingDto.getName());
        painting.setAuthor(addPaintingDto.getAuthor());
        painting.setStyle(style);
        painting.setOwner(user);
        painting.setImageUrl(addPaintingDto.getImageUrl());
        painting.setFavorite(false);
        painting.setVotes(0);

        paintingRepository.save(painting);

        return true;
    }

    @Transactional
    public boolean removePainting(long id) {
        if (!userSession.isLoggedIn()) {
            return false;
        }
        Painting painting = paintingRepository.findById(id).get();

        if (painting.isFavorite()) {
            return false;
        }

        Set<User> allByRatedPaintingsId = userRepository.findAllByRatedPaintingsId(id);
        for (User user : allByRatedPaintingsId) {
            user.getRatedPaintings().remove(painting);
            userRepository.save(user);

        }
        paintingRepository.delete(painting);

        return true;
    }

    @Transactional
    public Set<PaintingInfoDto> getAllOtherPaintings() {
        User user = userRepository.findById(userSession.getId()).get();
        Set<PaintingInfoDto> allByOwnerNot = paintingRepository.findAllByOwnerNot(user)
                .stream().map(PaintingInfoDto::new).collect(Collectors.toSet());
        return allByOwnerNot;
    }

    @Transactional
    public void addFavorite(long id) {
        User user = userRepository.findById(userSession.getId()).get();
        Painting painting = paintingRepository.findById(id).get();
        user.getFavoritePaintings().add(painting);
        painting.setFavorite(true);
        paintingRepository.save(painting);
        userRepository.save(user);
    }

    @Transactional
    public boolean voteForPainting(long id) {
        User user = userRepository.findById(userSession.getId()).get();
        Painting painting = paintingRepository.findById(id).get();
        if (painting.getOwner().getId() == user.getId()) {
            return false;
        }
        if (user.getRatedPaintings().contains(painting)) {
            return false;
        }
        painting.setVotes(painting.getVotes() + 1);
        user.getRatedPaintings().add(painting);
        userRepository.save(user);
        paintingRepository.save(painting);

        return true;
    }

    public LinkedHashSet<PaintingInfoDto> getMostVotedPaintings() {
        return paintingRepository.findTop2ByOrderByVotesDescNameAsc()
                .stream().map(PaintingInfoDto::new).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
