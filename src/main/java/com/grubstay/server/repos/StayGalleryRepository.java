package com.grubstay.server.repos;

import com.grubstay.server.entities.StayGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StayGalleryRepository extends JpaRepository<StayGallery,Long> {
    @Query("from StayGallery s where s.stayId = ?1")
    public List<StayGallery> getStayGalleryByPgId(String pgId);

    @Query("delete from StayGallery s where s.stayId = ?1")
    @Modifying
    @Transactional
    public void deletByStayId(String pgId);
}
