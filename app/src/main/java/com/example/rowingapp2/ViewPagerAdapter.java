package com.example.rowingapp2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    Bundle bundle;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Bundle bundle) {
        super(fragmentActivity);
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position)
        {
            default:
                MembersPage membersPage = new MembersPage();
                membersPage.setArguments(bundle);
                return membersPage;
            case 1: return new WorkoutPage();
            case 2: return new CalendarPage();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
