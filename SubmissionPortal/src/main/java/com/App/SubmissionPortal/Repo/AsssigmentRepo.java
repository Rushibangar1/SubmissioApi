package com.App.SubmissionPortal.Repo;

import com.App.SubmissionPortal.Model.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AsssigmentRepo extends MongoRepository<Assignment,String> {
    Object findByUserId(String userId);
}
