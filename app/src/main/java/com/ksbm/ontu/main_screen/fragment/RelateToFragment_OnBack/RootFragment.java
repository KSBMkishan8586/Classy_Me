package com.ksbm.ontu.main_screen.fragment.RelateToFragment_OnBack;

import androidx.fragment.app.Fragment;

/**
 * Created
 */

public class RootFragment extends Fragment implements OnBackPressListener {

    @Override
    public boolean onBackPressed() {
        return new BackPressImplimentation(this).onBackPressed();
    }
}