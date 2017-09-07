package com.example.schedule;

public class ScheduleHolder {

    private static ThreadLocal<Schedule> holder = new ThreadLocal();

    public static void set(Schedule schedule) {
        holder.set(schedule);
    }

    public static Schedule get() {
        return holder.get();
    }
}
