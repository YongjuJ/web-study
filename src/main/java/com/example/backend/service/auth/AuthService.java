package com.example.backend.service.auth;

import com.example.backend.dto.request.RegisterRequest;
import com.example.backend.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);
}
