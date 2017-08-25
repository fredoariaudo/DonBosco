package com.donbosco.android.porlosjovenes.model;

import com.google.gson.annotations.SerializedName;

public class RunResultResponse
{
    @SerializedName("IdActividad")
    private long runId;

    public long getRunId() {
        return runId;
    }

    public void setRunId(long runId) {
        this.runId = runId;
    }
}
