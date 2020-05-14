package org.cgic.commons.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 资源表
 *
 * @author charleyZZZZ 2019-07-02 14:09:54
 */
@Getter
@Setter
@ApiModel("资源表")
@Table(name = "sys_resource")
public class SysResource extends BaseDTO {

    public static final String FIELD_RESOURCE_ID = "resourceId";
    public static final String FIELD_RESOURCE_TYPE = "resourceType";
    public static final String FIELD_RESOURCE_CODE = "resourceCode";
    public static final String FIELD_RESOURCE_URL = "resourceUrl";
    public static final String FIELD_RESOURCE_NAME = "resourceName";


    @Id
    private Long resourceId;
    @NotEmpty
    private String resourceType;
    @NotEmpty
    private String resourceCode;
    @NotEmpty
    private String resourceUrl;
    @NotEmpty
    private String resourceName;

}
