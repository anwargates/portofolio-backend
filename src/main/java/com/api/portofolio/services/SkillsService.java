package com.api.portofolio.services;

import com.api.portofolio.models.GenericResponse;
import com.api.portofolio.models.LoginReq;
import com.api.portofolio.models.LoginRes;
import com.api.portofolio.models.RegisterRes;
import com.api.portofolio.models.SkillsReq;
import com.api.portofolio.models.entities.PortoUser;
import com.api.portofolio.models.entities.Skills;
import com.api.portofolio.repositories.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

@Service
public class SkillsService {

    @Autowired
    private SkillsRepository skillsRepository;


    public GenericResponse doAddSkill(SkillsReq skillsReq, MultipartFile icon) {
        GenericResponse resp = new GenericResponse();
        try {
            Skills skills = new Skills();
            skills.setUserId(skillsReq.getUserId());
            skills.setTitle(skillsReq.getTitle());
            skills.setDescription(skillsReq.getDescription());

            // Save icon if provided
            if (icon != null && !icon.isEmpty()) {
                String uploadsDir = "uploads/skill-icons";
                String iconName = UUID.randomUUID() + icon.getOriginalFilename();
                Path iconPath = Paths.get(uploadsDir);
                Files.copy(icon.getInputStream(), iconPath.resolve(iconName));
                skills.setIcon(uploadsDir + "/" + iconName);
            }

            Skills savedSkills = skillsRepository.save(skills);

            resp.setMessage("Skills saved successfully");
            resp.setStatusCode(HttpStatus.CREATED.value());
            resp.setData(savedSkills);
            return resp;
        } catch (Exception e) {
            resp.setMessage(e.getMessage());
            resp.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return resp;
        }
    }

//    public ResponseEntity<GenericResponse> signIn(LoginReq loginReq) {
//        GenericResponse resp = new GenericResponse();
//        try {
//            var user = skillsRepository.findByEmail(loginReq.getEmail()).orElseThrow(
//                    () -> new UsernameNotFoundException("Email not found")
//            );
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
//            System.out.println("USER IS: " + user);
//            var jwt = jwtUtils.generateToken(user);
//            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
//            resp.setMessage("Successfully Signed In");
//            resp.setStatusCode(HttpStatus.OK.value());
//            resp.setData(
//                    new LoginRes(
//                            user.getName(),
//                            user.getEmail(),
//                            jwt,
//                            refreshToken,
//                            "24Hr"
//                    )
//            );
//
//            // record activity
////            logActivityService.recordActivity(
////                    user.getId(),
////                    user.getEmail(),
////                    "SIGN IN SUCCESS"
////            );
//            return ResponseEntity.ok().body(resp);
//        } catch (BadCredentialsException e) {
//            resp.setMessage("Wrong Password");
//            resp.setStatusCode(HttpStatus.UNAUTHORIZED.value());
//
//            // record activity
////            logActivityService.recordActivity(
////                    loginReq.getEmail(),
////                    "SIGN IN FAILED"
////            );
//            return ResponseEntity.internalServerError().body(resp);
//        } catch (UsernameNotFoundException e) {
//            resp.setMessage("Email not found");
//            resp.setStatusCode(HttpStatus.NOT_FOUND.value());
//
//            return ResponseEntity.internalServerError().body(resp);
//        }
//    }

}

