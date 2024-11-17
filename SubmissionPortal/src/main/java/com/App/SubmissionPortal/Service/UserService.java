package com.App.SubmissionPortal.Service;

import com.App.SubmissionPortal.Dto.AssignmentDto;
import com.App.SubmissionPortal.Dto.LoginDto;
import com.App.SubmissionPortal.Dto.UserDto;
import com.App.SubmissionPortal.Model.Assignment;
import com.App.SubmissionPortal.Model.User;
import com.App.SubmissionPortal.Repo.AsssigmentRepo;
import com.App.SubmissionPortal.Repo.UserRepo;
import com.App.SubmissionPortal.Utillity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AsssigmentRepo asssigmentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }

    public String loginUser(LoginDto loginDto) {
        UserDetails userDetails = loadUserByUsername(loginDto.getEmail());
        if (passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) {
            return jwtUtil.generateToken(userDetails);
        }
        return null;
    }

    public ResponseEntity<String> registerUser(UserDto userDto) {
        if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists with this email.");
        }

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encryptedPassword);
        user.setRole("USER");

        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    public ResponseEntity<String> uploadAssignment(String userId, AssignmentDto assignmentDto) {

        Optional<User> userOptional = userRepo.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        Assignment assignment = new Assignment();
        assignment.setUserId(userId);
        assignment.setTask(assignmentDto.getTask());
        assignment.setStatus("PENDING");
        assignment.setTimeStamp(LocalDateTime.now());


        asssigmentRepo.save(assignment);
        return ResponseEntity.ok("Assignment uploaded successfully.");
    }

    public ResponseEntity<Object> getAssignments(String userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        return ResponseEntity.ok(asssigmentRepo.findByUserId(userId));
    }
}
