package br.com.leuras.commons.exception;

public class ExportacaoExcelException extends ExportacaoException {

    private static final long serialVersionUID = -6319172341491762748L;

    public ExportacaoExcelException() {
        super();
    }

    public ExportacaoExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExportacaoExcelException(String message) {
        super(message);
    }

    public ExportacaoExcelException(Throwable cause) {
        super(cause);
    }
    
}
