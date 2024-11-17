package com.App.SubmissionPortal.Controller;


import com.App.SubmissionPortal.Dto.LoginDto;
import com.App.SubmissionPortal.Dto.UserDto;
import com.App.SubmissionPortal.Service.AdminService;
import com.App.SubmissionPortal.Utillity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(@RequestBody UserDto userDto) {

        System.out.println(userDto);
        return adminService.registerAdmin(userDto);
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody LoginDto loginRequest) {
        System.out.println(loginRequest);
        String jwtToken = adminService.loginAdmin(loginRequest);
        return ResponseEntity.ok(jwtToken);
    }


    @GetMapping("/assignments")
    public ResponseEntity<Object> viewAssignments() {
        return adminService.viewAssignments();
    }


    @PutMapping("/assignments/accept/{id}")
    public ResponseEntity<String> acceptAssignment(@PathVariable String id) {
        return adminService.acceptAssignment(id);
    }


    @PutMapping("/assignments/reject/{id}")
    public ResponseEntity<String> rejectAssignment(@PathVariable String id) {
        return adminService.rejectAssignment(id);
    }
}