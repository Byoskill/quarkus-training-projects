package com.byoskill.domain.common;

import io.smallrye.mutiny.Uni;

public interface ChangeNameOperation<T extends HasName> {
    Uni<T> changeName(T entityToBeUpdated, String newName);
}
