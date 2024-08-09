package com.example.authservice.services.impls;

import com.example.authservice.services.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String generate(UserDetails userDetails) {
        return "";
    }
}
