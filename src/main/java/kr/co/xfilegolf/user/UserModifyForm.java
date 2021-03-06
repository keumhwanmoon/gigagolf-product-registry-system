package kr.co.xfilegolf.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author jason, Moon
 * @since 2016-10-13
 */
@Data
public class UserModifyForm extends UserForm {

    private Long id;

    @NotNull
    @Size(min = 8, max = 15, message = "최소 8자에서, 최대 15자 사이의 비밀번호를 입력하십시오.")
    private String password;

    private String agencyName;

    private String presidentName;

    @NotNull
    @Size(min = 1, message = "담당자명은 반드시 입력해야 합니다.")
    private String personInCharge;

    private String agencyAddress;

    private String businessNumber;

    private String phoneNumber;

    private String address;
}
