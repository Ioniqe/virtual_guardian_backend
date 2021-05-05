package app.virtual_guardian.repository;

import app.virtual_guardian.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    @Query(value = "SELECT * FROM patient_daily_activities WHERE patient_id = :patientId AND patient_daily_activities.day = :today", nativeQuery = true)
    List<Activity> getActivitiesOfPatientIdAndDay(@Param("patientId") String patientId, @Param("today") String day);

}
