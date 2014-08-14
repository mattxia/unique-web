package org.unique.plugin.db.exception;

/**
 * //TODO 更新异常
 * @author rex
 */
@SuppressWarnings("serial")
public class UpdateException extends DBException{

    public UpdateException() {
        super();
    }
    
    public UpdateException(String message) {
        super(message);
    }
    
    public UpdateException(Throwable cause) {
        super(cause);
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
