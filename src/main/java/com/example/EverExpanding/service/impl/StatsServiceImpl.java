package com.example.EverExpanding.service.impl;

import com.example.EverExpanding.model.view.StatsViewModel;
import com.example.EverExpanding.service.StatsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

    private int anonymousRequest, authorizedRequest;

    @Override
    public StatsViewModel getStats() {
        return new StatsViewModel(authorizedRequest, anonymousRequest);
    }

    @Override
    public void onRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && (authentication.getPrincipal() instanceof UserDetails)) {
            authorizedRequest++;
        } else {
            anonymousRequest++;
        }
    }
}
