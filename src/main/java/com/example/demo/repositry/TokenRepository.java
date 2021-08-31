package com.example.demo.repositry;

import com.example.demo.model.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<ConfirmationToken,Integer> {
    ConfirmationToken findByValue(String value);
}
