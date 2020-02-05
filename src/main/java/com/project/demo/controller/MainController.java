package com.project.demo.controller;

import com.project.demo.entities.Categories;
import com.project.demo.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @GetMapping(path="/")
    public String index(ModelMap model){
//        categoriesRepository.save(new Categories(null, "Copter"));//Checking
        List<Categories> categories = categoriesRepository.findAll();
//        for(Categories c : categories)
//            System.out.println(c.getName());
        model.addAttribute("categories", categories);
        return "index";
    }

    @PostMapping(path = "/addCategory")
    public String addCategory(@RequestParam(name = "category") String category){

        categoriesRepository.save(new Categories(null, category));
        return "redirect:/";
    }

    @GetMapping(path = "/edit/{id}")
    public String editCategoryPage(ModelMap model, @PathVariable(name = "id") Long id){
        Optional<Categories> category = categoriesRepository.findById(id);
        model.addAttribute("category", category.orElse(new Categories(null,"No name")));
        return "editCategory";
    }

    @PostMapping(path = "/editCategory")
    public String editCategory(@RequestParam(name = "id") Long id,
                               @RequestParam(name = "category") String category){

        Optional<Categories> editCategory = categoriesRepository.findById(id);

        //editCategory.get().setName(category);
        //categoriesRepository.save(editCategory);

        //categoriesRepository.updateCategory(category, id);

        if(editCategory.isPresent()){
            Categories categoryOzgert = editCategory.get();
            categoryOzgert.setName(category);
            categoriesRepository.save(categoryOzgert);
        }
        return "redirect:/";
    }
}
