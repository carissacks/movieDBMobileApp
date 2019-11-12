//package com.dicoding.movieandtvshow.db;
//
//import android.os.Handler;
//import android.os.Looper;
//
//import androidx.annotation.NonNull;
//
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//public class AppExecutors {
//    private static final Object LOCK = new Object();
//    private static AppExecutors instance;
//    private final Executor diskIO;
//    private final Executor networkIO;
//
//    public AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
//        this.diskIO = diskIO;
//        this.networkIO = networkIO;
//        this.mainThread = mainThread;
//    }
//
//    private final Executor mainThread;
//
//    public static AppExecutors getInstance(){
//        if(instance==null){
//            synchronized (LOCK){
//                instance= new AppExecutors(Executors.newSingleThreadExecutor(),
//                        Executors.newFixedThreadPool(3),
//                        new MainThreadExecutor());
//            }
//        }
//        return instance;
//    }
//    public Executor diskIO() {
//        return diskIO;
//    }
//
//    public Executor mainThread() {
//        return mainThread;
//    }
//
//    public Executor networkIO() {
//        return networkIO;
//    }
//
//    private static class MainThreadExecutor implements Executor {
//        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
//
//        @Override
//        public void execute(@NonNull Runnable command) {
//            mainThreadHandler.post(command);
//        }
//    }
//}
