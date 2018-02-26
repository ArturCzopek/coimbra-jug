package pl.simplecoding.coimbrajug;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * @Author Artur Czopek
 * @Link https://simplecoding.pl/coimbra-kotlin
 */

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/usersList")
    public List<Map<String, Object>> getUsers(Principal principal) {
        return userService.getAllUsers(principal);
    }

    @GetMapping("/me")
    public User getData(Principal principal) {
        return userService.getData(principal);
    }

    @PostMapping("/action")
    public void giveToken(@RequestParam long toUserId, @RequestParam String tokenType, Principal principal) {
        userService.giveToken(toUserId, TokenType.fromString(tokenType), principal);
    }
}
