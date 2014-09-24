package com.digitalbuddha.daggerdemo.job;

/**
 * Created by MikeN on 9/14/14.
 */
public class single {
    private static single ourInstance = new single();

    public static single getInstance() {
        return ourInstance;
    }

    private single() {
    }
}
