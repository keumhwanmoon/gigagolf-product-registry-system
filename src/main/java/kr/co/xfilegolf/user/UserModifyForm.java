package kr.co.xfilegolf.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author jason, Moon
 * @since 2016-10-13
 */
@Data
public class UserModifyForm {

    private Long id;

    @NotNull
    @Size(min = 8, max = 15, message = "최소 8자에서, 최대 15자 사이의 비밀번호를 입력하십시오.")
    private String password;

    @NotNull
    @Size(min = 1, message = "가맹점명은 반드시 입력해야 합니다.")
    private String agencyName;

    @NotNull
    @Size(min = 1, message = "대표자명은 반드시 입력해야 합니다.")
    private String presidentName;

    @NotNull
    @Size(min = 1, message = "담당자명은 반드시 입력해야 합니다.")
    private String personInCharge;

    @NotNull
    @Size(min = 1, message = "사업장 소재지는 반드시 입력해야 합니다.")
    private String agencyAddress;

    @NotNull
    @Size(min = 1, message = "사업자 등록번호는 반드시 입력해야 합니다.")
    private String businessNumber;

    @NotNull
    @Size(min = 1, message = "대표전화는 반드시 입력해야 합니다.")
    private String phoneNumber;

    @NotNull
    @Size(min = 1, message = "핸드폰 번호는 반드시 입력해야 합니다.")
    private String mobilePhoneNumber;
}
