package io.jpress.model;

import io.jboot.db.annotation.Table;
import io.jpress.model.base.BaseCoupon;

/**
 * Generated by JPress.
 */
@Table(tableName = "coupon", primaryKey = "id")
public class Coupon extends BaseCoupon<Coupon> {

    private static final long serialVersionUID = 1L;

    public static final int VALID_TYPE_ABSOLUTELY_EFFECTIVE = 1; //优惠码的验证类型：绝对有效，（XXX-XXX时间段有效）
    public static final int VALID_TYPE_RELATIVELY_EFFECTIVE = 2; //优惠码的验证类型：相对时效（领取后N天有效）


    //1未领取 2未使用、3使用中、9不能使用
    public static final int STATUS_UNCLAIMED = 1;
    public static final int STATUS_UNUSED = 2;
    public static final int STATUS_INUSING = 3;
    public static final int STATUS_USED_OR_ANNUL = 9;

    public boolean isNormal() {
        Integer status = getStatus();
        return status != null && (status == STATUS_UNUSED || status == STATUS_INUSING);
    }

}
