package brightspot.article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import brightspot.ad.injection.view.SupportsViewBasedAdInjection;
import brightspot.author.HasAuthorsWithField;
import brightspot.breadcrumbs.HasBreadcrumbs;
import brightspot.cascading.CascadingPageElements;
import brightspot.commenting.HasCommenting;
import brightspot.commenting.coral.HasCoralPageMetadata;
import brightspot.commenting.disqus.HasDisqusPageMetadata;
import brightspot.embargo.Embargoable;
import brightspot.homepage.Homepage;
import brightspot.image.WebImageAsset;
import brightspot.l10n.LocaleProvider;
import brightspot.link.InternalLink;
import brightspot.mediatype.HasMediaTypeWithOverride;
import brightspot.mediatype.MediaType;
import brightspot.module.list.page.PagePromo;
import brightspot.module.promo.page.PagePromoModulePlacementInline;
import brightspot.opengraph.article.OpenGraphArticle;
import brightspot.page.Page;
import brightspot.permalink.AbstractPermalinkRule;
import brightspot.permalink.Permalink;
import brightspot.promo.page.InternalPagePromoItem;
import brightspot.promo.page.PagePromotableWithOverrides;
import brightspot.rte.LargeRichTextToolbar;
import brightspot.rte.TinyRichTextToolbar;
import brightspot.rte.image.ImageRichTextElement;
import brightspot.rte.link.LinkRichTextElement;
import brightspot.search.boost.HasSiteSearchBoostIndexes;
import brightspot.search.modifier.exclusion.SearchExcludable;
import brightspot.section.HasSecondarySections;
import brightspot.section.HasSecondarySectionsWithField;
import brightspot.section.HasSection;
import brightspot.section.HasSectionWithField;
import brightspot.section.Section;
import brightspot.section.SectionPrefixPermalinkRule;
import brightspot.seo.SeoWithFields;
import brightspot.share.Shareable;
import brightspot.sharedcontent.SharedContent;
import brightspot.site.DefaultSiteMapItem;
import brightspot.sponsoredcontent.HasSponsorWithField;
import brightspot.tag.HasTags;
import brightspot.tag.HasTagsWithField;
import brightspot.tag.Tag;
import brightspot.update.LastUpdatedProvider;
import brightspot.urlslug.HasUrlSlugWithField;
import brightspot.util.RichTextUtils;
import brightspot.video.VideoLead;
import com.google.common.collect.ImmutableList;
import com.psddev.cms.db.Content;
import com.psddev.cms.db.ContentEditDrawerItem;
import com.psddev.cms.db.Directory;
import com.psddev.cms.db.Interchangeable;
import com.psddev.cms.db.Site;
import com.psddev.cms.db.SiteSettings;
import com.psddev.cms.db.ToolUi;
import com.psddev.cms.ui.ToolLocalization;
import com.psddev.cms.ui.content.Suggestible;
import com.psddev.cms.ui.content.place.Placeable;
import com.psddev.cms.ui.content.place.PlaceableTarget;
import com.psddev.dari.db.ObjectType;
import com.psddev.dari.db.Query;
import com.psddev.dari.db.Recordable;
import com.psddev.dari.util.ObjectUtils;
import com.psddev.dari.util.Utils;
import com.psddev.feed.FeedItem;
import com.psddev.sitemap.NewsSiteMapItem;
import com.psddev.sitemap.SiteMapEntry;
import com.psddev.sitemap.SiteMapNews;
import com.psddev.sitemap.SiteMapSettingsModification;
import com.psddev.suggestions.Suggestable;
import com.psddev.theme.StyleEmbeddedContentCreator;
import org.apache.commons.lang3.StringUtils;

@ToolUi.FieldDisplayOrder({
        "headline",
        "subheadline",
        "hasUrlSlug.urlSlug",
        "hasAuthorsWithField.authors",
        "lead",
        "intro",
        "items",
        "hasSectionWithField.section",
        "hasTags.tags",
        "embargoable.embargo",
        "seo.title",
        "seo.suppressSeoDisplayName",
        "seo.description",
        "seo.keywords",
        "seo.robots",
        "ampPage.ampDisabled"
})
@ToolUi.IconName("format_list_numbered")
@ToolUi.FieldDisplayPreview({
        "headline",
        "subheadline",
        "hasAuthorsWithField.authors",
        "hasSectionWithField.section",
        "hasTags.tags",
        "cms.content.updateDate",
        "cms.content.updateUser" })
public class Listicle extends Content implements
        CascadingPageElements,
        ContentEditDrawerItem,
        Embargoable,
        DefaultSiteMapItem,
        FeedItem,
        HasAuthorsWithField,
        HasBreadcrumbs,
        HasCommenting,
        HasCoralPageMetadata,
        HasDisqusPageMetadata,
        HasMediaTypeWithOverride,
        HasSecondarySectionsWithField,
        HasSectionWithField,
        HasSiteSearchBoostIndexes,
        HasSponsorWithField,
        HasTagsWithField,
        HasUrlSlugWithField,
        Interchangeable,
        NewsSiteMapItem,
        OpenGraphArticle,
        Page,
        PagePromotableWithOverrides,
        Placeable,
        SearchExcludable,
        SeoWithFields,
        Shareable,
        SharedContent,
        Suggestable,
        Suggestible,
        SupportsViewBasedAdInjection {

    public static final String TAB_OVERRIDES = "Overrides";

    @Indexed
    @Required
    @ToolUi.NoteHtml("<span data-dynamic-html=\"${content.getSeoTitleNoteHtml()}\"></span>")
    @ToolUi.RichText(toolbar = TinyRichTextToolbar.class)
    private String headline;

    @ToolUi.RichText(toolbar = TinyRichTextToolbar.class)
    private String subheadline;

    @ToolUi.EmbeddedContentCreatorClass(StyleEmbeddedContentCreator.class)
    private ListicleLead lead;

    @ToolUi.RichText(inline = false, toolbar = LargeRichTextToolbar.class, lines = 10)
    private String intro;

    private List<ListicleItem> items;

    /**
     * @return rich text
     */
    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    /**
     * @return rich text
     */
    public String getSubheadline() {
        return subheadline;
    }

    public void setSubheadline(String subheadline) {
        this.subheadline = subheadline;
    }

    /**
     * @return rich text
     */
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public ListicleLead getLead() {
        return lead;
    }

    public void setLead(ListicleLead lead) {
        this.lead = lead;
    }

    public List<ListicleItem> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<ListicleItem> items) {
        this.items = items;
    }

    // --- HasBreadcrumbs support ---

    @Override
    public List<Section> getBreadcrumbs() {
        List<Section> ancestors = getSectionAncestors();
        Collections.reverse(ancestors);
        return ancestors;
    }

    // --- HasMediaTypeOverride Support ---

    @Override
    public MediaType getPrimaryMediaTypeFallback() {
        if (getLead() instanceof VideoLead) {
            return MediaType.VIDEO;
        }

        return MediaType.TEXT;
    }

    // --- HasSiteSearchBoostIndexes support ---

    @Override
    public String getSiteSearchBoostTitle() {
        return getHeadline();
    }

    @Override
    public String getSiteSearchBoostDescription() {
        return getSubheadline();
    }

    // --- HasUrlSlugWithField support ---

    @Override
    public String getUrlSlugFallback() {
        return Utils.toNormalized(RichTextUtils.richTextToPlainText(getHeadline()));
    }

    // *** Promotable implementation *** //
    @Override
    public String getPagePromotableTitleFallback() {
        return getHeadline();
    }

    @Override
    public String getPagePromotableDescriptionFallback() {
        return getSubheadline();
    }

    /**
     * Gets the first image on the listicle. First looks for an image in the listicle's {@link Listicle#lead} and then
     * looks in the listicle's {@link Listicle#intro}, and finally in each {@link ListicleItem}'s body.
     *
     * @return an {@link WebImageAsset} or {@code null}, if no image is found.
     */
    @Override
    public WebImageAsset getPagePromotableImageFallback() {
        return Optional.ofNullable(getLead())
                .map(ListicleLead::getListicleLeadImage)
                .orElseGet(() -> Optional.ofNullable(getIntro())
                        .map(ImageRichTextElement::getFirstImageFromRichText)
                        .orElseGet(() -> getItems()
                                .stream()
                                .map(ListicleItem::getFirstImage)
                                .filter(Objects::nonNull)
                                .findFirst()
                                .orElse(null)));
    }

    @Override
    public String getPagePromotableType() {
        return Optional.ofNullable(getPrimaryMediaType())
            .map(MediaType::getIconName)
            .orElse(null);
    }

    @Override
    public String getPagePromotableCategoryFallback() {
        return Optional.ofNullable(asHasSectionData().getSectionParent())
                .map(Section::getSectionDisplayNameRichText)
                .orElse(null);
    }

    @Override
    public String getPagePromotableCategoryUrlFallback(Site site) {
        return Optional.ofNullable(asHasSectionData().getSectionParent())
                .map(section -> section.getLinkableUrl(site))
                .orElse(null);
    }

    /**
     * Gets the total number of plain text characters from the {@link Listicle} intro as well as each {@link
     * ListicleItem}
     *
     * @return A {@link String} displaying the time to read in minutes, minimum 1 minute
     */
    public String getReadDuration() {
        long characterCount = Optional.ofNullable(getIntro())
                .map(RichTextUtils::richTextToPlainText)
                .map(RichTextUtils::stripRichTextElements)
                .map(String::length)
                .orElse(0);

        characterCount += getItems()
                .stream()
                .mapToLong(ListicleItem::getTextCharacterCount)
                .sum();

        // TODO: localization needed. This implementation is highly dependent on language!
        // Average adult reading time given: 275 wpm. Average number of characters per English
        // word is 4-5 characters. Therefore, average is 1100-1375 CPM. 1200 CPM used.
        long readTime = Math.max(1L, characterCount / 1_200);

        return readTime + " " + ToolLocalization.text(Listicle.class, "readTimeMinutes");
    }

    // --- OpenGraphArticle support ---

    @Override
    public Date getOpenGraphArticleModifiedDate() {
        return getUpdateDate();
    }

    @Override
    public Date getOpenGraphArticlePublishedDate() {
        return getPublishDate();
    }

    @Override
    public List<String> getOpenGraphArticleAuthorUrls(Site site) {
        return Optional.ofNullable(asHasAuthorsWithFieldData().getAuthors())
                .flatMap(authors -> Optional.ofNullable(authors.stream()))
                .orElseGet(Stream::empty)
                .map(author -> Permalink.getPermalink(site, author))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public String getOpenGraphArticleSection() {
        return Optional.ofNullable(asHasSectionData().getSectionParent())
                .map(Section::getSectionDisplayNamePlainText)
                .orElse(null);
    }

    @Override
    public List<String> getOpenGraphArticleTags() {
        return getTags()
                .stream()
                .map(Tag::getTagDisplayNamePlainText)
                .collect(Collectors.toList());
    }

    // --- Recordable support ---

    @Override
    public String getLabel() {
        return RichTextUtils.richTextToPlainText(getHeadline());
    }

    // --- SeoWithFields support ---

    @Override
    public String getSeoTitleFallback() {
        return RichTextUtils.richTextToPlainText(getPagePromotableTitle());
    }

    @Override
    public String getSeoDescriptionFallback() {
        return RichTextUtils.richTextToPlainText(getPagePromotableDescription());
    }

    // --- Shareable support ---

    @Override
    public String getShareableTitleFallback() {
        return RichTextUtils.richTextToPlainText(getPagePromotableTitleFallback());
    }

    @Override
    public String getShareableDescriptionFallback() {
        return RichTextUtils.richTextToPlainText(getPagePromotableDescriptionFallback());
    }

    @Override
    public WebImageAsset getShareableImageFallback() {
        return getPagePromotableImageFallback();
    }

    // --- Linkable support ---

    @Override
    public String getLinkableText() {
        return getPagePromotableTitle();
    }

    // --- NewsSiteMapItem support ---

    @Override
    public List<SiteMapEntry> getNewsSiteMapEntries(Site site) {
        String sitePermalinkPath = as(Directory.ObjectModification.class).getSitePermalinkPath(site);
        if (StringUtils.isBlank(sitePermalinkPath)) {
            return Collections.emptyList();
        }

        Locale locale = ObjectUtils.firstNonNull(
                LocaleProvider.getModelLocale(site, this),
                LocaleProvider.DEFAULT_LOCALE);

        SiteMapEntry siteMapEntry = new SiteMapEntry();
        siteMapEntry.setUpdateDate(
                ObjectUtils.firstNonNull(
                        LastUpdatedProvider.getMostRecentUpdateDate(getState()),
                        getState().as(Content.ObjectModification.class).getPublishDate()
                )
        );
        siteMapEntry.setPermalink(SiteSettings.get(
                site,
                f -> f.as(SiteMapSettingsModification.class).getSiteMapDefaultUrl()
                        + StringUtils.prependIfMissing(sitePermalinkPath, "/")));

        SiteMapNews siteMapNews = new SiteMapNews();
        siteMapNews.setName(site != null ? site.getName() : "Global");

        siteMapNews.setLanguage(locale.getISO3Language());
        siteMapNews.setPublicationDate(this.getPublishDate());
        siteMapNews.setTitle(ObjectUtils.firstNonBlank(
                getSeoTitle(),
                RichTextUtils.richTextToPlainText(this.getHeadline())
        ));
        List<String> keywords = getTags()
                .stream()
                .map(Tag::getTagDisplayNamePlainText)
                .collect(Collectors.toList());

        if (!ObjectUtils.isBlank(keywords)) {
            if (keywords.size() > 10) {
                keywords = keywords.subList(0, 10);
            }
            siteMapNews.setKeywords(keywords);
        }

        siteMapEntry.setNews(Collections.singletonList(siteMapNews));

        return Collections.singletonList(siteMapEntry);
    }

    // --- FeedElement support ---

    @Override
    public String getFeedTitle() {
        return getSeoTitle();
    }

    @Override
    public String getFeedDescription() {
        return getSeoDescription();
    }

    @Override
    public String getFeedLink(Site site) {
        return Permalink.getPermalink(site, this);
    }

    // --- FeedItem support ---

    @Override
    public String getFullContentEncoded() {
        return Stream.concat(
                        Stream.of(getIntro())
                                .map(RichTextUtils::stripRichTextElements)
                                .map(RichTextUtils::richTextToPlainText),
                        getItems().stream()
                                .map(ListicleItem::getFullContentEncoded))
                .collect(Collectors.joining(" "));
    }

    // --- Directory.Item support ---

    @Override
    public String createPermalink(Site site) {
        return AbstractPermalinkRule.create(site, this, SectionPrefixPermalinkRule.class);
    }

    // --- Suggestable support ---

    @Override
    public String getSuggestableText() {
        return Stream.concat(
                        Stream.of(getHeadline(), getSubheadline(), getIntro())
                                .map(RichTextUtils::stripRichTextElements)
                                .map(RichTextUtils::richTextToPlainText),
                        getItems().stream().map(ListicleItem::getSuggestableText))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(" "));
    }

    // --- Suggestible support ---

    @Override
    public List<String> getSuggestibleFields() {
        return Stream.of(
                "seo.title",
                "seo.description",
                "pagePromotable.promoTitle",
                "pagePromotable.promoDescription",
                "pagePromotable.promoImage",
                "shareable.shareTitle",
                "shareable.shareDescription",
                "shareable.shareImage"
        ).collect(Collectors.toList());
    }

    // --- Placeable support ---

    @Override
    public List<PlaceableTarget> getPlaceableTargets() {
        List<PlaceableTarget> targets = new ArrayList<>();

        targets.add(Query.from(Homepage.class)
                .where("cms.site.owner = ?", as(Site.ObjectModification.class).getOwner())
                .first());
        targets.addAll(this.as(HasTags.class)
                .getTags()
                .stream()
                .filter(PlaceableTarget.class::isInstance)
                .filter(tag -> !tag.isHiddenTag())
                .map(PlaceableTarget.class::cast)
                .collect(Collectors.toSet()));
        targets.addAll(this.as(HasSection.class)
                .getSectionAncestors()
                .stream()
                .filter(PlaceableTarget.class::isInstance)
                .map(PlaceableTarget.class::cast)
                .collect(Collectors.toSet()));
        targets.addAll(this.as(HasSecondarySections.class)
                .getSecondarySections()
                .stream()
                .filter(PlaceableTarget.class::isInstance)
                .map(PlaceableTarget.class::cast)
                .collect(Collectors.toSet()));

        return targets;
    }

    // --- Interchangeable support ---

    @Override
    public boolean loadTo(Object target) {

        // Support the following types for Shelf drag and drop:
        // - PagePromo in PageListModule
        // - PagePromoModulePlacementInline
        // - LinkRichTextElement
        if (target instanceof PagePromo) {

            PagePromo pagePromo = (PagePromo) target;
            InternalPagePromoItem internalPagePromoItem = new InternalPagePromoItem();
            internalPagePromoItem.setItem(this);
            pagePromo.setItem(internalPagePromoItem);

            return true;

        } else if (target instanceof PagePromoModulePlacementInline) {

            PagePromoModulePlacementInline pagePromoModulePlacementInline = (PagePromoModulePlacementInline) target;
            InternalPagePromoItem internalPagePromoItem = new InternalPagePromoItem();
            internalPagePromoItem.setItem(this);
            pagePromoModulePlacementInline.setItem(internalPagePromoItem);

            return true;

        } else if (target instanceof LinkRichTextElement) {

            LinkRichTextElement linkRte = (LinkRichTextElement) target;
            InternalLink internalLink = new InternalLink();
            internalLink.setItem(this);
            linkRte.setLink(internalLink);

            return true;
        }
        return false;
    }

    @Override
    public List<UUID> loadableTypes(Recordable recordable) {

        // Support the following types for Shelf drag and drop:
        // - PagePromo in PageListModule
        // - PagePromoModulePlacementInline
        // - LinkRichTextElement
        return ImmutableList.of(
                getState().getTypeId(), // enable dragging and dropping as itself from the shelf
                ObjectType.getInstance(PagePromo.class).getId(),
                ObjectType.getInstance(PagePromoModulePlacementInline.class).getId(),
                ObjectType.getInstance(LinkRichTextElement.class).getId()
        );
    }
}
