package com.example.EverExpanding.service;

import com.example.EverExpanding.model.view.StatsViewModel;

public interface StatsService {
    StatsViewModel getStats();

    void onRequest();
}
