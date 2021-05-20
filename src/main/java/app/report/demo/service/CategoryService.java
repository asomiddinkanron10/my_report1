package app.report.demo.service;

import app.report.demo.entity.Category;
import app.report.demo.entity.User;
import app.report.demo.exception.utils.CommonUtils;
import app.report.demo.payload.ApiResponse;
import app.report.demo.payload.CategoryDto;
import app.report.demo.repository.CategoryRepository;
import app.report.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;

    public ApiResponse saveOrEditCategory(CategoryDto categoryDto) {
        try {
            Category category = new Category();
            if (categoryDto.getId() != null)
                category = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new ResourceNotFoundException("getCategory"));
            if (categoryDto.getUserId() != null)
                category.setUser(userRepository.findById(categoryDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("getUser")));
            category.setName(categoryDto.getName() == null ? category.getName() : categoryDto.getName());
            category.setDescription(categoryDto.getDescription() == null ? category.getDescription() : categoryDto.getDescription());
            categoryRepository.save(category);
            return new ApiResponse("Category added", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse getAllCategory(int page, int size, User user) {
        try {
            return new ApiResponse(
                    "Success full",
                    true,
                    categoryRepository.findAllByUser(user, CommonUtils.getPageableById(page, size)
                    )
            );
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse deleteCategory(UUID id) {
        try {
            categoryRepository.deleteById(id);
            return new ApiResponse("Success full delete", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }
}

//    public ApiResponse getCategory(UUID id) {
//        try {
//            Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getCategory"));
//            return new ApiResponse("Success", true, (category));
//        } catch (Exception e) {
//            return new ApiResponse("Error", false);
//        }
//    }


//**Category all get qilish dtoga yasab olish**//
//    public CategoryDto getCategoryDto(Category category) {
//        return new CategoryDto(
//                category.getId(),
//                category.getUser().getId(),
//                category.getName(),
//                category.getDescription(),
//                new UserDto(category.getUser().getId(), category.getUser().getFirstName(), category.getUser().getLastName(), null, null, true, null, null, null, null)
//        );
//    }
