package com.abhinav.company_ai.controller;

import com.abhinav.company_ai.service.NvidiaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NvidiaTestController {

    private final NvidiaService nvidiaService;

    public NvidiaTestController(NvidiaService nvidiaService) {
        this.nvidiaService = nvidiaService;
    }

    @GetMapping("/api/test")
    public String test() {
        return nvidiaService.askNvidia("Say Hello from NVIDIA AI");
    }
}