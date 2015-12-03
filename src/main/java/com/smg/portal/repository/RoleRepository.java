package com.smg.portal.repository;

import com.smg.portal.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Role entity.
 */
public interface RoleRepository extends JpaRepository<Role, String> {
}
