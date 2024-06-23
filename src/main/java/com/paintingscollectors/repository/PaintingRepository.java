package com.paintingscollectors.repository;

import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {

    Set<Painting> findAllByOwnerNot(User user);

    Set<Painting> findTop2ByOrderByVotesDescNameAsc();


}
