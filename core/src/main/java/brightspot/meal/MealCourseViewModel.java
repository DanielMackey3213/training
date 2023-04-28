package brightspot.meal;

import brightspot.util.RichTextUtils;
import com.psddev.cms.view.ViewModel;
import com.psddev.styleguide.meal.MealCourseView;
import com.psddev.styleguide.meal.MealCourseViewDishesField;
import com.psddev.styleguide.meal.MealCourseViewNameField;
import com.psddev.styleguide.meal.MealCourseViewSummaryField;

public class MealCourseViewModel extends ViewModel<MealCourse> implements MealCourseView {

    @Override
    public Iterable<? extends MealCourseViewDishesField> getDishes() {
        return createViews(MealCourseViewDishesField.class, model.getDishes());
    }

    @Override
    public Iterable<? extends MealCourseViewNameField> getName() {
        return RichTextUtils.buildHtml(
            model,
            MealCourse::getName,
            e -> createView(MealCourseViewNameField.class, e));
    }

    @Override
    public Iterable<? extends MealCourseViewSummaryField> getSummary() {
        return RichTextUtils.buildHtml(
            model,
            MealCourse::getSummary,
            e -> createView(MealCourseViewSummaryField.class, e));
    }
}
