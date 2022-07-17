package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.user.AddressBookEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressBookRepository extends JpaRepository<AddressBookEntity,Long> {

    Page<AddressBookEntity> findAllByUserEntity(UserEntity userEntity, Pageable pageable);

}
