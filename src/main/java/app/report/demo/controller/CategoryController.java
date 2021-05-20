package app.report.demo.controller;

import app.report.demo.entity.User;
import app.report.demo.payload.ApiResponse;
import app.report.demo.payload.CategoryDto;
import app.report.demo.security.CurrentUser;
import app.report.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping
    public HttpEntity<?> saveOrEditCategory(@RequestBody CategoryDto categoryDto) {
        ApiResponse apiResponse = categoryService.saveOrEditCategory(categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

//    @GetMapping("/{id}")
//    public HttpEntity<?> getCategory(@PathVariable UUID id) {
//        ApiResponse apiResponse = categoryService.getCategory(id);
//        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
//    }


    @GetMapping("/getAllCategory")
    public HttpEntity<?> getAllCategory(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                        @CurrentUser User user) {
        ApiResponse apiResponse = categoryService.getAllCategory(page, size, user);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/remove/{id}")
    public HttpEntity<?> deleteCategory(@PathVariable UUID id) {
        ApiResponse apiResponse = categoryService.deleteCategory(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
