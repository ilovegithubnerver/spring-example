package com.example.datasource;

public class RoutingDataSourceHolder {

    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static void set(String key) {
        holder.set(key);
    }

    public static String get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }

}
