package com.App.SubmissionPortal.Controller;


import com.App.SubmissionPortal.Dto.AssignmentDto;
import com.App.SubmissionPortal.Dto.LoginDto;
import com.App.SubmissionPortal.Dto.UserDto;
import com.App.SubmissionPortal.Service.UserService;
import com.App.SubmissionPortal.Utillity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

      @Autowired
      private UserService userService;

      @Autowired
      private JwtUtil jwtUtil;


      @GetMapping("/HelloWorld")
      public String SayHello(){
        return "ThisIsMe";
      }


      @PostMapping("/register")
      public ResponseEntity<String> registerUser(@RequestBody UserDto userDto){
          System.out.println("This was Hit");
          return userService.registerUser(userDto);
      }

      @PostMapping("/login")
      public ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto) {
        String jwtToken = userService.loginUser(loginDto);
        return ResponseEntity.ok(jwtToken);
      }

    @PostMapping("/upload-assignment")
    public ResponseEntity<String> uploadAssignment(@RequestBody AssignmentDto assignmentDto) {
        System.out.println(assignmentDto);
        System.out.println(assignmentDto.getUserId()+assignmentDto.getTask()+assignmentDto.getStatus());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); //
        return userService.uploadAssignment(assignmentDto.getUserId(), assignmentDto);
    }

    @GetMapping("/assignments")
    public ResponseEntity<Object> getAssignments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return userService.getAssignments(userId);
    }


}
