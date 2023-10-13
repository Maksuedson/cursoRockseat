package br.com.maksuedson.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository repo;

    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody UserModel userModel){
       var user =  this.repo.findByUserName(userModel.getUserName());
       if (user != null){
        System.out.println("Usuárioi ja existe");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
       }

       var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

       userModel.setPassword(passwordHashred);

       var userCreated = this.repo.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
    
}
