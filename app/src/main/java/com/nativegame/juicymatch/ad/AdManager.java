package com.nativegame.juicymatch.ad;

import android.app.Activity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.nativegame.juicymatch.R;
import com.nativegame.natyengine.util.ResourceUtils;



public class AdManager {

    private final Activity mActivity;

    private RewardedAd mRewardedAd;
    private AdRewardListener mListener;
    private boolean mRewardEarned = false;


    public AdManager(Activity activity) {
        mActivity = activity;
        requestAd();
    }

    public AdRewardListener getListener() {
        return mListener;
    }

    public void setListener(AdRewardListener listener) {
        mListener = listener;
    }

    public void requestAd() {
        if (mRewardedAd != null) {
            return;
        }

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(mActivity, ResourceUtils.getString(mActivity, R.string.txt_admob_reward),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });
    }

    public boolean showRewardAd() {
        if (mRewardedAd == null) {
            return false;
        }

        mRewardEarned = false;

        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdShowedFullScreenContent() {
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                mRewardedAd = null;
                if (!mRewardEarned) {
                    mListener.onLossReward();
                }
                requestAd();
            }
        });

        mRewardedAd.show(mActivity, new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(RewardItem rewardItem) {
                mListener.onEarnReward();
                mRewardEarned = true;
            }
        });

        return true;
    }

    public interface AdRewardListener {

        void onEarnReward();

        void onLossReward();

    }

}
