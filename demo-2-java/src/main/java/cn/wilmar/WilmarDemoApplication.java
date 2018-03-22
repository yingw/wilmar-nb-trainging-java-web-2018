package cn.wilmar;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@RepositoryRestResource
interface UserRepository extends JpaRepository<User, Long> {

}

@SpringBootApplication
public class WilmarDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WilmarDemoApplication.class, args);
    }
}

@Controller
class HelloController {
final UserRepository userRepository;

    HelloController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")

    public String index() {
        return "index";
    }

    @GetMapping("/pages/{path}.html")
    public String page(@PathVariable("path") String path) {
        return "pages/" + path;
    }

    @GetMapping("/users")
    public ModelAndView users() {
        return new ModelAndView("user/userList", "users", userRepository.findAll());
    }

    @GetMapping("/users/new")
    public ModelAndView newUser() {
        return new ModelAndView("user/userForm", "user", new User());
    }

    @PostMapping("/users")
    public String save(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }
}

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
class User {
    @Id @GeneratedValue
    Long id;

    @NonNull
    String username;

    @NonNull
    String fullname;

    @NonNull
    String email;
}

