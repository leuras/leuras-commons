package br.com.leuras.commons.exception;

public class ExportacaoException extends Exception {

    private static final long serialVersionUID = 510924580440367490L;

    public ExportacaoException() {
        super();
    }

    public ExportacaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExportacaoException(String message) {
        super(message);
    }

    public ExportacaoException(Throwable cause) {
        super(cause);
    }
    
}
