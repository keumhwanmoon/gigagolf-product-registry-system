package kr.co.xfilegolf.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author jason, Moon
 * @since 2016-10-06
 */
@Data
public class UserRegisterForm extends UserForm {

    private Long id;

    private boolean update;

    @NotNull
    @Size(min = 5, max = 15, message = "최소 5자에서, 최대 15자 사이의 로그인ID를 입력하십시오.")
    private String loginId;

    @NotNull
    @Size(min = 8, max = 15, message = "최소 8자에서, 최대 15자 사이의 비밀번호를 입력하십시오.")
    private String password;

    private String agencyName;

    private String agencyAddress;

    private String presidentName;

    @NotNull
    @Size(min = 1, message = "담당자명은 반드시 입력해야 합니다.")
    private String personInCharge;

    private String address;

    private String businessNumber;

    private String phoneNumber;

    @NotNull
    private boolean activation;
}
