package app.virtual_guardian.service;

import app.virtual_guardian.entity.Activity;
import app.virtual_guardian.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getActivitiesForTheDay(String userId, String day){
        return activityRepository.getActivitiesOfPatientIdAndDay(userId, day);
    }
}
