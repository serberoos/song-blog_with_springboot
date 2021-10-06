package com.song.springboot_blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.song.springboot_blog.dto.ResponseDto;
import com.song.springboot_blog.model.User;
import com.song.springboot_blog.service.UserService;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    /*
     * @Autowired private HttpSession session; // 스프링 컨트롤러에서 기본으로 가지고 있다. 따라서
     * Autowired로 가져올 수 있음.
     */

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
        System.out.println("UserApiController : save 호출됨.");

        // 실제로 DB에 insert를 하고 아래에서 return이 되면 된다.

        userService.회원가입(user); // result = 1 성공 , result = -1 실패
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);// OK is 200
    }

    /*
     * // 이러한 방식은 스프링 전통적인 방식으로 요즘은 스프링 시큐리티를 이용해서 로그인을 구현한다.
     *
     * @PostMapping("api/user/login") public ResponseDto<Integer> login(@RequestBody
     * User user) { //, HttpSession session 파라미터를 추가해 쓸 수도 있다.
     * System.out.println("User ApiController : login 호출됨"); User principal =
     * userService.로그인(user); // principal 접근 주체
     *
     * if (principal != null) { session.setAttribute("principal",principal); }
     * return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); }
     */

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user) { // password, email
        System.out.println("UserApiController : update 호출됨.");

        userService.회원정보수정(user); // result = 1 성공 , result = -1 실패
        //여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됬음.
        //하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션 값을 변경해줄 것임.

        //꼭 DB값이 변경된 후 세션 값을 변경 해야 한다.
        //세션 등록 Authentication 객체가 날려지면서 세션이 등록된다.
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);// OK is 200
    }

}
