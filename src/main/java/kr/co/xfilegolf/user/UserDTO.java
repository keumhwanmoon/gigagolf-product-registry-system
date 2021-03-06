package kr.co.xfilegolf.user;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jason, Moon
 * @since 2016-10-08
 */
@Data
public class UserDTO {

    private Long id;
    private String loginId;
    private String role;
    private String agencyName;
    private String presidentName;
    private String personInCharge;
    private String agencyAddress;
    private String businessNumber;
    private String phoneNumber;
    private LocalDateTime createdOn;
    private LocalDateTime lastModifiedOn;
    private boolean activation;
}
