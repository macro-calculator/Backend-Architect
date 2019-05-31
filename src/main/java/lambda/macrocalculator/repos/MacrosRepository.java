package lambda.macrocalculator.repos;

import lambda.macrocalculator.model.Macros;
import org.springframework.data.repository.CrudRepository;

import javax.crypto.Mac;

public interface MacrosRepository extends CrudRepository<Macros, Long>
{

}

