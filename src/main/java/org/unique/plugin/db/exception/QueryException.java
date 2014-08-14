package org.unique.plugin.db.exception;

/**
 * //TODO 查询异常
 * @author rex
 */
@SuppressWarnings("serial")
public class QueryException extends DBException{

    public QueryException() {
        super();
    }
    
    public QueryException(String message) {
        super(message);
    }
    
    public QueryException(Throwable cause) {
        super(cause);
    }

    public QueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
