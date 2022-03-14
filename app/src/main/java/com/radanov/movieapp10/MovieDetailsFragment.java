package com.radanov.movieapp10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.radanov.movieapp10.databinding.FragmentMovieDetailsBinding;
import com.radanov.movieapp10.databinding.LayoutBottomSheetBinding;
import com.radanov.movieapp10.models.MovieModelOffline;

import com.radanov.movieapp10.viewmodels.MovieViewModelDb;

import java.util.ArrayList;
import java.util.List;



public class MovieDetailsFragment extends Fragment {

    Context context;
    FragmentMovieDetailsBinding binding;
    LayoutBottomSheetBinding bindingBottomSheet;

    private MovieModelOffline movie;
    private boolean isWatchlist;
    private NavController navController;

    private final List<Integer> test_movie = new ArrayList<>();


    private MovieViewModelDb movieViewModelDb;

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
        movieViewModelDb = new ViewModelProvider(this).get(MovieViewModelDb.class);

        movieViewModelDb.getAllDbMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModelOffline>>() {
            @Override
            public void onChanged(List<MovieModelOffline> movieModelOfflines) {
                for (int i = 0; i < movieModelOfflines.size(); i++) {
                test_movie.add(movieModelOfflines.get(i).getId());
                }
            }
        });

        if (getArguments() != null) {
            movie = (MovieModelOffline) getArguments().getSerializable("movie");
            isWatchlist = getArguments().getBoolean("is_watchlist");
        }
        initView();

        // Inflate the layout for this fragment
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
                    ((ViewGroup)bottomSheetView.getParent()).removeView(bottomSheetView);
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
                    movieViewModelDb.delete(movie);
                    navController.popBackStack();
                    Toast.makeText(context, "Movie Deleted from your watchlist", Toast.LENGTH_SHORT).show();
                }
                if (test_movie.contains(movie.getId())) {
                    Toast.makeText(context, "You Already Have this Movie in Watchlist", Toast.LENGTH_SHORT).show();
                } else {
                        movieViewModelDb.insert(movie);
                        Toast.makeText(context, "Movie Saved to your watchlist", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}