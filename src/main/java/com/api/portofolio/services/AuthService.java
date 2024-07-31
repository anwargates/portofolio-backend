package com.api.portofolio.services;

import com.api.portofolio.models.GenericResponse;
import com.api.portofolio.models.LoginReq;
import com.api.portofolio.models.LoginRes;
import com.api.portofolio.models.RegisterReq;
import com.api.portofolio.models.RegisterRes;
import com.api.portofolio.models.entities.PortoUser;
import com.api.portofolio.repositories.PortoUserRepository;
import com.api.portofolio.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private PortoUserRepository portoUserRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    private LogActivityService logActivityService;

    public GenericResponse signUp(RegisterReq registerReq) {
        GenericResponse resp = new GenericResponse();
//        UserActivity userActivity = new UserActivity();
        try {
            if (portoUserRepository.existsByEmail(registerReq.getEmail())) {
                resp.setMessage("Email is already registered");
                resp.setStatusCode(HttpStatus.BAD_REQUEST.value());
                return resp;
            }

            PortoUser ourUser = new PortoUser();
            ourUser.setEmail(registerReq.getEmail());
            ourUser.setPassword(passwordEncoder.encode(registerReq.getPassword()));
            ourUser.setRole(registerReq.getRole());
            ourUser.setName(registerReq.getName());

//            // Save profile picture if provided
//            if (profilePicture != null && !profilePicture.isEmpty()) {
//                String uploadsDir = "uploads";
//                String profilePictureName = UUID.randomUUID() + profilePicture.getOriginalFilename();
//                Path profilePicturePath = Paths.get(uploadsDir);
//                Files.copy(profilePicture.getInputStream(), profilePicturePath.resolve(profilePictureName));
//                ourUser.setProfilePicture(uploadsDir + "/" + profilePictureName);
//            }

            PortoUser savedUser = portoUserRepository.save(ourUser);

            var jwt = jwtUtils.generateToken(savedUser);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), savedUser);

            if (savedUser.getId() > 0) {
                RegisterRes registerRes = new RegisterRes();
                registerRes.setToken(jwt);
                registerRes.setRefreshToken(refreshToken);
                registerRes.setExpirationTime("24Hr");
                registerRes.setUserDetails(savedUser);

                resp.setMessage("User saved successfully");
                resp.setStatusCode(HttpStatus.CREATED.value());
                resp.setData(registerRes);

                // record activity
//                logActivityService.recordActivity(
//                        ourUser.getId(),
//                        ourUser.getEmail(),
//                        "SIGN UP SUCCESS"
//                );
                return resp;
            }
            return resp;
        } catch (Exception e) {
            resp.setMessage(e.getMessage());
            resp.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            // record activity
//            logActivityService.recordActivity(
//                    registerReq.getEmail(),
//                    "SIGN UP FAILED"
//            );
            return resp;
        }
    }

    public GenericResponse signIn(LoginReq loginReq) {
        GenericResponse resp = new GenericResponse();
        try {
            var user = portoUserRepository.findByEmail(loginReq.getEmail()).orElseThrow(
                    () -> new UsernameNotFoundException("Email not found")
            );
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            System.out.println("USER IS: " + user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            resp.setMessage("Successfully Signed In");
            resp.setStatusCode(HttpStatus.OK.value());
            resp.setData(
                    new LoginRes(
                            user.getName(),
                            user.getEmail(),
                            jwt,
                            refreshToken,
                            "24Hr"
                    )
            );

            // record activity
//            logActivityService.recordActivity(
//                    user.getId(),
//                    user.getEmail(),
//                    "SIGN IN SUCCESS"
//            );
            return resp;
        } catch (BadCredentialsException e) {
            resp.setMessage("Wrong Password");
            resp.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            return resp;
        } catch (UsernameNotFoundException e) {
            resp.setMessage("Email not found");
            resp.setStatusCode(HttpStatus.NOT_FOUND.value());
            return resp;
        }
    }

//    public ResponseEntity<ReqRes> refreshToken(ReqRes refreshTokenRequest) {
//        ReqRes resp = new ReqRes();
//        String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
//        OurUser users = portoUserRepository.findByEmail(ourEmail).orElseThrow();
//        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
//            var jwt = jwtUtils.generateToken(users);
//            resp.setMessage("Successfully Refreshed Token");
//            resp.setStatusCode(HttpStatus.OK.value());
//            resp.setToken(jwt);
//            resp.setRefreshToken(refreshTokenRequest.getToken());
//            resp.setExpirationTime("24Hr");
//
//            // record activity
//            logActivityService.recordActivity(
//                    users.getId(),
//                    users.getEmail(),
//                    "REFRESH TOKEN SUCCESS"
//            );
//            return ResponseEntity.ok().body(resp);
//        } else {
//            resp.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//
//            // record activity
//            logActivityService.recordActivity(
//                    ourEmail,
//                    "REFRESH TOKEN FAILED"
//            );
//            return ResponseEntity.internalServerError().body(resp);
//        }
//    }
}

