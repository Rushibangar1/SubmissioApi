package com.App.SubmissionPortal.Service;

import com.App.SubmissionPortal.Dto.LoginDto;
import com.App.SubmissionPortal.Dto.UserDto;
import com.App.SubmissionPortal.Model.Assignment;
import com.App.SubmissionPortal.Model.User;

import com.App.SubmissionPortal.Repo.AsssigmentRepo;
import com.App.SubmissionPortal.Repo.UserRepo;
import com.App.SubmissionPortal.Utillity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Lazy
@Service
public class AdminService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AsssigmentRepo assignmentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    public ResponseEntity<String> registerAdmin(UserDto userDto) {

        Optional<User> existingUser = userRepo.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email is already registered.");
        }


        User admin = new User();
        admin.setName(userDto.getName());
        admin.setEmail(userDto.getEmail());
        admin.setRole("ADMIN");
        admin.setPassword(passwordEncoder.encode(userDto.getPassword()));


        userRepo.save(admin);
        return ResponseEntity.ok("Admin registered successfully.");
    }


    public String loginAdmin(LoginDto loginRequest) {
        Optional<User> userOptional = userRepo.findByEmail(loginRequest.getEmail());
        if (userOptional.isEmpty()) {
            return null;
        }
        User user = (User) userOptional.get();
        UserDetails userDetails = loadUserByUsername(user.getEmail());
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return null;
        }
        ;
        return jwtUtil.generateToken(userDetails);
    }

    private UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepo.findByEmail(email);  // Assuming userRepo returns Optional<User>
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("User not found with email: " + email);
    }



    public ResponseEntity<Object> viewAssignments() {

        return ResponseEntity.ok(assignmentRepo.findAll());
    }


    public ResponseEntity<String> acceptAssignment(String id) {

        Optional<Assignment> assignmentOptional = assignmentRepo.findById(id);
        if (assignmentOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Assignment not found.");
        }

        Assignment assignment = (Assignment) assignmentOptional.get();
        assignment.setStatus("ACCEPTED");


        assignmentRepo.save(assignment);
        return ResponseEntity.ok("Assignment accepted.");
    }


    public ResponseEntity<String> rejectAssignment(String id) {

        Optional<Assignment> assignmentOptional = assignmentRepo.findById(id);
        if (assignmentOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Assignment not found.");
        }

        Assignment assignment = (Assignment) assignmentOptional.get();
        assignment.setStatus("REJECTED");


        assignmentRepo.save(assignment);
        return ResponseEntity.ok("Assignment rejected.");
    }
}
