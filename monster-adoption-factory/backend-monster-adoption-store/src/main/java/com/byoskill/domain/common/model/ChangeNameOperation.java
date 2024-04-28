package com.byoskill.domain.common.model;

import io.smallrye.mutiny.Uni;

public interface ChangeNameOperation<T extends HasName> {
    Uni<T> changeName(T entityToBeUpdated, String newName);
}
