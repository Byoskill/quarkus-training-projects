package com.byoskill.domain.common.model;

import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;

@Priority(10)
@Decorator
public class ChangeNameOperationDecorator implements ChangeNameOperation<HasName> {

    @Inject
    @Any
    @Delegate
    ChangeNameOperation<HasName> delegate;

    @Override
    public HasName changeName(final HasName entityToBeUpdated, final String newName) {
        if (3 > newName.length()) throw new IllegalArgumentException("Name must be at least 3 characters long");
        return delegate.changeName(entityToBeUpdated, newName);
    }
}