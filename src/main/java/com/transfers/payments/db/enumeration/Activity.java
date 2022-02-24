package com.transfers.payments.db.enumeration;


import com.transfers.payments.db.entity.User;

public enum Activity {
    ACTIVE, BLOCKED;

    public static Activity getActivity(User user) {
        int activityId = user.getActivityId();
        return Activity.values()[activityId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
