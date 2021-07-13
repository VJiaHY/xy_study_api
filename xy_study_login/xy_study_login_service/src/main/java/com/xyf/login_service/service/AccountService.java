package com.xyf.login_service.service;

import com.xyf.login_api.domain.md.LoginMD;
import com.xyf.login_api.domain.md.SingInMD;
import com.xyf.result.R;

import javax.servlet.http.HttpServletResponse;

public interface AccountService {


    R login(LoginMD loginMD, HttpServletResponse response);

    R sigIn(SingInMD singInMD);

    R getCode();

}
