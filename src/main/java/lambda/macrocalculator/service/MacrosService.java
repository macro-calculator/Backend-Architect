package lambda.macrocalculator.service;

import lambda.macrocalculator.model.Macros;
import java.security.Principal;
import java.util.List;

public interface MacrosService
{
	List<Macros> findAll();

	Macros findById(Principal principal);

	Macros update(Macros macros);

	void delete(long id);

	Macros save(Macros macros, Principal principal);
}
