package hello.springmvc.ch6.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.ch6.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.chrono.HijrahDate;

/**
 * Spring MVC Chapter 6 - 기본기능
 * 10. HTTP 요청 메시지 - JSON
 */
@Slf4j
@Controller
public class RequestBodyJsonController {
    /*
        아래와 같이 json 데이터로 클라이언트에서 보낼거야.
        {"username":"hello", "age":20}
        content-type: application/json
        그걸 읽어서 객체에 담아 보자구
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /*
        HttpServletRequest를 사용해서 직접 HTTP 메시지 바디에서 데이터를 읽어와서, 문자로 변환한다.
        문자로 된 JSON 데이터를 Jackson 라이브러리인  objectMapper 를 사용해서 자바 객체로 변환한다
     */
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        /*
            결과
            messageBody={
                "username": "hello",
                "age": 20
            }
         */

        //json 데이터를 읽어서 객체에 담아보자
        //objectMapper 객체의 readValue 메서드를 호출해서 args 로 messageBody 와 타겟 클래스를 받자구
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("data={}", data); //결과 data=HelloData(username=hello, age=20)
        log.info("username={},age={}", data.getUsername(), data.getAge()); //결과 username=hello,age=20
        response.getWriter().write("ok");
    }

    /*
        위의 방식보다 더 간략하게 해보자고
        @ResponseBody 와 @RequestBody 를 이용하자.
        편리한데 readValue 하는거 쫌 그래 v3 보자
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={},age={}", data.getUsername(), data.getAge()); //결과 username=hello,age=20
        return "ok";
    }

    /*
        위의 과정 중 readValue 부분 짱나니까 애초에 객체 파라미터를 받아버려!
        HttpEntity, @RequestBody 를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을
        우리가 원하는 문자나 객체 등으로 변환해준다.
        HTTP 메시지 컨버터는 문자 뿐만 아니라 JSON도 객체로 변환해주는데,
        V2에서 했던 작업을 대신 처리해준다
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) {
        log.info("username={},age={}", data.getUsername(), data.getAge()); //결과 username=hello,age=20
        return "ok";
    }
    /*
        중요
        @RequestBody는 생략 불가능
        스프링은 @ModelAttribute, @RequestParam 과 같은 해당 애노테이션을 생략시 다음과 같은 규칙을 적용한다.
            String, int, Integer 같은 단순 타입 = @RequestParam
            나머지 = @ModelAttribute(argument resolver 로 지정해둔 타입 외)
        따라서, HelloData 에 @RequestBody 를 생략하면 @ModelAttribute 가 적용되어버린다.
            ex) HelloData data == @ModelAttribute HelloData data
            생략하면 HTTP 메시지 바디가 아니라 요청 파라미터를 처리하게 된다.

        주의
        HTTP 요청시에 content-type이 application/json인지 꼭! 확인해야 한다.
        그래야 JSON을 처리할 수 있는 HTTP 메시지 컨버터가 실행된다.
     */

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();
        log.info("username={},age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    /*
        @ResponseBody 를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣을 수 있음
        HttpEntity 를 사용해도 됨.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data;
    }

    /*
        return type => httpEntity 로도 바꿔보자.
     */
    @PostMapping("/request-body-resp-entity")
    public HttpEntity<HelloData> requestBodyJsonV6(HttpEntity<HelloData> httpEntity) {
        HelloData messageBody = httpEntity.getBody();
        log.info("username={}, age={}", messageBody.getUsername(), messageBody.getAge());
        return new HttpEntity<HelloData>(messageBody);
    }

    /*
        entity 를 Response 와 Request 로 바꿔보자
     */
    @PostMapping("/request-body-entity")
    public ResponseEntity<HelloData> requestBodyJsonV6(RequestEntity<HelloData> requestEntity) {
        HelloData messageBody = requestEntity.getBody();
        log.info("username={}, age={}", messageBody.getUsername(), messageBody.getAge());
        return new ResponseEntity<>(messageBody, HttpStatus.CREATED);
    }
}
