package hello.springmvc.ch6.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Spring MVC Chapter 6 - 기본기능
 * 5. HTTP 요청 - 기본, 헤더 조회
 */
@Slf4j
@RestController
public class RequestHeaderController {
    /*
        애노테이션 기반 스프링 컨트롤러
        http 헤더 정보 조회 방법
     */
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String,String> headerMap,
                          @RequestHeader("host") String host,
                          @CookieValue(value = "myCookie", required = false) String cookie) {
        log.info("request={}",request);
        log.info("response={}",response);
        log.info("httpMethod={}",httpMethod);
        log.info("locale={}",locale);
        log.info("headerMap={}",headerMap);
        log.info("host={}",host);
        log.info("cookie={}",cookie);
        return "ok";
    }
}
