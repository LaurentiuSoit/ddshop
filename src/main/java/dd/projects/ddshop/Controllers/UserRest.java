package dd.projects.ddshop.Controllers;

import dd.projects.ddshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class UserRest {
    @Autowired
    UserService userService;

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @PostMapping(path = "/login")
//    public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap){
//        try{
//            return userService.login(requestMap)
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return new ResponseEntity<String>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
