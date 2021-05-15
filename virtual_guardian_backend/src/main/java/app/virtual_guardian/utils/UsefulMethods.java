package app.virtual_guardian.utils;

import app.virtual_guardian.dto.MonitoredActivityDTO;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UsefulMethods {
    private static String[] activityArray = new String[]{"Breakfast", "Dinner", "Grooming", "Leaving", "Lunch", "Showering", "Sleeping", "Snack", "Spare_Time/TV", "Toileting"};

    private static int indexOf(String[] arr, String val) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(val))
                return i;
        }
        return -1;
    }

    private static double round(double value) {
        double val = value;
        val = val * 100;
        val = Math.round(val);
        val = val / 100;
        return val;
    }

    public static double[] toDurationFrequencyRatio(List<MonitoredActivityDTO> monitoredActivities) {
        //["Breakfast", "Dinner", "Grooming", "Leaving", "Lunch", "Showering", "Sleeping", "Snack", "Spare_Time/TV", "Toileting"]
        double[] computedArray = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        long[] durationOfActivities = new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] frequencyOfActivities = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        monitoredActivities.forEach(activity -> {
            long diff = TimeUnit.MILLISECONDS.toSeconds(activity.getEndTime().getTime() - activity.getStartTime().getTime());

            durationOfActivities[indexOf(activityArray, activity.getActivity())] += diff ;
            frequencyOfActivities[indexOf(activityArray, activity.getActivity())] += 1;
        });

        for (int i = 0; i < computedArray.length; i++) {
            if (frequencyOfActivities[i] != 0)
                computedArray[i] = round((double) durationOfActivities[i] / frequencyOfActivities[i]);
        }

        return computedArray;
    }
}
