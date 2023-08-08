package com.example.careforadmin;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class QuoteAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

    public QuoteAdapter(FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public Fragment getItem(int posititon){
        switch (posititon){
            case 0:
                CG_QuoteFragment cg_quoteFragment = new CG_QuoteFragment();
                return cg_quoteFragment;
            case 1:
                Client_QuoteFragment client_quoteFragment = new Client_QuoteFragment();
                return client_quoteFragment;
            default:
                return null;
        }
    }
}
