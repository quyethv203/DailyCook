package com.example.foodwed.controller;

import com.example.foodwed.dto.response.ApiRespone;
import com.example.foodwed.dto.response.RecipeResponse;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.service.RandomRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suggestion")
public class SuggestionController {
    @Autowired
    private RandomRecipeService randomRecipeService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getSuggestion(@PathVariable String id){
        if (id.isEmpty()){
            throw new Appexception(ErrorCode.PARAM_ERROR);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<List<RecipeResponse>>("success","200","ok",
                        randomRecipeService.getSuggestion(id)
                        ));
    }

}
