package com.example.fakestore.utilities;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
//This class manages threads; STOLEN FROM GITHUB
public class AppExecutors {
    private static AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static synchronized AppExecutors getInstance() {  // FOR SINGLETON INSTANTIATION USE synchronized KEYWORD
        if (sInstance == null) {
//            synchronized (LOCK) {
            sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                    Executors.newFixedThreadPool(3),
                    new MainThreadExecutor());
//            }
        }
        return sInstance;
    }

    public Executor diskIO() {
        return diskIO;
    } //USE THIS TO FETCH DATA FROM DATABASE

    public Executor mainThread() {
        return mainThread;
    } //THIS IS THE MAIN THREAD

    public Executor networkIO() {
        return networkIO;
    } //USE THIS TO RUN TASK LIKE FETCH FROM NETWORK

    private static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}