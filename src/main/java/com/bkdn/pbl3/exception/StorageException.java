package com.bkdn.pbl3.exception;

public class StorageException extends RuntimeException{
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String failed_to_store_file, Exception e) {
        super(failed_to_store_file, e);
    }
}
