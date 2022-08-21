package hello.springmvc.ch6.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * Spring MVC Chapter 6 - 기본기능
 * 9. HTTP 요청 메시지 - 단순 텍스트
 */
@Slf4j
@Controller
public class RequestBodyStringController {

    /*
        요청 파라미터와 다르게
        http 메시지 바디를 통해 데이터가 넘어오는 경우
        @RequestParam, @ModelAttribute 를 사용할 수 없다.
            (*HTTP 요청 파라미터 편 참고)
     */
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //HTTP 메시지 바디의 데이터를 InputStream 을 이용해서 직접 읽을 수 있다.
        ServletInputStream inputStream = request.getInputStream();
        //ServletInputStream 을 통해 읽어들인 이진데이터를 String 타입으로 변환
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        response.getWriter().write("ok");
    }

    /*
        메시지 바디를 InputStream 을 통해 바로 받아 문자열로 변환한다.
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    /*
        위의 프로세스를 보다 간소화할 수 있는 방법 - HttpEntity
        HttpEntity: HTTP header, body 정보를 편리하게 조회
        - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
        - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

        응답에서도 HttpEntity 사용 가능
        - 메시지 바디 정보 직접 반환(view 조회X)
        - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

        HttpEntity 를 상속받은 다음 객체들도 같은 기능을 제공한다.
        RequestEntity
            HttpMethod, url 정보가 추가, 요청에서 사용
        ResponseEntity
            HTTP 상태 코드 설정 가능, 응답에서 사용
            return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED)
        참고
        스프링MVC 내부에서 HTTP 메시지 바디를 읽어서 문자나 객체로 변환해서 전달해주는데,
        이때 HTTP 메시지 컨버터( HttpMessageConverter )라는 기능을 사용한다.
        이것은 조금 뒤에 HTTP 메시지 컨버터에서 자세히 설명한다
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);
        return new HttpEntity<>("ok");
    }

    /*
        requestEntity=<POST http://localhost:8080/request-body-String-test,{"username":"userA", "age":20},
        [mode:"debug", user-agent:"PostmanRuntime/7.29.2", accept:"*//*",
            postman-token:"1caac3f4-c2b2-4894-ab8b-2d5755407d42", host:"localhost:8080", accept-encoding:"gzip,
            deflate, br", connection:"keep-alive", content-length:"30", Content-Type:"application/json;charset=UTF-8"]>

        response "hello world", status 200 ok
     */
    @PostMapping("/request-body-String-test")
    public ResponseEntity<String> requestBodyStringTest(RequestEntity<String> requestEntity) {
        log.info("requestEntity={}", requestEntity);
        return new ResponseEntity<>("hello world", HttpStatus.OK);
    }

    /*
        @RequestBody
        - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
        - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

        @ResponseBody
        - 메시지 바디 정보 직접 반환(view 조회X)
        - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @ResponseBody
    @PostMapping("request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        log.info("messageBody={}", messageBody);
        return "ok";
    }
}
