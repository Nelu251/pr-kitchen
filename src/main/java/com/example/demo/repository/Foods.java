package com.example.demo.repository;

import com.example.demo.domain.model.Food;
import com.example.demo.domain.model.enums.CookingApparatus;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j
public class Foods {

    private static final List<Food> foods = new ArrayList<>();

    static {
        foods.add(new Food(1, "pizza", 5, 2, CookingApparatus.OVEN));
        foods.add(new Food(2, "salad", 3, 1, CookingApparatus.STOVE));
        foods.add(new Food(3, "Scallop Sashimi with Meyer Lemon Confit", 1, 3, null));
        foods.add(new Food(4, "Island Duck with Mulberry Mustard", 6, 3, CookingApparatus.OVEN));
        foods.add(new Food(5, "Waffles", 2, 1, CookingApparatus.STOVE));
        foods.add(new Food(6, "Aubergine", 3, 1, null));
        foods.add(new Food(7, "Lasagna", 4, 2, CookingApparatus.OVEN));
        foods.add(new Food(8, "Gyros", 3, 1, null));
    }

    public static Food getFoodById(int id) {
        return foods.stream().filter(food -> food.getId() == id).findFirst().orElseThrow(ExceptionInInitializerError::new);
    }

    public int getTheHighestPreparationTimeFood(List<Integer> list) {
//        int max = 0;
//        for (Food food : foods) {
//            if (food.getPreparationTime() > max) {
//                max = food.getPreparationTime();
//            }
//        }
//        return max;
        return list.stream().max(Integer::compare).get();
    }

}
