package lambda.macrocalculator.controller;

import io.swagger.annotations.ApiOperation;
import lambda.macrocalculator.model.Macros;
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

	@ApiOperation(value = "Returns currently authenticated user info")
	//localhost:2019/users/current
	@GetMapping(value = "/current",
				produces = {"application/json"})
	public ResponseEntity<?> getCurrentUser(Principal principal)
	{
		return new ResponseEntity<>(userService.getCurrentUser(principal), HttpStatus.OK);
	}

	@ApiOperation("Creates a new user")
	//localhost:2019/users/create
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addUser(@Valid @RequestBody User newUser)
	{
		userService.save(newUser);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	@ApiOperation("Updates the currently authenticated user")
	@PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateUser(@RequestBody User updatedUser, Principal principal)
	{
		userService.update(updatedUser, principal);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
