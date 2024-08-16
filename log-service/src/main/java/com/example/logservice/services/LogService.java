package com.example.logservice.services;

import com.example.logservice.dtos.LogMessageDto;

public interface LogService {

    void log(LogMessageDto logMessage);
}
