package brightspot.meal;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import brightspot.article.ArticleLead;
import brightspot.cascading.CascadingPageElements;
import brightspot.cascading.CascadingPageElementsModification;
import brightspot.image.WebImageAsset;
import brightspot.module.recipe.RecipeModulePlacementInline;
import brightspot.page.Page;
import brightspot.permalink.AbstractPermalinkRule;
import brightspot.promo.page.PagePromotableWithOverrides;
import brightspot.promo.page.PagePromotableWithOverridesData;
import brightspot.recipe.HasRecipes;
import brightspot.recipe.Recipe;
import brightspot.rte.MediumRichTextToolbar;
import brightspot.rte.TinyRichTextToolbar;
import brightspot.section.HasSectionData;
import brightspot.section.HasSectionWithField;
import brightspot.section.HasSectionWithFieldData;
import brightspot.section.Section;
import brightspot.section.SectionPrefixPermalinkRule;
import brightspot.seo.RobotsValue;
import brightspot.seo.SeoWithFields;
import brightspot.seo.SeoWithFieldsData;
import brightspot.share.Shareable;
import brightspot.share.ShareableData;
import brightspot.site.DefaultSiteMapItem;
import brightspot.tag.HasTagsData;
import brightspot.tag.HasTagsWithField;
import brightspot.tag.HasTagsWithFieldData;
import brightspot.tag.Tag;
import brightspot.urlslug.HasUrlSlugData;
import brightspot.urlslug.HasUrlSlugWithField;
import brightspot.urlslug.HasUrlSlugWithFieldData;
import brightspot.util.RichTextUtils;
import com.psddev.cms.db.Content;
import com.psddev.cms.db.Site;
import com.psddev.cms.db.ToolUi;
import com.psddev.cms.ui.form.DynamicPlaceholderMethod;
import com.psddev.dari.db.Recordable;
import com.psddev.dari.util.Utils;
import com.psddev.sitemap.SiteMapEntry;
import org.apache.commons.lang3.StringUtils;

public class Meal extends Content implements CascadingPageElements, DefaultSiteMapItem,
    HasRecipes, HasSectionWithField, HasTagsWithField,
    HasUrlSlugWithField, Page, PagePromotableWithOverrides, SeoWithFields, Shareable {

    @Indexed
    @Required
    @ToolUi.RichText(toolbar = TinyRichTextToolbar.class, inline = true)
    private String pageTitle;
    @DynamicPlaceholderMethod("getInternalNameFallback")
    private String internalName;
    @ToolUi.RichText(toolbar = MediumRichTextToolbar.class, inline = false)
    private String pageDescription;
    private ArticleLead lead;
    @CollectionMinimum(1)
    private List<MealCourse> courses;

    public String getInternalNameFallback() {
        return RichTextUtils.richTextToPlainText(getPageTitle());
    }

    public String getPageTitle() {
        return this.pageTitle;
    }

    public String getPageDescription() {
        return this.pageDescription;
    }

    public ArticleLead getLead() {
        return this.lead;
    }

    @Override
    public String getLinkableUrl(Site site) {
        return Page.super.getLinkableUrl(site);
    }

    @Override
    public String getLinkableText() {
        return getPagePromotableTitle();
    }

    @Override
    public String getSeoTitleFallback() {
        return RichTextUtils.richTextToPlainText(pageTitle);
    }

    @Override
    public String getSeoDescriptionFallback() {
        return RichTextUtils.richTextToPlainText(this.getPagePromotableDescription());
    }

    @Override
    public SeoWithFieldsData asSeoWithFieldsData() {
        return SeoWithFields.super.asSeoWithFieldsData();
    }

    @Override
    public String getSeoTitle() {
        return SeoWithFields.super.getSeoTitle();
    }

    @Override
    public String getSeoDescription() {
        return SeoWithFields.super.getSeoDescription();
    }

    @Override
    public Set<RobotsValue> getSeoRobots() {
        return SeoWithFields.super.getSeoRobots();
    }

    @Override
    public String getSeoFullTitle(Site site) {
        return SeoWithFields.super.getSeoFullTitle(site);
    }

    @Override
    public String getSeoTitleNoteHtml() {
        return SeoWithFields.super.getSeoTitleNoteHtml();
    }

    @Override
    public String getUrlSlugFallback() {
        return Utils.toNormalized(RichTextUtils.richTextToPlainText(getPageTitle()));
    }

    @Override
    public String getUrlSlug() {
        return HasUrlSlugWithField.super.getUrlSlug();
    }

    @Override
    public HasUrlSlugData asHasUrlSlugData() {
        return HasUrlSlugWithField.super.asHasUrlSlugData();
    }

    @Override
    public HasUrlSlugWithFieldData asHasUrlSlugWithFieldData() {
        return HasUrlSlugWithField.super.asHasUrlSlugWithFieldData();
    }

    @Override
    public String createPermalink(Site site) {
        return AbstractPermalinkRule.create(site, this, SectionPrefixPermalinkRule.class);
    }

    @Override
    public CascadingPageElementsModification asCascadingPageElementsModification() {
        return CascadingPageElements.super.asCascadingPageElementsModification();
    }

    @Override
    public Recordable getCascadingPageElementsContainer() {
        return CascadingPageElements.super.getCascadingPageElementsContainer();
    }

    @Override
    public PagePromotableWithOverridesData asPagePromotableWithOverridesData() {
        return PagePromotableWithOverrides.super.asPagePromotableWithOverridesData();
    }

    @Override
    public String getPagePromotableTitleFallback() {
        return PagePromotableWithOverrides.super.getPagePromotableTitleFallback();
    }

    @Override
    public String getPagePromotableDescriptionFallback() {
        return PagePromotableWithOverrides.super.getPagePromotableDescriptionFallback();
    }

    @Override
    public WebImageAsset getPagePromotableImageFallback() {
        return PagePromotableWithOverrides.super.getPagePromotableImageFallback();
    }

    @Override
    public String getPagePromotableCategoryFallback() {
        return PagePromotableWithOverrides.super.getPagePromotableCategoryFallback();
    }

    @Override
    public String getPagePromotableCategoryUrlFallback(Site site) {
        return PagePromotableWithOverrides.super.getPagePromotableCategoryUrlFallback(site);
    }

    @Override
    public String getPagePromotableTitle() {
        return PagePromotableWithOverrides.super.getPagePromotableTitle();
    }

    @Override
    public String getPagePromotableDescription() {
        return PagePromotableWithOverrides.super.getPagePromotableDescription();
    }

    @Override
    public WebImageAsset getPagePromotableImage() {
        return PagePromotableWithOverrides.super.getPagePromotableImage();
    }

    @Override
    public String getPagePromotableType() {
        return PagePromotableWithOverrides.super.getPagePromotableType();
    }

    @Override
    public String getPagePromotableCategory() {
        return PagePromotableWithOverrides.super.getPagePromotableCategory();
    }

    @Override
    public String getPagePromotableCategoryUrl(Site site) {
        return PagePromotableWithOverrides.super.getPagePromotableCategoryUrl(site);
    }

    @Override
    public Date getPagePromotableDate() {
        return PagePromotableWithOverrides.super.getPagePromotableDate();
    }

    @Override
    public String getPagePromotableUrl(Site site) {
        return PagePromotableWithOverrides.super.getPagePromotableUrl(site);
    }

    @Override
    public Section getSectionParent() {
        return HasSectionWithField.super.getSectionParent();
    }

    @Override
    public HasSectionData asHasSectionData() {
        return HasSectionWithField.super.asHasSectionData();
    }

    @Override
    public List<Section> getSectionAncestors() {
        return HasSectionWithField.super.getSectionAncestors();
    }

    @Override
    public HasSectionWithFieldData asHasSectionWithFieldData() {
        return HasSectionWithField.super.asHasSectionWithFieldData();
    }

    @Override
    public ShareableData asShareableData() {
        return Shareable.super.asShareableData();
    }

    @Override
    public String getShareableTitleFallback() {
        return Shareable.super.getShareableTitleFallback();
    }

    @Override
    public String getShareableDescriptionFallback() {
        return Shareable.super.getShareableDescriptionFallback();
    }

    @Override
    public WebImageAsset getShareableImageFallback() {
        return Shareable.super.getShareableImageFallback();
    }

    @Override
    public List<SiteMapEntry> getSiteMapEntries(Site site) {
        return DefaultSiteMapItem.super.getSiteMapEntries(site);
    }

    @Override
    public List<Tag> getTags() {
        return HasTagsWithField.super.getTags();
    }

    @Override
    public List<Tag> getVisibleTags() {
        return HasTagsWithField.super.getVisibleTags();
    }

    @Override
    public HasTagsData asHasTagsData() {
        return HasTagsWithField.super.asHasTagsData();
    }

    @Override
    public HasTagsWithFieldData asHasTagsWithFieldData() {
        return HasTagsWithField.super.asHasTagsWithFieldData();
    }

    public String getInternalName() {
        return this.internalName;
    }

    @Override
    public String getLabel() {
        return StringUtils.firstNonBlank(getInternalName(), getInternalNameFallback());
    }

    @Override
    public boolean isInstantiableTo(Class<?> targetClass) {
        return super.isInstantiableTo(targetClass);
    }

    @Override
    public <T> T as(Class<T> targetClass) {
        return super.as(targetClass);
    }

    public List<MealCourse> getCourses() {
        return this.courses;
    }

    public void setCourses(List<MealCourse> courses) {
        this.courses = courses;
    }

    @Override
    public List<Recipe> getRecipes() {
        return getCourses()
            .stream()
            .flatMap(course -> course.getDishes().stream())
            .map(RecipeModulePlacementInline::getRecipe)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}

