package com.example.library.service;

import com.example.library.models.AuthenticationReqModel;
import com.example.library.models.AuthenticationResModel;
import com.example.library.models.RegisterReqModel;

public interface AuthenticationService {

	AuthenticationResModel register(RegisterReqModel request);
		
	AuthenticationResModel authenticate(AuthenticationReqModel request);
}
