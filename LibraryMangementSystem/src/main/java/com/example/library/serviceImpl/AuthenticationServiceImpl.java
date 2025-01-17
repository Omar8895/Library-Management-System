package com.example.library.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.library.entities.User;
import com.example.library.enums.ApiErrorEnum;
import com.example.library.enums.Role;
import com.example.library.exceptions.BusinessLogicViolationException;
import com.example.library.helper.Helper;
import com.example.library.models.AuthenticationReqModel;
import com.example.library.models.AuthenticationResModel;
import com.example.library.models.RegisterReqModel;
import com.example.library.repository.UserRepository;
import com.example.library.security.JwtService;
import com.example.library.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private final UserRepository userRepositorey;
	@Autowired
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private final JwtService jwtService;
	@Autowired
	private final AuthenticationManager authenticationManager;

	@Override
	@Transactional
	public AuthenticationResModel register(RegisterReqModel request) {

		Helper.validateRegisterRequest(request, 6, 10);
		
		User user = User
				.builder()
				.firstName(request.getFirstname())
				.LastName(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();

		userRepositorey.save(user);

		String jwtToken = jwtService.generateToken(user);

		return AuthenticationResModel.builder().token(jwtToken).build();
	}
	
	
	@Override
	public AuthenticationResModel authenticate(AuthenticationReqModel request) {

		Helper.validateAuthRequestBalnkOrNull(request);
		
		User user = userRepositorey.findByEmail(request.getEmail()).orElseThrow();

		Helper.validateAuthRequestMatches(request, user);
		
		boolean isMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());

		if (isMatch == false) {

			throw new BusinessLogicViolationException(ApiErrorEnum.WRONG_PASSWORD.name());
		}

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResModel.builder().token(jwtToken).build();
	}

}
