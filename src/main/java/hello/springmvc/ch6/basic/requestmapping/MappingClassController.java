package hello.springmvc.ch6.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    @GetMapping
    public String users() {
        return "getUsers";
    }

    @PostMapping
    public String addUsers() {
        return "addUsers";
    }

    @GetMapping("/{userId}")
    public String getUserId(@PathVariable("userId") String userId) {
        return "get path userId =" + userId ;
    }
    @PostMapping("/{userId}")
    public String addUserId(@PathVariable String userId) {
        return "add path userId=" + userId ;
    }
    @PatchMapping("/{userId}")
    public String updateUserId(@PathVariable String userId) {
        return "update path userId=" + userId ;
    }
    @DeleteMapping("/{userId}")
    public String deleteUserId(@PathVariable String userId) {
        return "delete path userId=" + userId ;
    }
}
