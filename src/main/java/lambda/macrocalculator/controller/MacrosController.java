package lambda.macrocalculator.controller;

import io.swagger.annotations.ApiOperation;
import lambda.macrocalculator.model.Macros;
import lambda.macrocalculator.model.User;
import lambda.macrocalculator.service.MacrosService;
import lambda.macrocalculator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/macros")
public class MacrosController
{
	@Autowired
	private MacrosService macrosService;

	@Autowired
	private UserService userService;

	@ApiOperation("Takes in inches and returns macros calculation")
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createMacros(Principal principal, @Valid @RequestBody Macros newMacros)
	{
		macrosService.save(newMacros, principal);
		return new ResponseEntity<>(findMacros(principal).getBody(), HttpStatus.CREATED);
	}

	public ResponseEntity<?> findMacros(Principal principal)
	{

		Macros currentMacros = macrosService.findById(principal);

		return new ResponseEntity<>(currentMacros, HttpStatus.OK);
	}
}
