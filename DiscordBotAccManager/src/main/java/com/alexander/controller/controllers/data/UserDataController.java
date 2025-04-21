package com.alexander.controller.controllers.data;

import com.alexander.service.data.UserDataService;


public abstract class UserDataController {
    protected UserDataService userDataService;

    protected UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }
}
