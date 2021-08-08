package com.tochie.ussdexample.repositories;

import com.tochie.ussdexample.services.UssdSession;
import org.springframework.data.repository.CrudRepository;

public interface UssdSessionRepository extends CrudRepository<UssdSession, String> {
}
