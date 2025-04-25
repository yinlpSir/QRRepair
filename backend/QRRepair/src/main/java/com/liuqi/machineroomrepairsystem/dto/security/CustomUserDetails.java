package com.liuqi.machineroomrepairsystem.dto.security;

import com.liuqi.machineroomrepairsystem.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 *  Customize UserDetails.
 */
public final class CustomUserDetails extends User implements UserDetails {
    public CustomUserDetails(User user) {
        super(user.getId(), user.getUsername(), user.getPassword(),user.getPhoneNumber(),user.getRole());
    }

    /**
     *  返回授予用户的权限 ，cannot return null
     * @return the authorities,按自然键排序(never null)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var grantedAuthorities = new ArrayList<GrantedAuthority>();
        byte role = super.getRole();
        String roleName = switch(role){
            case 1 -> "ADMIN";
            default -> "COMMON";
        };
        grantedAuthorities.add(new SimpleGrantedAuthority(roleName));
        return grantedAuthorities;
    }

    /**
     * 重写 UserDetails 的 getUsername() ,返回用于对用户进行身份验证的用户名。cannot return null
     * @return the username (never null)
     */
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    /**
     * 用户账户是否过期,无法对过期的帐户进行身份验证。
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 显示用户处于锁定状态或未锁定状态。被锁定的用户无法进行认证。
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     *  指示用户的凭据(密码)是否已过期。过期凭据将会阻止身份验证。
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 显示用户是否启用或禁用。禁用的用户无法进行认证。
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
