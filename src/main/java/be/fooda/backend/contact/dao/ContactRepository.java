package be.fooda.backend.contact.dao;

import be.fooda.backend.contact.model.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    Boolean existsByEmail(String email);

    List<ContactEntity> findAllByUser_ExternalUserId(Long userId);
}
