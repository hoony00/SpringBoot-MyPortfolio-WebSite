package com.hoon.hoonportfolio.Controller;

import com.hoon.hoonportfolio.CService.EducationService;
import com.hoon.hoonportfolio.CService.CertificationService;
import com.hoon.hoonportfolio.CService.SkillService;
import com.hoon.hoonportfolio.CService.UserService;
import com.hoon.hoonportfolio.DTO.ExplanationRequestDTO;
import com.hoon.hoonportfolio.DTO.IntroduceDTO;
import com.hoon.hoonportfolio.DTO.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SessionAttributes("userExplanation")
@Controller
@Slf4j // 로그를 위한 어노테이션
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final EducationService educationService;

    private final CertificationService certificationService;

    private final SkillService skillService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/") //
    public String index() { // 홈
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            log.info("루트 authentication.getName() : " + authentication.getName());
        }
        return "index";
    }

    @GetMapping("/user/login")
    public String showLoginForm(Model model) {
        // UserDTO 객체를 사용하여 회원가입 폼을 초기화
        model.addAttribute("userDTO", new UserDTO());
        return "user/login";
    }

    @GetMapping("/user/join") // 회원가입 폼을 표시
    public String showRegistrationForm(Model model) {
        // UserDTO 객체를 사용하여 회원가입 폼을 초기화
        model.addAttribute("userDTO", new UserDTO());
        return "user/join";
    }


    // 로그아웃
    @GetMapping("/user/logout")
    public String performLogout(HttpServletRequest request, HttpServletResponse response) {
        // .. perform logout
        log.info("===============> logout");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    @PostMapping("/user/joinSuccess")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 폼 유효성 검사 오류가 있는 경우 회원가입 폼으로 다시 이동
            log.info("회원가입 폼에 유효성 검사 오류가 있습니다.");
            return "user/join";
        }
        try {
            userService.registerNewUser(userDTO, passwordEncoder);
            educationService.saveEducation(userDTO.getEmail());
            certificationService.saveCertification(userDTO.getEmail());
            skillService.saveSkill(userDTO.getEmail());
            // 회원가입 성공 시 로그인 페이지로 리다이렉트
            model.addAttribute("userExplanation", userDTO.getEmail());
            System.out.println("회원가입 성공 -> 이메일 : " + userDTO.getEmail());
            // 회원가입 성공 시 로그인 페이지로 리다이렉트
            return "alert/suc";
        } catch (IllegalStateException e) {
            // 회원가입 중 오류 발생 시 오류 메시지와 함께 회원가입 폼으로 다시 이동
            model.addAttribute("errorMessage", e.getMessage());
            return "user/join";
        }
    }


    // 이름과 자기소개 가져오기
    @GetMapping("/user/getNameAndExplanation/{email}")
    public String getNameAndExplanation(@PathVariable String email, Model model) {
        System.out.println("가져온 이메일: " + email);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            log.info("authentication.getName() : " + authentication.getName());
        }
        model.addAttribute("userExplanation", userService.getNameAndExplanation(email));

        return "layout/newPortfolio";
    }

    @GetMapping("/user/loginAction/{email}")
    public String getSelect(@RequestParam String email, Model model) {
        model.addAttribute("userExplanation", userService.getNameAndExplanation(email));


        return "layout/newPortfolio";
    }


    // 이미지 업로드 API
    @PostMapping("/user/uploadProfileImage")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("email") String email) {
        try {
            if (email == null) {
                throw new IllegalStateException("이메일을 입력해주세요.");
            }
            // MultipartFile을 바이트 배열로 변환하여 서비스로 전달
            byte[] imageBytes = file.getBytes();
            userService.saveProfileImage(email, imageBytes); // 이메일 값을 적절히 전달

            // 이미지 업로드가 성공했을 때, 이미지 URL을 클라이언트로 반환
            String imageUrl = "/user/profileImage/" + email;

            return new ResponseEntity<>(imageUrl, HttpStatus.OK);
        } catch (IOException e) {
            // 파일 업로드 중 오류 발생 시 처리
            e.printStackTrace(); // 오류 처리를 원하는 방식으로 수정
            // 오류 상태를 클라이언트에게 알림
            return new ResponseEntity<>("파일 업로드 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 프로필 사진 조회 API
    @GetMapping("/user/profileImage/{email}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable String email) {
        System.out.println("사진 조회 시작");
        byte[] image = userService.getProfileImage(email);
        if (image != null) {
            System.out.println("사진 조회 성공");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // 이미지 유형에 따라 변경

            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        }
        System.out.println("사진 조회 실패");
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @GetMapping("/user/checkUser")
    public ResponseEntity<String> checkUser(@RequestParam("email") String email) {
        if (userService.isEmailExist(email)) {
            // 사용자가 존재하는 경우, 여기서 추가적인 로직을 수행하거나 다른 API를 호출할 수 있습니다.
            // 예를 들어, 다른 API 호출이 필요한 경우, 그 API를 호출하고 결과를 클라이언트에 반환할 수 있습니다.
            // 이 예제에서는 간단하게 "success" 문자열을 반환합니다.
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            // 사용자가 존재하지 않는 경우
            return new ResponseEntity<>("not_found", HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("user/updateExplanation")
    public ResponseEntity<Map<String, String>> updateExplanation(@RequestBody ExplanationRequestDTO request) {
        Map<String, String> response = new HashMap<>();
        try {
            String email = request.getEmail();
            String explanation = request.getExplanation();

            // 이메일을 사용하여 사용자의 자기소개 업데이트
            userService.updateExplanation(email, explanation);
            response.put("result", "success");
        } catch (Exception e) {
            response.put("result", "error");
        }

        return ResponseEntity.ok(response);
    }


    //객체를 받아서 업데이트
    @PostMapping("user/updateIntroduce")
    public ResponseEntity<Map<String, String>> updateIntroduce(@RequestBody List<IntroduceDTO> introduceDto) {
        Map<String, String> response = new HashMap<>();
        try {
            for (IntroduceDTO dto : introduceDto) {
                System.out.println("dto = " + dto);
                if (dto.getSkillName() == null) {
                    continue;
                }
                System.out.println("introduceDto = " + dto);
                skillService.updateSkill(dto.getSid(), dto.getSkillName());
                certificationService.updateCertification(dto.getCid(), dto.getCertificationName());
                educationService.updateEducation(dto.getEid(), dto.getEducationName());
            }
            response.put("result", "success");
        } catch (Exception e) {
            response.put("result", "error");
        }
        return ResponseEntity.ok(response);
    }


}
