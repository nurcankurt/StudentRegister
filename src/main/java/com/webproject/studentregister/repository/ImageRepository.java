package com.webproject.studentregister.repository;

import com.webproject.studentregister.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    @Modifying
    @Transactional
    void deleteByName(String name);

    Image getByName(String name);
}
