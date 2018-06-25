package com.karlofduty.altfinder.callbacks;

public interface FetchMCLeaksCallback {

    // -1: Error, 0: Not mcleaks, 1: IsMcleaks, 2: Not found
    void onCheckDone(int result);

}