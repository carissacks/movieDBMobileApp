//package com.dicoding.movieandtvshow.db;
//
//import android.app.Application;
//import android.os.AsyncTask;
//
//import java.util.List;
//
//public class MovieRepository {
//
//    private MovieDatabaseDAO movieDatabaseDAO;
//    private List<MovieDatabase> listMovieDatabase;
//
//    public MovieRepository(Application application) {
//        AppDatabase db = AppDatabase.getDatabase(application);
//        movieDatabaseDAO = db.movieDao();
//        listMovieDatabase = movieDatabaseDAO.getAll();
//    }
//
//    public List<MovieDatabase> getAllMovies() {
//        return listMovieDatabase;
//    }
//
//    public void insert(MovieDatabase movie) {
//        new insertAsyncTask(movieDatabaseDAO).execute(movie);
//    }
//
//    private static class insertAsyncTask extends AsyncTask<MovieDatabase, Void, Void> {
//
//        private MovieDatabaseDAO mAsyncTaskDao;
//
//        insertAsyncTask(MovieDatabaseDAO dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(MovieDatabase... movieDatabases) {
//            mAsyncTaskDao.insert(movieDatabases[0]);
//            return null;
//        }
//    }
//
//    public void delete(MovieDatabase movie){
//        new deleteAsyncTask(movieDatabaseDAO).execute(movie);
//    }
//
//    private static class deleteAsyncTask extends AsyncTask<MovieDatabase, Void, Void>{
//
//        private MovieDatabaseDAO mAsyncTaskDao;
//
//        public deleteAsyncTask(MovieDatabaseDAO dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(MovieDatabase... movieDatabases) {
//            mAsyncTaskDao.delete(movieDatabases[0]);
//            return null;
//        }
//    }
//
//
//}
