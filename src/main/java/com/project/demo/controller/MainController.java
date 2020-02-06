package com.project.demo.controller;

import com.project.demo.entities.Categories;
import com.project.demo.entities.Toys;
import com.project.demo.repositories.CategoriesRepository;
import com.project.demo.repositories.ToysRepository;
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

    @Autowired
    private ToysRepository toysRepository;

    @GetMapping(path="/")
    public String index(ModelMap model){
//        categoriesRepository.save(new Categories(null, "Copter"));//Checking
        List<Categories> categories = categoriesRepository.findAll();
//        for(Categories c : categories)
//            System.out.println(c.getName());
        model.addAttribute("categories", categories);
        List<Toys> toys = toysRepository.findAll();
        model.addAttribute("toys", toys);

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

    @PostMapping(path = "/addToy")
    public String addToy(@RequestParam(name = "toy") String toy,
//                         @RequestParam(name = "toyCategory") String toyCategory,
                         @RequestParam(name = "price") int price,
                         @RequestParam(name = "toyCategoryId") Long toyCategoryId){

        Optional<Categories> neededCategory = categoriesRepository.findById(toyCategoryId);
        if(neededCategory.isPresent()){
            Categories category = neededCategory.get();
            Toys newToy = new Toys(null, toy, price, category);
            toysRepository.save(newToy);
        }

//        Toys newToy = new Toys(null, toy, price, categoriesRepository.findById(toyCategoryId).get());
//        toysRepository.save(newToy);

        return "redirect:/";
    }

    @GetMapping(path = "/editToyPage/{id}")
    public String editToyPage(ModelMap model, @PathVariable(name = "id") Long id){
        Optional<Toys> toy = toysRepository.findById(id);
        model.addAttribute("toy", toy.orElse(new Toys(null,"No name",0,null)));

        List<Categories> categories = categoriesRepository.findAll();
        //model.addAttribute("category", category.orElse(new Categories(null,"No name")));
        model.addAttribute("categories", categories);

        return "editToyPage";
    }

    @PostMapping(path = "/editToy")
    public String editToy(@RequestParam(name = "id") Long id,
                          @RequestParam(name = "toy") String toy,
                          @RequestParam(name = "toyCategoryId") Long toyCategoryId,
                          @RequestParam(name = "price") double price){

        Optional<Categories> category = categoriesRepository.findById(toyCategoryId);

        Optional<Toys> neededToy = toysRepository.findById(id);
        if(neededToy.isPresent()){
            neededToy.get().setName(toy);
            neededToy.get().setCategories(category.get());
            neededToy.get().setPrice(price);

            toysRepository.save(neededToy.get());
        }


        return "redirect:/";
    }
}
