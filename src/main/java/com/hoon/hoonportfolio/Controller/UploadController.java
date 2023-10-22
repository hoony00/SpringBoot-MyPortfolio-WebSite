package com.hoon.hoonportfolio.Controller;

import com.hoon.hoonportfolio.CService.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UploadController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/upload")
    public String uploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("projectId") Long projectId, RedirectAttributes redirectAttributes) throws IOException {
        if (!file.isEmpty()) {
            // 이미지를 업로드하고 데이터베이스에 저장
            byte[] imageBytes = file.getBytes();
            imageService.uploadMainPhoto(projectId, imageBytes); // projectId를 전달
            redirectAttributes.addFlashAttribute("successMessage", "이미지가 업로드되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "파일을 선택해 주세요.");
        }

        return "redirect:/upload";
    }

}
