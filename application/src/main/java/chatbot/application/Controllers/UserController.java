package chatbot.application.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chatbot.application.Entities.User;
import chatbot.application.Services.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST mapping for creating a new user
    @PostMapping("/create")
    public String createUser(@RequestParam String username, @RequestParam String password, Model model) {
        userService.createUser(username, password);
        model.addAttribute("message", "User created succesfully");
        return "login";
    }

    // GET mapping for fetching a user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id).orElse(null);
    }

    // Serve the user creation form (GET request)
    @GetMapping("/create")
    public String showCreateUserForm() {
        return "createUser"; // This corresponds to createUser.html in the templates folder
    }

}


