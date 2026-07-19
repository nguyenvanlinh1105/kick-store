package com.kick_api.repository;

import com.kick_api.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
    List<Address> findAllByUserIdAndIsDeletedFalse(Long userId);

    Optional<Address> findByIdAndUserIdAndIsDeletedFalse(Long id, Long userId);

    Optional<Address> findByUserIdAndIsDefaultTrueAndIsDeletedFalse(Long userId);
}
