package com.karlofduty.altfinder.callbacks;

public interface VPNCallback
{
    // JSON object if successful, ERROR if not
    void onCheckDone(String result);
}
