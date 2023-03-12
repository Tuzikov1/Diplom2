package test.data;

import site.stellarburgers.steps.ApiRegister;

import java.util.ArrayList;
import java.util.Random;

public class Util {
    //Спорный момент,конечно, я хотел убрать все эти элементы из каждого класса,
    //избавиться от дублирования кода, придумал только такую реализацию, хотя и понимаю,
// что это не целевое использование наследования
    protected String email = "family" + new Random().nextInt(999) + "@mail.ru";
    protected String password = "Password" + new Random().nextInt(999);
    protected String name = "Ivan Petrov" + new Random().nextInt(999);
    protected String token = ""; // С таким значение для метода After, где пользователь не создается, но After должен отработать
    protected ApiRegister step = new ApiRegister();
    protected String ingredient = "61c0c5a71d1f82001bdaaa6c";
    protected ArrayList<String> ingredients = new ArrayList<>();
}
