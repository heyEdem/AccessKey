package com.amalitech.AccessKey.controller;

import com.amalitech.AccessKey.dto.GetAccessKeyProjection;
import com.amalitech.AccessKey.service.AccessKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class KeyRestController {
    private final AccessKeyService accessKeyService;
//
//    @GetMapping("/home")
//    public String showHomePage(Model model,@RequestParam(defaultValue = "0") int page,
//                               @RequestParam(defaultValue = "100") int size,
//                               Principal principal) {
//        Page<GetAccessKeyProjection> accessKeys = accessKeyService.getAllUserKeys(page,size,principal);
//        model.addAttribute("accessKeys", accessKeys);
//        return "home";
//    }
}
