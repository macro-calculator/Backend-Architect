package lambda.macrocalculator.controller;

import lambda.macrocalculator.model.User;
import lambda.macrocalculator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController
{
	@Autowired
	private UserService userService;

	//returns the current users info
	//localhost:2019/users/current
	@GetMapping(value = "/current",
				produces = {"application/json"})
	public ResponseEntity<?> getCurrentUser(Principal principal)
	{
		return new ResponseEntity<>(userService.getCurrentUser(principal), HttpStatus.OK);
	}


		//localhost:2019/users/create
	@PostMapping(value = "/create", consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> addUser(@Valid @RequestBody User newUser)
	{
		userService.save(newUser);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}
}
