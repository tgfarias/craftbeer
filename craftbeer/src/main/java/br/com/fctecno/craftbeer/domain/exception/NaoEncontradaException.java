package br.com.fctecno.craftbeer.domain.exception;

public class NaoEncontradaException extends ValidacaoException {

	private static final long serialVersionUID = 1L;
	
	public NaoEncontradaException(String message) {
		super(message);
	}

}
