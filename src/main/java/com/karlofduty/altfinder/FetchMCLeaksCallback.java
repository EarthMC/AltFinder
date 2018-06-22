package com.karlofduty.altfinder;

public interface FetchMCLeaksCallback {

    // -1: Error, 0: Not mcleaks, 1: IsMcleaks
    void onCheckDone(int result, String username);

}