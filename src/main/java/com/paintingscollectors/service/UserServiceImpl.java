package com.paintingscollectors.service;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.PaintingInfoDto;
import com.paintingscollectors.model.dto.UserLoginDto;
import com.paintingscollectors.model.dto.UserRegisterDto;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.PaintingRepository;
import com.paintingscollectors.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;
    private final PaintingRepository paintingRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession, PaintingRepository paintingRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
        this.paintingRepository = paintingRepository;
    }

    public boolean register(UserRegisterDto userRegisterDto) {
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(userRegisterDto.getUsername(), userRegisterDto.getEmail());
        if (optionalUser.isPresent()) {
            return false;
        }

        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        userRepository.save(user);

        return true;
    }

    public boolean login(UserLoginDto userLoginDto) {
        if (userSession.isLoggedIn()) {
            return false;
        }

        Optional<User> optionalUser = userRepository.findByUsername(userLoginDto.getUsername());

        if (optionalUser.isEmpty()) {
            return false;
        }

        if (!passwordEncoder.matches(userLoginDto.getPassword(), optionalUser.get().getPassword())) {
            return false;
        }

        User user = optionalUser.get();
        userSession.login(user.getId(), user.getUsername());
        return true;

    }

    @Transactional
    public Set<PaintingInfoDto> getCurrentUserPaintings() {
        return userRepository.findByUsername(userSession.getUsername()).orElse(null).getPaintings()
                .stream().map(PaintingInfoDto::new).collect(Collectors.toSet());
    }

    @Transactional
    public Set<PaintingInfoDto> getFavoritePaintings() {
        return userRepository.findById(userSession.getId()).get().getFavoritePaintings()
                .stream().map(PaintingInfoDto::new).collect(Collectors.toSet());
    }

    @Transactional
    public void removeFavorite(long id) {
        User user = userRepository.findById(userSession.getId()).get();
        Painting painting = paintingRepository.findById(id).get();
        user.getFavoritePaintings().remove(painting);
        Set<User> allByFavoritePaintingsId = userRepository.findAllByFavoritePaintingsId(id);
        if (allByFavoritePaintingsId.isEmpty()) {
            painting.setFavorite(false);
        }
        paintingRepository.save(painting);
        userRepository.save(user);
    }
}
