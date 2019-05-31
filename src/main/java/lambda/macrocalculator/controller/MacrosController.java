package lambda.macrocalculator.controller;

import io.swagger.annotations.ApiOperation;
import lambda.macrocalculator.exception.ResourceNotFoundException;
import lambda.macrocalculator.model.Macros;
import lambda.macrocalculator.service.MacrosService;
import lambda.macrocalculator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/macros")
public class MacrosController
{
	@Autowired
	private MacrosService macrosService;

	@Autowired
	private UserService userService;

	@ApiOperation("Returns macros based off of current user")
	@GetMapping(value = "/getmacros", produces = "application/json")
	public ResponseEntity<?> findMacros(Principal principal) throws ResourceNotFoundException
	{

		Macros currentMacros = macrosService.findById(principal);

		return new ResponseEntity<>(currentMacros, HttpStatus.OK);
	}

	@ApiOperation("Updates meal plan and macros")
	@PutMapping(value = "updatemeals", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateMeals(Principal principal, @RequestBody Macros updateMacros) throws ResourceNotFoundException
	{
		macrosService.update(updateMacros, principal);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
