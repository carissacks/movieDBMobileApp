//package com.dicoding.movieandtvshow.model;
//
//import android.app.Application;
//
//import androidx.lifecycle.AndroidViewModel;
//
//import com.dicoding.movieandtvshow.db.MovieDatabase;
//import com.dicoding.movieandtvshow.db.MovieRepository;
//
//import java.util.List;
//
//public class MovieViewModel extends AndroidViewModel {
//    private MovieRepository movieRepository;
//    private List<MovieDatabase> listMovieDatabase;
//
//    public MovieViewModel(Application application) {
//        super(application);
//        movieRepository= new MovieRepository(application);
//        listMovieDatabase= movieRepository.getAllMovies();
//    }
//
//    List<MovieDatabase> getAllMovies(){
//        return listMovieDatabase;
//    }
//
//    public void insert(MovieDatabase movie){
//        movieRepository.insert(movie);
//    }
//
//    public void delete(MovieDatabase movie){
//        movieRepository.delete(movie);
//    }
//}
