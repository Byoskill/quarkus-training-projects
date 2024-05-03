package com.byoskill.domain.common;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;

@Priority(10)
@Decorator
public class ChangeNameOperationDecorator implements ChangeNameOperation<HasName> {

    @Inject
    @Any
    @Delegate
    ChangeNameOperation<HasName> delegate;

    @Override
    public Uni<HasName> changeName(final HasName entityToBeUpdated, final String newName) {
        if (3 > newName.length()) throw new WebApplicationException("Name must be at least 3 characters long", 400);
        if (newName.contains(" ")) throw new WebApplicationException("Name must not contain spaces", 400);
        return delegate.changeName(entityToBeUpdated, newName);
    }
}