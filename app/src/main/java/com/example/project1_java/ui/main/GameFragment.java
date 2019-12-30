package com.example.project1_java.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project1_java.FixableViewPager;
import com.example.project1_java.MainActivity;
import com.example.project1_java.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameFragment extends Fragment {

    static GameFragment newInstance() {
        return new GameFragment();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        final View view = getView();
        assert view != null;
        Button startButton = view.findViewById(R.id.game_start_button);
        //Log.d("startButton",""+startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Block swipe */
                FixableViewPager viewPager;
                try{
                    viewPager = getActivity().findViewById(R.id.view_pager);
                    viewPager.setPageFixed(!viewPager.getPageFixed());
                }
                catch(Exception e){
                    Log.d("onStart","Error");
                }

                /* Switch to gamePlayFragment */
                assert getFragmentManager() != null;
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.game_fragment, new GamePlayFragment());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                trans.addToBackStack(null);

                trans.commit();
                //((MainActivity)getActivity()).replaceFragment(GamePlayFragment.newInstance());
            }
        });
    }
}