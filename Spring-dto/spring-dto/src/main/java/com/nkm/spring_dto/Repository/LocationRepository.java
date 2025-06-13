package com.nkm.spring_dto.Repository;

import com.nkm.spring_dto.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
