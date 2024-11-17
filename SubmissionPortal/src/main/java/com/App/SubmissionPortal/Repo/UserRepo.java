package com.App.SubmissionPortal.Repo;

import com.App.SubmissionPortal.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);

}
