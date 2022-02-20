package com.grubstay.server.repos;

import com.grubstay.server.entities.StayForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StayFormRepository extends JpaRepository<StayForm,Long> {

}
