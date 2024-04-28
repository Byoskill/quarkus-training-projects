package com.byoskill.frontend.controllers;

import com.byoskill.domain.adoption.model.Monster;

import java.util.List;

public record MonsterView(List<Monster> monsters) {
}