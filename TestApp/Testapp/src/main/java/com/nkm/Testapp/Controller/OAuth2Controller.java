//package com.nkm.Testapp.Controller;
//import com.nkm.Testapp.Entity.User;
//import com.nkm.Testapp.Repository.UserRepository;
//
//
//@RestController
//@RequestMapping("/api/oauth2")
//public class OAuth2Controller {
//
//    private final UserRepository userRepository;
//
//    public OAuth2Controller(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @GetMapping("/success")
//    public String successPage(OAuth2AuthenticationToken authToken) {
//        OAuth2User user = authToken.getPrincipal();
//        String email = user.getAttribute("email");
//        String name = user.getAttribute("name");
//
//        // Register or update user in DB
//        User existing = userRepository.findAll().stream()
//                .filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
//
//        if (existing == null) {
//            User newUser = new User();
//            newUser.setEmail(email);
//            newUser.setName(name);
//            newUser.setRole("ROLE_USER"); // default role
//            newUser.setPassword(""); // or a random value
//            userRepository.save(newUser);
//        }
//
//        return "Logged in with Google: " + email;
//    }
//}
//
