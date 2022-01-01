package com.grubstay.server.services.impl;

import com.grubstay.server.repos.SubLocationRepository;
import com.grubstay.server.services.SubLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubLocationServiceImpl implements SubLocationService {

    @Autowired
    private SubLocationRepository _subLocationRepository;

}
