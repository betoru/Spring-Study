package hello.springmvc.ch6.basic.request;


import hello.springmvc.ch6.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Spring MVC Chapter 6 - 기본기능
 * 6. HTTP 요청 파라미터 - 쿼리 파라미터, HTML Form
 * 7. HTTP 요청 파라미터 - @RequestParam
 * 8. HTTP 요청 파라미터 - @ModelAttribute
 */
@Slf4j
@Controller
public class RequestParamController {

    /*
        반환타입없이 응답값에 데이터를 바인딩하면 viewTemplate 조회 x
        @RequestMapping 사용으로 호출 메서드타입을 따로 지정하지 않았으니
        모든 타입의 호출 메서드가 동작한다.
        get 방식으로 호출해보고
        html-form 을 이용해서 post method 2가지 방식로 호출하여 확인해보자.

     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username ={}, age={}", username, age);
        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age) {
        log.info("username={}, age={}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-required")
    public String requestRequiredParam(@RequestParam(required = true, defaultValue = "guest") String username,
                                       @RequestParam(required = true, defaultValue = "10") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("request-map")
    public String requestMapParam(@RequestParam Map<String, Objects> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-multi-map")
    public String requestMultiMapParam(@RequestParam MultiValueMap<String, Objects> multiValueMap) {
        log.info("username={}, age={}", multiValueMap.get("username"), multiValueMap.get("age"));
        return "ok";
    }

    /*
        @ModelAttribute 사용
        참고 : model.addAttribute(helloData) 코드도 함께 자동 적용됨.
        스프링MVC는  @ModelAttribute 가 있으면 다음을 실행한다.
        1. 객체를(HelloData) 생성한다.
        2. 요청 파라미터의 이름으로 객체의(HelloData) 프로퍼티를 찾는다.(=mapping)
        3. 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 입력(바인딩) 한다.
        예) 파라미터 이름이  username 이면  setUsername()  메서드를 찾아서 호출하면서 값을 입력한다.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /*
        @ModelAttribute *** 생략 가능 ***
        생략 시 적용되는 타입의 조건
        @RequestParam => String, int 같은 단순 타입의 경우
        @ModelAttribute => argument resolver 로 지정해둔 타입 외의 경우
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttribute(HelloData helloData) {
        log.info("username={},age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}

