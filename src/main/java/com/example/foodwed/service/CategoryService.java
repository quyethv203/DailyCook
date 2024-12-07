package com.example.foodwed.service;

import com.example.foodwed.dto.response.PaginatedResponse;
import com.example.foodwed.entity.Category;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.repository.CategoryReponsitory;
import com.example.foodwed.repository.RecipeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryReponsitory categoryReponsitory;
    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    public Category create(String name){
        Category category = new Category();
        if(categoryReponsitory.existsByName(name)){
            throw new Appexception(ErrorCode.CATEGORY_EXITED);
        }
        category.setName(name);
        return categoryReponsitory.save(category);
    }


    @Transactional
    public void delete(String id) {
        // Kiểm tra xem category có tồn tại không
        if (!categoryReponsitory.existsById(id)) {
            throw new Appexception(ErrorCode.CATEGORY_NOT_EXITED);
        }

        // Xóa tất cả các bản ghi trong bảng recipe_category có liên quan đến categoryId
        recipeCategoryRepository.deleteByCategoryId(id);

        // Xóa bản ghi trong bảng category
        categoryReponsitory.deleteById(id);
    }


    public Category update(String id, String name) {


        // Lấy category từ cơ sở dữ liệu
        Category category = categoryReponsitory.findById(id)
                .orElseThrow(() -> new Appexception(ErrorCode.CATEGORY_NOT_EXITED));

        // Cập nhật tên mới
        category.setName(name);

        // Lưu lại category đã cập nhật
        return categoryReponsitory.save(category);
    }
    public PaginatedResponse<Category> getAllPaginated(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryReponsitory.findAll(pageable);

        // Trả về DTO chứa dữ liệu phân trang
        return new PaginatedResponse<Category>(
                categoryPage.getContent(),
                categoryPage.getNumber(),
                categoryPage.getSize(),
                categoryPage.getTotalElements(),
                categoryPage.getTotalPages(),
                categoryPage.isLast()
        );
    }

}
