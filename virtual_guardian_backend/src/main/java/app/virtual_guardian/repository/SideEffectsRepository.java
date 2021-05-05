package app.virtual_guardian.repository;

import app.virtual_guardian.entity.SideEffect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SideEffectsRepository extends JpaRepository<SideEffect, Integer> {
}
