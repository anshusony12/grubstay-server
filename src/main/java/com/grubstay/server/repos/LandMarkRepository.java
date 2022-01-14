package com.grubstay.server.repos;

import com.grubstay.server.entities.LandMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LandMarkRepository extends JpaRepository<LandMarks, Integer> {
    public LandMarks findLandMarksByLandMarkId(int landMarkId);

    @Query("from LandMarks l where l.pgStayId.pgId=?1")
    public List<LandMarks> findLandMarksByPgStayId(String pgId);

    @Query("from LandMarks l where l.landMarkName=?1 and l.pgStayId.pgId=?2")
    public LandMarks findLandMarksByLandMarkNameAndPgStayId(String landMarkName, String pgId);

    @Transactional
    @Modifying
    @Query("delete from LandMarks l where l.landMarkId=?1")
    public void deleteByLandMarkId(int landMarkId);


}
