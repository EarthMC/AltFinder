package com.karlofduty.altfinder.commands;

public interface FetchMCLeaksCallback {

    // -1: Error, 0: Not mcleaks, 1: IsMcleaks
    void onCheckDone(int result);

}