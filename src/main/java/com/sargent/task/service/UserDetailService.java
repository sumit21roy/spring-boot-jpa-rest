package com.sargent.task.service;

import com.sargent.task.dao.jpa.UserDetailRepository;
import com.sargent.task.domain.UserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class UserDetailService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailService.class);

    @Autowired
    private UserDetailRepository userDetailRepository;

//    @Autowired
//    CounterService counterService;
//
//    @Autowired
//    GaugeService gaugeService;

    public UserDetailService() {
    }

    public UserDetail createUserDetail(UserDetail userDetail) {
        return userDetailRepository.save(userDetail);
    }

    public UserDetail getUserDetail(long id) {
        return userDetailRepository.findOne(id);
    }

}
