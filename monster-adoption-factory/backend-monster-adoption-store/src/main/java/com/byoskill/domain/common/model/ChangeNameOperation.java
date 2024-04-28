package com.byoskill.domain.common.model;

public interface ChangeNameOperation<T extends HasName> {
    T changeName(T _newEntity, String newName);
}
