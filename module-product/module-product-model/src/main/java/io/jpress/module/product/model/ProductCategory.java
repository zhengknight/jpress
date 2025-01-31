package io.jpress.module.product.model;

import com.jfinal.core.JFinal;
import io.jboot.db.annotation.Table;
import io.jboot.utils.StrUtil;
import io.jpress.JPressOptions;
import io.jpress.commons.layer.SortModel;
import io.jpress.commons.url.JPressUrlUtil;
import io.jpress.module.product.model.base.BaseProductCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by JPress.
 */
@Table(tableName = "product_category", primaryKey = "id")
public class ProductCategory extends BaseProductCategory<ProductCategory> implements SortModel {

    private static final long serialVersionUID = 1L;

    /**
     * 普通的分类
     * 分类可以有多个层级
     */
    public static final String TYPE_CATEGORY = "category";

    /**
     * 标签
     * 标签只有一个层级
     */
    public static final String TYPE_TAG = "tag";


    public boolean isTag() {
        return TYPE_TAG.equals(getType());
    }

    private int layerNumber;
    private SortModel parent;
    private List<SortModel> childs;


    /**
     * 是否是顶级菜单
     *
     * @return
     */
    @Override
    public boolean isTop() {
        return getPid() != null && getPid() == 0;
    }

    @Override
    public Long getParentId() {
        return getPid();
    }

    @Override
    public void setParent(SortModel parent) {
        this.parent = parent;
    }

    @Override
    public SortModel getParent() {
        return parent;
    }

    @Override
    public void setChilds(List childs) {
        this.childs = childs;
    }

    @Override
    public void addChild(SortModel child) {
        if (childs == null) {
            childs = new ArrayList<>();
        }
        childs.add(child);
    }

    @Override
    public List getChilds() {
        return childs;
    }

    public boolean hasChild() {
        return childs != null && !childs.isEmpty();
    }

    @Override
    public void setLayerNumber(int layerNumber) {
        this.layerNumber = layerNumber;
    }

    @Override
    public int getLayerNumber() {
        return layerNumber;
    }

    public String getLayerString() {
        if (layerNumber == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < layerNumber; i++) {
            if (i == 0) {
                sb.append("|—");
            } else {
                sb.append("—");
            }
        }
        return sb.toString();
    }

    public boolean isMyChild(long id) {
        if (childs == null || childs.isEmpty()) {
            return false;
        }

        return isMyChild(childs, id);
    }

    private boolean isMyChild(List<SortModel> categories, long id) {
        for (SortModel category : categories) {
            if (category.getId().equals(id)) {
                return true;
            }

            if (category.getChilds() != null) {
                boolean isChild = isMyChild(category.getChilds(), id);
                if (isChild) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getUrl() {
        String prefix = TYPE_CATEGORY.equals(getType()) ? "/product/category/" : "/product/tag/";
        return JPressUrlUtil.getUrl(prefix, getSlug());
    }


    public String getHtmlView() {
        return StrUtil.isBlank(getStyle()) ? "prolist.html" : "prolist_" + getStyle().trim() + ".html";
    }
}
