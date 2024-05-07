package com.calyrsoft.budgetapp

class Recipe {
    private val ingredients = mutableListOf<String>()

    fun addIngredient(name: String) {
        ingredients.add(name)
    }

    fun getIngredients(): List<String> {
        return ingredients
    }
}