package com.kick_api.repository;

import com.kick_api.entity.Brand;
import com.kick_api.constant.BrandStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    
    List<Brand> findAllByStatus(BrandStatus status);
}
