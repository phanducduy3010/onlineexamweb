package com.ngovangiang.onlineexam.exception;

public class ResourceNotFoundException extends RuntimeException {

    public <T, ID> ResourceNotFoundException(ID id, Class<T> resourceType) {
        super(resourceType.getSimpleName() + " with id " + id + " not exists!");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
