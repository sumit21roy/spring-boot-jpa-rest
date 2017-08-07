package com.sargent.task.api.rest;

import com.sargent.task.domain.UserDetail;
import com.sargent.task.service.UserDetailService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/example/v1/userDetails")
@Api(value = "userDetails", description = "UserDetail API")
public class UserDetailController extends AbstractRestHandler {

    @Autowired
    private UserDetailService userDetailService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a userDetail resource.", notes = "Returns the URL of the new resource for the user details.")
    public void createUserDetail(@RequestBody UserDetail userDetail,
                                 HttpServletRequest request, HttpServletResponse response) {
        UserDetail createdUserDetail = this.userDetailService.createUserDetail(userDetail);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdUserDetail.getId()).toString());
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET,produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single user detail.", notes = "You have to provide a valid user ID.")
    public
    @ResponseBody
    UserDetail getUserDetail(@ApiParam(value = "The ID of the user.", required = true)
                             @PathVariable("id") Long id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserDetail userDetail = this.userDetailService.getUserDetail(id);
        checkResourceFound(userDetail);
        return userDetail;
    }
}
