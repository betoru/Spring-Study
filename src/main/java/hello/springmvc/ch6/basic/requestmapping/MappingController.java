package hello.springmvc.ch6.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Spring MVC Chapter 6 - 기본기능
 * 3. 요청 매핑
 */
@Slf4j
@RestController
public class MappingController {
    /*
        @RestController
        @Controller 는 반환 값이  String 이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다.
        @RestController 는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다.
        따라서 실행 결과로 ok 메세지를 받을 수 있다.
            (*@ResponseBody 와 관련이 있음)
     */

    /*
        기본요청
        둘다 허용 /hello-basic, /hello-basic/
        HTTP 메서드 모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE
            (*요청메서드를 구체화 하지 않았기 때문)
     */
    @RequestMapping("/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /*
        편리한 축약 애노테이션 (코드보기)
        @GetMapping
        @PostMapping
        @PutMapping
        @DeleteMapping
        @PatchMapping
            (*이놈들 내부코드 뜯어보면 method 방식 지정해주고 있음. 별거없움)
     */
    @GetMapping("/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }

    /*
        PathVariable
        @PathVariable("userId") String userId -> @PathVariable userId
        위와 같이 변수명을 동일하게 사용하게 되면 pathVariable 의 변수명을 명시하지 않아도 된다.
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String userId) {
        log.info("mappingPath userId={}", userId);
        return "ok";
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /*
        특정 파라미터로 조건 매핑
        params="mode",
        params="!mode"
        params="mode=debug"
        params="mode!=debug" (! = )
        params = {"mode=debug","data=good"}
    */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /*
        headers="mode",
        headers="!mode"
        headers="mode=debug"
        headers="mode!=debug" (! = )
            (*postman 에서 header 정보를 담아줘야함)
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeaders() {
        log.info("mappingHeader");
        return "ok";
    }
    /*
        Content-Type 헤더 기반 추가 매핑 Media Type
        consumes="application/json"
        consumes="!application/json"
        consumes="application/*"
        consumes="*\/*"
        MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }
    /*
        consumes = "text/plain"
        consumes = {"text/plain", "application/*"}
        consumes = MediaType.TEXT_PLAIN_VALUE
     */
    @PostMapping(value = "/mapping-consume-v2", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumesV2() {
        log.info("mappingConsumesV2");
        return "ok";
    }

    @PostMapping(value = "/mapping-produces", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
