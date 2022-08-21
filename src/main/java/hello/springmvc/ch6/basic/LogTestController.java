package hello.springmvc.ch6.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC Chapter 6 - 기본기능
 * 2. 로깅 간단히 알아보기
 */
//@Slf4j
@RestController
public class LogTestController {

    /*
        로그 선언
        private Logger log = LoggerFactory.getLogger(getClass());
        private static final Logger log = LoggerFactory.getLogger(Xxx.class)
        @Slf4j : 롬복 사용 가능

        테스트
        로그가 출력되는 포멧 확인
        시간, 로그 레벨, 프로세스 ID, 쓰레드 명, 클래스명, 로그 메시지
        로그 레벨 설정을 변경해서 출력 결과를 보자.
        LEVEL:  TRACE > DEBUG > INFO > WARN > ERROR
        개발 서버는 debug 출력
        운영 서버는 info 출력
     */
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";
        log.trace("trace log={}", name);

        /*
        log.debug("data="+data)
        로그 출력 레벨을 info로 설정해도 해당 코드에 있는 "data="+data가 실제 실행이 되어 버린다.
        결과적으로 문자 더하기 연산이 발생한다.

        log.debug("data={}", data)
        로그 출력 레벨을 info로 설정하면 아무일도 발생하지 않는다. 따라서 앞과 같은 의미없는 연산이
        발생하지 않는다.

        아래의 방식으로 사용하면 안돼. 연산이 먼저 실행되어버렷!
        */
        log.debug("String concat log=" + name);
        return "ok";
    }
    /*
        로그레벨을 설정하고 위의 내용을 확인해봐.
        로그 레벨 설정 => application.properties
        #전체 로그 레벨 설정(기본 info)
        logging.level.root=info
        #hello.springmvc 패키지와 그 하위 로그 레벨 설정
        logging.level.hello.springmvc=debug
     */
}
