package com.radanov.movieapp10.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.radanov.movieapp10.R;
import com.radanov.movieapp10.databinding.FragmentMovieDetailsBinding;
import com.radanov.movieapp10.databinding.LayoutBottomSheetBinding;
import com.radanov.movieapp10.io.models.MovieModel;

import com.radanov.movieapp10.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;


public class MovieDetailsFragment extends Fragment {

    Context context;
    FragmentMovieDetailsBinding binding;
    LayoutBottomSheetBinding bindingBottomSheet;

    private MovieModel movie;
    private boolean isWatchlist;
    private NavController navController;

    private final List<Integer> test_movie = new ArrayList<>();

    private MovieViewModel movieViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false);
        bindingBottomSheet = LayoutBottomSheetBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(this);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        movieViewModel.getWatchlistMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                for (int i = 0; i < movieModels.size(); i++) {
                    test_movie.add(movieModels.get(i).getId());
                }
            }
        });

        if (getArguments() != null) {
            movie = (MovieModel) getArguments().getSerializable("movie");
            isWatchlist = getArguments().getBoolean("is_watchlist");
        }
        initView();

        return binding.getRoot();
    }

    private void initView() {

        binding.textViewTitleDetails.setText(movie.getTitle());
        binding.textViewDescDetails.setText(movie.getOverview());
        binding.TVRating.setText(String.valueOf(movie.getVote_average()));
        binding.TVDate.setText(movie.getRelease_date());
        //binding.TVDate.setText(DateTimeUtils.serverToUserFormat(movie.getRelease_date(), "dd/MM/yyyy"));

        Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + movie.getPoster_path()).into(binding.imageViewDetails);

        if (isWatchlist) {
            binding.buttonAddWatch.setText("Delete \nfrom watchlist");
        }

        binding.btnSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.Theme_Design_BottomSheetDialog);
                View bottomSheetView = LayoutInflater.from(
                        context.getApplicationContext()).inflate(
                        R.layout.layout_bottom_sheet, bindingBottomSheet.bottomSheetContainer);
                bindingBottomSheet.overviewTitle.setText("Movie Overview:");
                bindingBottomSheet.overviewPopup.setText(movie.getOverview());
                if (bottomSheetView.getParent() != null) {
                    ((ViewGroup) bottomSheetView.getParent()).removeView(bottomSheetView);
                }
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.cancel();
                bottomSheetDialog.show();
            }
        });

        binding.buttonAddWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isWatchlist) {
                    movieViewModel.deleteWatchlist(movie);
                    navController.popBackStack();
                    Toast.makeText(context, "Movie Deleted from your watchlist", Toast.LENGTH_SHORT).show();
                }
                if (test_movie.contains(movie.getId())) {
                    Toast.makeText(context, "You Already Have this Movie in Watchlist", Toast.LENGTH_SHORT).show();
                } else {
                    movieViewModel.insertWatchlist(movie);
                    Toast.makeText(context, "Movie Saved to your watchlist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}