package pt.ipleiria.estg.ei.dae.projeto.projetopmei.exceptions;

import java.util.stream.Collectors;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

public class MyConstraintViolationException extends Exception {
	public MyConstraintViolationException(ConstraintViolationException e) {
		super(getConstraintViolationMessages(e));
	}

	private static String
	getConstraintViolationMessages(ConstraintViolationException e) {
		return e.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.joining("; "));
	}
}
