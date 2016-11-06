package org.fund.util;

import org.fund.user.entity.User;

public class UserHolder {

    private static ThreadLocal<User> userHolder = new ThreadLocal<User>();

    public static User getUser() {
        return userHolder.get();
    }

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static long getUserId() {
        return userHolder.get().getUserId();
    }

    public static void remove() {
        userHolder.remove();
    }
}
